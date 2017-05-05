package com.senierr.lib;

import android.app.Activity;
import android.app.FragmentManager;

import java.util.Arrays;
import java.util.List;

/**
 * 权限管理
 *
 * @author zhouchunjie
 * @date 2017/3/6
 */

public class PermissionManager {

    private static final String TAG = "PermissionManager";

    private CheckFragment checkFragment;
    private String[] permissions;

    private PermissionManager(Activity activity) {
        if (checkFragment == null) {
            checkFragment = (CheckFragment) activity.getFragmentManager().findFragmentByTag(TAG);
            if (checkFragment == null) {
                checkFragment = new CheckFragment();
                FragmentManager fragmentManager = activity.getFragmentManager();
                fragmentManager.beginTransaction()
                        .add(checkFragment, TAG)
                        .commitAllowingStateLoss();
                fragmentManager.executePendingTransactions();
            }
        }
    }

    public static PermissionManager build(Activity activity) {
        return new PermissionManager(activity);
    }

    public PermissionManager permissions(final String... permissions) {
        if (permissions == null || permissions.length == 0) {
            throw new IllegalArgumentException("PermissionManager requires at least one input permission");
        }
        this.permissions = permissions;
        return this;
    }

    public void check(CheckCallback checkCallback) {
        if (checkFragment != null) {
            checkFragment.setCheckCallback(checkCallback);
            checkFragment.checkPermissions(permissions);
        }
    }
}
