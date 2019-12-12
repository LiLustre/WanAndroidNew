package com.lize.wanandroid.ui.adapter;

import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.databinding.ViewDataBinding;

import com.lize.wanandroid.R;
import com.lize.wanandroid.databinding.NaviItemLayoutBinding;
import com.lize.wanandroid.model.navi.Navi;
import com.lize.wanandroid.ui.adapter.base.DataBindingRecyclerAdapter;
import com.lize.wanandroid.util.DisplayUtil;

import java.util.List;

public class NaviListAdapter extends DataBindingRecyclerAdapter {
    private List<Navi> navis;
    private int selectPos;
    private OnClickListener onClickListener;

    public NaviListAdapter(List<Navi> navis) {
        this.navis = navis;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    protected void onBindView(ViewDataBinding binding, final int position, VH holder) {
        NaviItemLayoutBinding naviBinding = (NaviItemLayoutBinding) binding;
        Navi navi = navis.get(position);
        naviBinding.nameTv.setText(navi.getName());
        if (selectPos == position) {
            naviBinding.nameTv.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.Themecolor));
            naviBinding.nameTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
            naviBinding.nameTv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            naviBinding.selectedView.setVisibility(View.VISIBLE);
        } else {
            naviBinding.nameTv.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.grey_text_color));
            naviBinding.nameTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            naviBinding.nameTv.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            naviBinding.selectedView.setVisibility(View.INVISIBLE);
        }
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPos = position;
                notifyDataSetChanged();
                if (onClickListener != null) {
                    onClickListener.onItemClick(position);
                }
            }
        });
        naviBinding.executePendingBindings();
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.navi_item_layout;
    }

    @Override
    public int getItemCount() {
        return navis == null ? 0 : navis.size();
    }

    public interface OnClickListener {
        void onItemClick(int pos);
    }
}
