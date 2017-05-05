package com.senierr.lib;

import java.util.List;

/**
 * 权限检查回调
 *
 * @author zhouchunjie
 * @date 2017/5/4
 */

public abstract class CheckCallback {

    /**
     * 所有请求权限通过
     */
    public abstract void onAllGranted();

    /**
     * 权限未通过
     *
     * @param deniedWithNextAskList 拒绝，下次询问的权限列表
     * @param deniedWithNoAskList 拒绝，不再询问的权限列表
     */
    public void onDenied(List<String> deniedWithNextAskList, List<String> deniedWithNoAskList) {}
}
