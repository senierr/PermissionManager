package com.senierr.lib;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
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
    public void checkPermissions(final String[] permissions) {
        if (permissions == null || permissions.length == 0) {
            return;
        }

        if (!isMarshmallow()) {
            Log.d("PermissionManager", "Android sdk < 23!");
            if (checkCallback != null) {
                checkCallback.onAllGranted(permissions);
            }
            return;
        }

        List<String> permissionList = new ArrayList<>();
        for (String permission: permissions) {
            if (getActivity().checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            } else {
                if (checkCallback != null) {
                    checkCallback.onGranted(permission);
                }
            }
        }
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

        boolean isAllGranted = true;
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                if (checkCallback != null) {
                    checkCallback.onDenied(permissions[i], !shouldShowRequestPermissionRationale(permissions[i]));
                }
                isAllGranted = false;
            } else {
                if (checkCallback != null) {
                    checkCallback.onGranted(permissions[i]);
                }
            }
        }

        if (checkCallback != null && isAllGranted) {
            checkCallback.onAllGranted(permissions);
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
