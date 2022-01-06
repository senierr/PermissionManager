package com.senierr.permission

/**
 * 请求结果
 *
 * @author chunjiezhou
 */
sealed class RequestResult {

    /**
     * 全部通过
     */
    object Granted : RequestResult()

    /**
     * 拒绝
     *
     * @param nextAsk 拒绝，下次询问的权限列表
     * @param neverAsk 拒绝，不再询问的权限列表
     */
    data class Denied(
        val nextAsk: List<String>,
        val neverAsk: List<String>
    ): RequestResult()
}