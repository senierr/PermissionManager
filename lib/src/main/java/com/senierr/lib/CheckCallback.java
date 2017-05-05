package com.senierr.lib;

import java.util.List;

/**
 * 权限检查回调
 *
 * @author zhouchunjie
 * @date 2017/5/4
 */

public interface CheckCallback {

    /**
     * 所有请求权限通过
     *
     * @param permissions 请求权限
     */
    void onAllGranted(List<String> permissions);

    /**
     * 权限未通过
     *
     * @param deniedByUserList 用户手动拒绝的权限
     * @param deniedNoRequestList 不再提醒的拒绝权限
     */
    void onDenied(List<String> deniedByUserList, List<String> deniedNoRequestList);
}
