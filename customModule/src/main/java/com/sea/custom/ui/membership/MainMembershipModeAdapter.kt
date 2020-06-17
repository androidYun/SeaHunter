package com.sea.custom.ui.membership

import android.widget.TextView
import com.amap.api.services.geocoder.GeocodeQuery
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.RegeocodeResult
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sea.custom.R
import com.sea.custom.common.Constants
import com.sea.custom.ui.store.StoreListItem
import com.xhs.baselibrary.BaseApplication
import com.xhs.baselibrary.utils.imageLoader.ImageLoader

class MainMembershipModeAdapter(mList: List<StoreListItem>) :
    BaseQuickAdapter<StoreListItem, BaseViewHolder>(
        R.layout.item_mian_club_member_ship_mode_layout,
        mList
    ) {
    override fun convert(helper: BaseViewHolder?, item: StoreListItem) {
        helper?.setText(R.id.tvStoreName, item.title)
        helper?.setText(R.id.tvPhone, item.mobile)
        helper?.setText(R.id.tvWxChat, item.webchat)
        helper?.setText(R.id.tvStoreAddress, item.address)
        ImageLoader.loadImageRoundedCorners(
            helper?.getView(R.id.ivStoreHead),
            Constants.baseUrl.plus(item.img_url), 4
        )
        loadMapView(helper, item)
        if (item.is_join) {
            helper?.setText(R.id.tvMemberShipMode, "已申请")
            helper?.getView<TextView>(R.id.tvMemberShipMode)?.isClickable = false
        } else {
            helper?.setText(R.id.tvMemberShipMode, "申请入会")
            helper?.getView<TextView>(R.id.tvMemberShipMode)?.isClickable = true
        }
        helper?.addOnClickListener(R.id.tvMemberShipMode)
    }

    private fun loadMapView(helper: BaseViewHolder?, item: StoreListItem) {
        val geocodeSearch = GeocodeSearch(BaseApplication.getsInstance())
        geocodeSearch.setOnGeocodeSearchListener(object : GeocodeSearch.OnGeocodeSearchListener {
            override fun onRegeocodeSearched(geocodeResult: RegeocodeResult?, p1: Int) {

            }

            override fun onGeocodeSearched(geocodeResult: GeocodeResult?, p1: Int) {
                if (p1 != 1000 || geocodeResult == null) {
                    return
                }
                val geocodeAddressList = geocodeResult?.geocodeAddressList
                if (!geocodeAddressList.isNullOrEmpty()) {
                    val geocodeAddress = geocodeAddressList[0]
                    ImageLoader.loadImageRoundedCorners(
                        helper?.getView(R.id.ivMapView),
                        "http://restapi.amap.com/v3/staticmap?location=${geocodeAddress.latLonPoint.longitude},${geocodeAddress.latLonPoint.latitude}&zoom=13&size=750*300&markers=mid,,A:116.481485,39.990464&key=bf4b7cf518727bd82c78d18362b1de3a",8)
                }
            }
        })
        val geocodeQuery = GeocodeQuery(item.address, "")

        geocodeSearch.getFromLocationNameAsyn(geocodeQuery)
    }
}