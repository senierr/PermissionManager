package com.senierr.permission;

import android.app.Activity;
import android.app.FragmentManager;

/**
 * 权限管理
 *
 * @author zhouchunjie
 * @date 2017/3/6
 */
public class PermissionManager {

    private static final String TAG = PermissionManager.class.getSimpleName();

    private ProxyFragment checkFragment;
    private String[] permissions;

    private PermissionManager(Activity activity) {
        if (checkFragment == null) {
            checkFragment = (ProxyFragment) activity.getFragmentManager().findFragmentByTag(TAG);
            if (checkFragment == null) {
                checkFragment = new ProxyFragment();
                FragmentManager fragmentManager = activity.getFragmentManager();
                fragmentManager.beginTransaction()
                        .add(checkFragment, TAG)
                        .commitAllowingStateLoss();
                fragmentManager.executePendingTransactions();
            }
        }
    }

    /**
     * 创建
     *
     * @param activity
     * @return
     */
    public static PermissionManager with(Activity activity) {
        return new PermissionManager(activity);
    }

    /**
     * 添加权限
     *
     * @param permissions
     * @return
     */
    public PermissionManager permissions(final String... permissions) {
        if (permissions == null || permissions.length == 0) {
            throw new IllegalArgumentException("PermissionManager requires at least one input permission!");
        }
        this.permissions = permissions;
        return this;
    }

    /**
     * 检查权限
     *
     * @return
     */
    public boolean check() {
        if (checkFragment != null) {
            return checkFragment.check(permissions);
        } else {
            return false;
        }
    }

    /**
     * 请求权限
     *
     * @param checkCallback
     */
    public void request(RequestCallback checkCallback) {
        if (checkFragment != null) {
            checkFragment.setRequestCallback(checkCallback);
            checkFragment.request(permissions);
        }
    }
}
