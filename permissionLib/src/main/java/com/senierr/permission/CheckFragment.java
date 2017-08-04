package com.senierr.permission;

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
    public boolean check(final String[] permissions) {
        if (permissions == null || permissions.length == 0) {
            return true;
        }

        if (!isMarshmallow()) {
            Log.d("PermissionManager", "Android sdk < 23!");
            return true;
        }

        List<String> permissionList = new ArrayList<>();
        for (String permission: permissions) {
            if (getActivity().checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }

        return permissionList.size() == 0;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void checkAndRequest(final String[] permissions) {
        if (permissions == null || permissions.length == 0) {
            return;
        }

        if (!isMarshmallow()) {
            Log.d("PermissionManager", "Android sdk < 23!");
            if (checkCallback != null) {
                checkCallback.onAllGranted();
            }
            return;
        }

        List<String> permissionList = new ArrayList<>();
        for (String permission: permissions) {
            if (getActivity().checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        if (permissionList.size() > 0) {
            requestPermissions(permissionList.toArray(new String[permissionList.size()]), PERMISSIONS_REQUEST_CODE);
        } else {
            if (checkCallback != null) {
                checkCallback.onAllGranted();
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
        List<String> deniedWithNextAskList = new ArrayList<>();
        List<String> deniedWithNoAskList = new ArrayList<>();

        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                isAllGranted = false;
                if (shouldShowRequestPermissionRationale(permissions[i])) {
                    deniedWithNextAskList.add(permissions[i]);
                } else {
                    deniedWithNoAskList.add(permissions[i]);
                }
            }
        }

        if (checkCallback != null) {
            if (isAllGranted) {
                checkCallback.onAllGranted();
            } else {
                checkCallback.onDenied(deniedWithNextAskList, deniedWithNoAskList);
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
