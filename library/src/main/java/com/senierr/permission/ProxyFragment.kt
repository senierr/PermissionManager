package com.senierr.permission

import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import java.util.*

/**
 * 权限请求代理Fragment
 *
 * @author zhouchunjie
 * @date 2017/5/4
 */
class ProxyFragment : Fragment() {

    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 666
    }

    private var requestCallback: RequestCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true   // 保存实例
    }

    /**
     * 是否6.0以上
     */
    private fun isMarshmallow(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    /**
     * 检查权限
     */
    @TargetApi(Build.VERSION_CODES.M)
    fun check(permissions: Array<out String>?): Boolean {
        if (permissions.isNullOrEmpty() || !isMarshmallow()) return true

        val permissionList = ArrayList<String>()
        for (permission in permissions) {
            if (activity?.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission)
            }
        }
        return permissionList.size == 0
    }

    /**
     * 请求权限
     */
    @TargetApi(Build.VERSION_CODES.M)
    fun request(permissions: Array<out String>?, requestCallback: RequestCallback?) {
        this.requestCallback = requestCallback

        if (permissions.isNullOrEmpty() || !isMarshmallow()) {
            requestCallback?.onAllGranted()
            return
        }

        val permissionList = ArrayList<String>()
        for (permission in permissions) {
            if (activity?.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission)
            }
        }
        if (permissionList.size > 0) {
            requestPermissions(permissionList.toTypedArray(), PERMISSIONS_REQUEST_CODE)
        } else {
            requestCallback?.onAllGranted()
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode != PERMISSIONS_REQUEST_CODE) return

        val nextAskList = mutableListOf<String>()
        val neverAskList = mutableListOf<String>()
        // 检查结果一致性
        if (permissions.size == grantResults.size) {
            for (i in 0 until grantResults.size) {
                val permission = permissions[i]
                val grantResult = grantResults[i]
                // 检查是否授权
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    // 检查是否是"下次询问"
                    if (shouldShowRequestPermissionRationale(permission)) {
                        nextAskList.add(permission)
                    } else {
                        neverAskList.add(permission)
                    }
                }
            }
        }

        if (nextAskList.isEmpty() && neverAskList.isEmpty()) {
            requestCallback?.onAllGranted()
        } else {
            requestCallback?.onDenied(nextAskList, neverAskList)
        }
    }
}