package com.senierr.permission

/**
 * 权限检查回调
 *
 * @author zhouchunjie
 * @date 2017/5/4
 */
abstract class RequestCallback {

    /**
     * 所有请求权限通过
     */
    abstract fun onAllGranted()

    /**
     * 权限未通过
     *
     * @param nextAskList 拒绝，下次询问的权限列表
     * @param neverAskList 拒绝，不再询问的权限列表
     */
    open fun onDenied(nextAskList: MutableList<String>, neverAskList: MutableList<String>) {}
}