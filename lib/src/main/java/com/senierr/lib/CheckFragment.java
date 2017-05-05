package com.senierr.lib;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 权限请求代理Fragment
 *
 * @author zhouchunjie
 * @date 2017/5/4
 */

public class CheckFragment extends Fragment {

    private static final int PERMISSIONS_REQUEST_CODE = 66;

    private CheckCallback checkCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void checkPermissions(final List<String> permissions) {
        if (permissions == null || permissions.size() == 0) {
            return;
        }

        if (!isMarshmallow()) {
            Log.d("PermissionManager", "Android sdk < 23!");
            if (checkCallback != null) {
                checkCallback.onAllGranted(permissions);
            }
            return;
        }

        // 检查权限
        List<String> permissionList = new ArrayList<>();
        for (String permission: permissions) {
            if (getActivity().checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        // 请求权限
        if (permissionList.size() > 0) {
            requestPermissions(permissionList.toArray(new String[permissionList.size()]), PERMISSIONS_REQUEST_CODE);
        } else {
            if (checkCallback != null) {
                checkCallback.onAllGranted(permissions);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != PERMISSIONS_REQUEST_CODE) {
            return;
        }

        List<String> deniedByUserList = new ArrayList<>();
        List<String> deniedNoRequestList = new ArrayList<>();

        boolean isAllGranted = true;
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                isAllGranted = false;
                if (!shouldShowRequestPermissionRationale(permissions[i])) {
                    deniedNoRequestList.add(permissions[i]);
                } else {
                    deniedByUserList.add(permissions[i]);
                }
            }
        }

        if (checkCallback != null) {
            if (isAllGranted) {
                checkCallback.onAllGranted(Arrays.asList(permissions));
            } else {
                checkCallback.onDenied(deniedByUserList, deniedNoRequestList);
            }
        }
    }

    boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public CheckCallback getCheckCallback() {
        return checkCallback;
    }

    public void setCheckCallback(CheckCallback checkCallback) {
        this.checkCallback = checkCallback;
    }
}
