package com.xhs.baselibrary.net.interceptor

import com.xhs.baselibrary.router.RouterManager
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import org.json.JSONObject
import java.io.EOFException
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException


class AuthorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var response = chain.proceed(request)
        response.body()?.let {
            val responseBody = getResponseBody(it)
            val jsonObject = JSONObject(responseBody)
            val code = jsonObject.get("code")
            if (code == 401) {
                RouterManager.prisonRouter?.loginOut()
            }
        }
        if (response.code() == 401) {
            RouterManager.prisonRouter?.loginOut()
        }
        return response
    }

    private val UTF8 = Charset.forName("UTF-8")
    private fun getResponseBody(responseBody: ResponseBody): String? {
        val source = responseBody.source()
        source.request(java.lang.Long.MAX_VALUE)
        val buffer = source.buffer()

        var charset = UTF8
        val contentType = responseBody.contentType()
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8)
            } catch (e: UnsupportedCharsetException) {
            }
        }

        if (!isPlaintext(buffer)) {
            return null
        }

        return if (responseBody.contentLength() != 0L) {
            buffer.clone().readString(charset)
        } else null
    }

    private fun isPlaintext(buffer: Buffer): Boolean {
        try {
            val prefix = Buffer()
            val byteCount = (if (buffer.size() < 64) buffer.size() else 64).toLong()
            buffer.copyTo(prefix, 0, byteCount)
            for (i in 0..15) {
                if (prefix.exhausted()) {
                    break
                }
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false
                }
            }
            return true
        } catch (e: EOFException) {
            return false
        }

    }
}