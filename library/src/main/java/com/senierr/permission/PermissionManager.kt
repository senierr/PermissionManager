package com.senierr.permission

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

/**
 * 权限管理
 *
 * @author zhouchunjie
 * @date 2017/3/6
 */
class PermissionManager private constructor(activity: FragmentActivity) {

    companion object {

        private val TAG = PermissionManager::class.java.simpleName

        @JvmStatic
        fun with(activity: FragmentActivity): PermissionManager {
            return PermissionManager(activity)
        }

        @JvmStatic
        fun with(fragment: Fragment): PermissionManager? {
            val activity = fragment.activity
            return if (activity != null) PermissionManager(activity) else null
        }
    }

    private var proxyFragment: ProxyFragment? = null
    private var permissions: Array<out String>? = null

    init {
        proxyFragment = activity.supportFragmentManager.findFragmentByTag(TAG) as ProxyFragment?
        if (proxyFragment == null) {
            proxyFragment = ProxyFragment()
            proxyFragment?.let {
                val fragmentManager = activity.supportFragmentManager
                fragmentManager.beginTransaction()
                        .add(it, TAG)
                        .commitAllowingStateLoss()
                fragmentManager.executePendingTransactions()
            }
        }
    }

    /**
     * 添加权限
     */
    fun permissions(vararg permissions: String): PermissionManager {
        if (permissions.isEmpty()) throw IllegalArgumentException("PermissionManager requires at least one input permission!")
        this.permissions = permissions
        return this
    }

    /**
     * 检查权限
     */
    fun check() = proxyFragment?.check(permissions) ?: false

    /**
     * 请求权限
     */
    fun request(requestCallback: RequestCallback) {
        proxyFragment?.request(permissions, requestCallback)
    }
}