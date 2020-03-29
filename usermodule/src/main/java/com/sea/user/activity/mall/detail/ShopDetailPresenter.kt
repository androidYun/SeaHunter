package com.sea.user.activity.mall.detail

import com.sea.user.api.ShopApi
import com.xhs.baselibrary.base.IPresenter
import com.xhs.baselibrary.net.retrifit.RetrofitUtils
import com.xhs.baselibrary.net.util.RxUtils


class ShopDetailPresenter : IPresenter<ShopDetailContact.IShopDetailView>(),
    ShopDetailContact.IShopDetailPresenter {
    override fun loadShopDetail(nShopDetailModelReq: NShopDetailModelReq) {
        RetrofitUtils.getRetrofit()
            .create(ShopApi::class.java)
            .loadShopDetail(nShopDetailModelReq)
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
                    if (it.data != null && it.code == 1) {
                        softView.get()?.loadShopDetailSuccess(
                            NShopDetailModel(
                                id = it.data.id,
                                bannerList = getImageList(it.data.img_url, it.data.albums),
                                paramsList = getParamsList(it.data.title, it.data.fields),
                                title = it.data.title,
                                content = it.data.content,
                                tags = it.data.tags,
                                saleNumber = getSaleNumber(it.data.fields),
                                sellPrice = it.data.fields.sell_price,
                                channelId = it.data.channel_id,
                                specs = it.specs,
                                goods = it.data.goods,
                                point = it.data.fields.point
                            )
                        )
                    } else {
                        softView.get()?.loadShopDetailFail(Throwable(it.msg))
                    }
                    //这里面是回调成功的方法
                }, { throwable -> softView.get()?.loadShopDetailFail(throwable) }
            )
    }

    private fun getImageList(imgUrl: String, albums: List<String>): List<String> {
        if (albums.isNullOrEmpty()) {
            return listOf(imgUrl)
        }
        return albums
    }

    private fun getSaleNumber(fields: Fields): String {
        return "已售：${fields.sale_num}    库存：${fields.stock_quantity} "
    }

    private fun getParamsList(title: String, fields: Fields): List<NShopParamsItem> {
        val paramsList = mutableListOf<NShopParamsItem>()
        paramsList.add(NShopParamsItem("商品名称:", title))
        paramsList.add(NShopParamsItem("商品重量:", fields.weight))
        paramsList.add(NShopParamsItem("产地:", fields.place))
        paramsList.add(NShopParamsItem("人工编号:", fields.worker_no))
        paramsList.add(NShopParamsItem("生产编码:", fields.product_code))
        paramsList.add(NShopParamsItem("食用方式:", fields.eat_way))
        return paramsList
    }


}