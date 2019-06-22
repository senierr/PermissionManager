package com.senierr.permission

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

/**
 * 扩展函数
 */

fun FragmentActivity.checkPermissions(vararg permissions: String): Boolean {
    return PermissionManager.with(this)
            .permissions(*permissions)
            .check()
}

fun FragmentActivity.requestPermissions(vararg permissions: String, onGrantedCallback: () -> Unit) {
    PermissionManager.with(this)
            .permissions(*permissions)
            .request(object : RequestCallback() {
                override fun onAllGranted() {
                    onGrantedCallback.invoke()
                }
            })
}

fun FragmentActivity.requestPermissions(
        vararg permissions: String,
        onGrantedCallback: () -> Unit,
        onDeniedCallback: ((nextAskList: MutableList<String>, neverAskList: MutableList<String>) -> Unit)? = null
) {
    PermissionManager.with(this)
            .permissions(*permissions)
            .request(object : RequestCallback() {
                override fun onAllGranted() {
                    onGrantedCallback.invoke()
                }

                override fun onDenied(nextAskList: MutableList<String>, neverAskList: MutableList<String>) {
                    onDeniedCallback?.invoke(nextAskList, neverAskList)
                }
            })
}


fun Fragment.checkPermissions(vararg permissions: String): Boolean {
    return PermissionManager.with(this)
            ?.permissions(*permissions)
            ?.check() ?: false
}

fun Fragment.requestPermissions(vararg permissions: String, onGrantedCallback: () -> Unit) {
    PermissionManager.with(this)
            ?.permissions(*permissions)
            ?.request(object : RequestCallback() {
                override fun onAllGranted() {
                    onGrantedCallback.invoke()
                }
            })
}

fun Fragment.requestPermissions(
        vararg permissions: String,
        onGrantedCallback: () -> Unit,
        onDeniedCallback: ((nextAskList: MutableList<String>, neverAskList: MutableList<String>) -> Unit)? = null
) {
    PermissionManager.with(this)
            ?.permissions(*permissions)
            ?.request(object : RequestCallback() {
                override fun onAllGranted() {
                    onGrantedCallback.invoke()
                }

                override fun onDenied(nextAskList: MutableList<String>, neverAskList: MutableList<String>) {
                    onDeniedCallback?.invoke(nextAskList, neverAskList)
                }
            })
}