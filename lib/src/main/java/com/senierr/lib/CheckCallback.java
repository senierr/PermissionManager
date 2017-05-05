package com.senierr.lib;

/**
 * 权限检查回调
 *
 * @author zhouchunjie
 * @date 2017/5/4
 */

public abstract class CheckCallback {

    /**
     * 所有请求权限通过
     *
     * @param permissions 请求的权限
     */
    public abstract void onAllGranted(String[] permissions);

    /**
     * 权限通过
     *
     * @param permission 请求的权限
     */
    public void onGranted(String permission) {}

    /**
     * 权限未通过
     *
     * @param permission 请求的权限
     * @param isNoAsk 是否不再询问
     */
    public abstract void onDenied(String permission, boolean isNoAsk);
}
