package com.lize.wanandroid.ui.activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.lize.wanandroid.R;
import com.lize.wanandroid.base.activity.BaseActivity;
import com.lize.wanandroid.databinding.ActivityMainBinding;
import com.lize.wanandroid.databinding.ActivityWebViewBinding;
import com.lize.wanandroid.ui.widget.ArticileMenuPopuWindow;
import com.lize.wanandroid.util.SharesUtils;
import com.lize.wanandroid.util.ValueUtil;

import java.util.List;

public class WebViewActivity extends BaseActivity<ActivityWebViewBinding> {
    private String loadUrl;
    private ArticileMenuPopuWindow articileMenuPopuWindow;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        loadUrl = getIntent().getStringExtra("loadUrl");
        binding.backIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initWebView();
    }

    private void initWebView() {
        WebSettings settings = binding.webView.getSettings();
        settings.setJavaScriptEnabled(true);// 支持JS
        settings.setBuiltInZoomControls(true);// 显示放大缩小按钮
        settings.setUseWideViewPort(true);// 支持双击放大缩小
        settings.setSupportZoom(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setLoadsImagesAutomatically(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 特别注意：5.1以上默认禁止了https和http混用，以下方式是开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                System.out.println("网页开始加载");
                binding.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                System.out.println("网页加载结束");
                String title = view.getTitle();

                binding.progressBar.setVisibility(View.GONE);
                addOnImageOnClickListener(view);
            }

            /**
             * 所有跳转的链接都在此方法中回调
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //view.loadUrl(url);
                try {
                    Log.i("跳转url", url);
                    if (!url.startsWith("http") && !url.startsWith("https") && !url.startsWith("intent://")) {
                        try {
                            // 以下固定写法
                            final Intent intent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(url));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        } catch (Exception e) {
                            // 防止没有安装的情况
                            Toast.makeText(getApplication(), "您所打开的第三方App未安装", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    } else if (url.startsWith("intent://")) {
                        //1、定义Intent 并指向跳转意图
                        Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                        //Category属性用于指定当前动作（Action）被执行的环境
                        //android.intent.category.BROWSABLE 指定寻找可在浏览器中打开应用Activity
                        intent.addCategory("android.intent.category.BROWSABLE");
                        //用于执行跳转的应用Activity
                        //ComponentName com = new ComponentName(MainActivity.this,MyActivity.class);//first

                        //ComponentName com = new ComponentName("com.example.testcomponent","com.example.testcomponent.MyActivity");//second  第二个参数必须写全包名
                        // intent.setComponent(null);

                        //2、根据intent 判读 intent所指向的意图的 应用是否存在
                        List<ResolveInfo> resolveInfos = WebViewActivity.this.getPackageManager().queryIntentActivities(intent, 0);
                        if (resolveInfos.size() > 0) {
                            startActivityIfNeeded(intent, -1);
                        } else {
                            Log.i("WebView", "未安装应用");
                        }
                        return true;

                    } else {
                        view.loadUrl(url);
                    }
                } catch (Exception e) {
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //super.onReceivedSslError(view, handler, error);
                Toast.makeText(getApplication(), "有问题", Toast.LENGTH_SHORT).show();
                handler.proceed();
            }
        });

        /*监听webView 下载文件
         * 1、可以 在本应用内处理下载
         * 2、直接破给浏览器下载，但这种方式 无法感知下载进度
         * 3、使用系统的下载服务 DownloadManager
         *  使用者只需提供下载 URI 和存储路径，并进行简单的设置。DownloadManager 会在后台进行下载，并且在下载失败、网络切换以及系统重启后尝试重新下载。
         * */
        binding.webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Log.i("下载文件", url);
                downloadByBrowser(url);
            }
        });

        binding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                binding.progressBar.setProgress(newProgress);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onReceivedTitle(WebView view, String title) {
                binding.titleTv.setText(title);
                super.onReceivedTitle(view, title);
            }
        });

        binding.webView.loadUrl(loadUrl);
    }


    public void onBackClick(View view) {
        finish();
    }


    public void onMoreClick(View view) {
        if (articileMenuPopuWindow == null) {
            articileMenuPopuWindow = new ArticileMenuPopuWindow(this);
            articileMenuPopuWindow.setListener(new ArticileMenuPopuWindow.Listener() {
                @Override
                public void onShare() {
                    SharesUtils.share(WebViewActivity.this, loadUrl);
                }

                @Override
                public void onLike() {

                }

                @Override
                public void onCopy() {
                    ClipboardManager cbm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    cbm.setPrimaryClip(ClipData.newPlainText("复制链接", loadUrl));
                    Snackbar.make(getWindow().getDecorView(), "链接已复制", Snackbar.LENGTH_SHORT).show();
                }

                @Override
                public void onOpenWeb() {
                    Uri uri = Uri.parse(binding.webView.getUrl());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
        }
        articileMenuPopuWindow.showAsDropDown(view);
    }

    private void addOnImageOnClickListener(WebView view) {
        String js = "javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistener.openImage(this.src);  " +//通过js代码找到标签为img的代码块，设置点击的监听方法与本地的openImage方法进行连接
                "    }  " +
                "}" +
                "})()";
        // view.loadUrl(js);
    }

    //通过浏览器下载
    private void downloadByBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && binding.webView.canGoBack()) {
            binding.webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (binding.webView != null) {
            binding.webView.clearCache(true);
            binding.webView.setDownloadListener(null);
            binding.webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            binding.webView.clearHistory();
            ((ViewGroup) binding.webView.getParent()).removeView(binding.webView);
            binding.webView.destroy();
        }


        super.onDestroy();

    }


    public class MJavascriptInterface {
        private Context context;
        private String[] imageUrls;

        public MJavascriptInterface(Context context, String[] imageUrls) {
            this.context = context;
            this.imageUrls = imageUrls;
        }

        @android.webkit.JavascriptInterface
        public void openImage(String img) {
            System.out.println("openImage:=" + img);
            // PhotoBowerActivity.runActivity(ArticleDetailActivity.this,img);
            /*Intent intent = new Intent();
            intent.putExtra("imageUrls", imageUrls);
            intent.putExtra("curImageUrl", img);
            intent.setClass(context, PhotoBrowserActivity.class);
            context.startActivity(intent);*/
        }
    }
}
