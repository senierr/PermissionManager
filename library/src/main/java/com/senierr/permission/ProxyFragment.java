package com.senierr.permission;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限请求代理Fragment
 *
 * @author zhouchunjie
 * @date 2017/5/4
 */
public class ProxyFragment extends Fragment {

    private static final int PERMISSIONS_REQUEST_CODE = 66;

    private RequestCallback requestCallback;

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
    public void request(final String[] permissions) {
        if (permissions == null || permissions.length == 0) {
            return;
        }

        if (!isMarshmallow()) {
            if (requestCallback != null) {
                requestCallback.onAllGranted();
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
            if (requestCallback != null) {
                requestCallback.onAllGranted();
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

        if (requestCallback != null) {
            if (isAllGranted) {
                requestCallback.onAllGranted();
            } else {
                requestCallback.onDenied(deniedWithNextAskList, deniedWithNoAskList);
            }
        }
    }

    boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public RequestCallback getRequestCallback() {
        return requestCallback;
    }

    public void setRequestCallback(RequestCallback requestCallback) {
        this.requestCallback = requestCallback;
    }
}
