package com.xhs.baselibrary.ui.update

import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.api.UpdateApi
import com.xhs.baselibrary.net.loaddown.JsDownloadListener
import com.xhs.baselibrary.net.loaddown.UpdateRetrofitUtils
import com.xhs.baselibrary.utils.SystemUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.*


/**
 * @ author guiyun.li
 * @ Email xyz_6776.@163.com
 * @ date 17/09/2019.
 * description:
 */
class UpdatePresenter : IPresenter<UpdateContact.UpdateView>(), UpdateContact.UpdatePresenter {


    override fun updateApk(apkUrl: String, apkName: String) {
        val apkFile = File(SystemUtils.getApkPath(), apkName)
        if (apkFile != null && apkFile.exists() && SystemUtils.isAppInstalled(apkFile.absolutePath)) {
            SystemUtils.install(apkFile.absolutePath)
            return
        }
        val retrofit = UpdateRetrofitUtils.getRetrofit(apkUrl, object : JsDownloadListener {
            override fun onStartDownload(length: Long) {
                softView.get()?.onStartDownload(length)
            }

            override fun onProgress(progress: Int) {
                softView.get()?.updateProgress(progress)
            }

            override fun onFail(errorInfo: String?) {
                softView.get()?.updateFail(Throwable(errorInfo))
            }
        })
        retrofit.create(UpdateApi::class.java).download(apkName)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map { t -> t.byteStream() }
                .observeOn(Schedulers.computation())
                .map { t ->
                    writeFile(t, apkName)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t ->
                    softView.get()?.updateFinish(t)
                }, { error ->
                    softView.get()?.updateFail(Throwable(error.message))
                })

    }

    /**
     * 将输入流写入文件
     *
     * @param inputStream
     * @param apkName
     */
    private fun writeFile(inputStream: InputStream, apkName: String): String {
        val apkFile = File(SystemUtils.getApkPath(), apkName)
        if (!apkFile.exists()) {
            apkFile.mkdirs()
        }
        // 指定下载文件地址，使用这个指定地址可不需要WRITE_EXTERNAL_STORAGE权限。
        if (apkFile.exists()) {
            apkFile.delete()
        }
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(apkFile)
            val buffer = ByteArray(1024)
            var len: Int = -1
            while ({ len = inputStream.read(buffer);len }() > 0) {
                fos.write(buffer, 0, len)
            }
            inputStream.close()
            fos.close()
            return apkFile.absolutePath
        } catch (e: FileNotFoundException) {
            softView.get()?.updateFail(Throwable(""));
        } catch (e: IOException) {
            softView.get()?.updateFail(Throwable(""));
        }
        return ""
    }

}
