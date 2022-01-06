package com.senierr.permission

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

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

suspend fun FragmentActivity.requestPermissions(vararg permissions: String): RequestResult {
    return suspendCancellableCoroutine { coroutine ->
        PermissionManager.with(this)
            .permissions(*permissions)
            .request(object : RequestCallback() {
                override fun onAllGranted() {
                    coroutine.resume(RequestResult.Granted)
                }

                override fun onDenied(
                    nextAskList: MutableList<String>,
                    neverAskList: MutableList<String>
                ) {
                    coroutine.resume(RequestResult.Denied(nextAskList, neverAskList))
                }
            })
    }
}

suspend fun Fragment.requestPermissions(vararg permissions: String): RequestResult {
    return suspendCancellableCoroutine { coroutine ->
        PermissionManager.with(this)
            ?.permissions(*permissions)
            ?.request(object : RequestCallback() {
                override fun onAllGranted() {
                    coroutine.resume(RequestResult.Granted)
                }

                override fun onDenied(
                    nextAskList: MutableList<String>,
                    neverAskList: MutableList<String>
                ) {
                    coroutine.resume(RequestResult.Denied(nextAskList, neverAskList))
                }
            })
    }
}