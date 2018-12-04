package com.senierr.permission;

import java.util.List;

/**
 * 权限检查回调
 *
 * @author zhouchunjie
 * @date 2017/5/4
 */
public abstract class RequestCallback {

    /**
     * 所有请求权限通过
     */
    public abstract void onAllGranted();

    /**
     * 权限未通过
     *
     * @param deniedAndNextAskList 拒绝，下次询问的权限列表
     * @param deniedAndNeverAskList 拒绝，不再询问的权限列表
     */
    public void onDenied(List<String> deniedAndNextAskList, List<String> deniedAndNeverAskList) {}
}
