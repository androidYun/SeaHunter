package com.sea.user.presenter.update

import com.sea.user.api.CommonApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils
import okhttp3.MediaType
import okhttp3.MultipartBody

import okhttp3.RequestBody
import java.io.File

/**
 *file部分，最终拼接成以下部分（注意“app_user_header”是后台定义好的，后台会用它作为key去查询你传的图片信息）：
 *Content-Disposition: form-data; name="app_user_header"; filename=fileNameByTimeStamp
 *Content-Type: image/jpeg
 *Content-Length: 52251(图片流字节数组的长度，底层的Okhttp帮我们计算了)
 *...(文件流)
 */
/**
 * file部分，最终拼接成以下部分（注意“app_user_header”是后台定义好的，后台会用它作为key去查询你传的图片信息）：
 * Content-Disposition: form-data; name="app_user_header"; filename=fileNameByTimeStamp
 * Content-Type: image/jpeg
 * Content-Length: 52251(图片流字节数组的长度，底层的Okhttp帮我们计算了)
 * ...(文件流)
 */
class UpdateImagePresenter : IPresenter<UpdateImageContact.IUpdateImageView>(),
    UpdateImageContact.IUpdateImagePresenter {
    override fun loadUpdateImage(type: String,imagePath: String) {
        //图片文件
        //图片文件
        val file = File(imagePath)
        val requestFile: RequestBody = RequestBody.create(MediaType.parse("image/jpeg"), file)

        val body = MultipartBody.Part.createFormData("","", requestFile)
        RetrofitUtils.getRetrofit()
            .create(CommonApi::class.java)
            .loadUpdateImage(type,body)
            .compose(RxUtils.getSchedulerTransformer())
            .compose(RxUtils.bindToLifecycle(softView.get()))
            .doOnSubscribe { disposable ->
                addDisposable(disposable)
                softView.get()?.showLoading()
            }.doFinally {
                softView.get()?.hideLoading()
                onStop()
            }
            .subscribe(
                {
                    if (it.code == 1) {
                        softView.get()?.loadUpdateImageSuccess(it.img_url)
                    } else {
                        softView.get()?.loadUpdateImageFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadUpdateImageFail(throwable) }
            )
    }
}