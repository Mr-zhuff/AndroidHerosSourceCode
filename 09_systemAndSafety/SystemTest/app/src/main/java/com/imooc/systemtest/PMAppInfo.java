package com.imooc.systemtest;

import android.graphics.drawable.Drawable;

/**
 * 封装一个Bean 保存list里面的item信息
 */
public class PMAppInfo {

    private String appLabel;
    private Drawable appIcon;
    private String pkgName;

    public PMAppInfo() {
    }

    public String getAppLabel() {
        return appLabel;
    }

    public void setAppLabel(String appName) {
        this.appLabel = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }
}