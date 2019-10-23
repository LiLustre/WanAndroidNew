package com.lize.wanandroid.model.classify;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.*;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.*;

/**
 * @author Lize
 * on 2019/10/22
 */
public class ArticleClassify implements Parcelable {

    @SerializedName("courseId")
    private int courseId;
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("order")
    private int order;
    @SerializedName("parentChapterId")
    private int parentChapterId;
    @SerializedName("userControlSetTop")
    private boolean userControlSetTop;
    @SerializedName("visible")
    private int visible;
    @SerializedName("children")
    private List<ArticleClassify> children;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getParentChapterId() {
        return parentChapterId;
    }

    public void setParentChapterId(int parentChapterId) {
        this.parentChapterId = parentChapterId;
    }

    public boolean isUserControlSetTop() {
        return userControlSetTop;
    }

    public void setUserControlSetTop(boolean userControlSetTop) {
        this.userControlSetTop = userControlSetTop;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public List<ArticleClassify> getChildren() {
        return children;
    }

    public void setChildren(List<ArticleClassify> children) {
        this.children = children;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.courseId);
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.order);
        dest.writeInt(this.parentChapterId);
        dest.writeByte(this.userControlSetTop ? (byte) 1 : (byte) 0);
        dest.writeInt(this.visible);
        dest.writeList(this.children);
    }

    public ArticleClassify() {
    }

    protected ArticleClassify(Parcel in) {
        this.courseId = in.readInt();
        this.id = in.readInt();
        this.name = in.readString();
        this.order = in.readInt();
        this.parentChapterId = in.readInt();
        this.userControlSetTop = in.readByte() != 0;
        this.visible = in.readInt();
        this.children = new ArrayList<ArticleClassify>();
        in.readList(this.children, ArticleClassify.class.getClassLoader());
    }

    public static final Parcelable.Creator<ArticleClassify> CREATOR = new Parcelable.Creator<ArticleClassify>() {
        @Override
        public ArticleClassify createFromParcel(Parcel source) {
            return new ArticleClassify(source);
        }

        @Override
        public ArticleClassify[] newArray(int size) {
            return new ArticleClassify[size];
        }
    };
}
