package com.sea.user.constant

enum class OrderEnum( val status: Int,  val statusTitle: String) {
    WaitPayment(1, "待付款"),
    WaitDelivery(2, "待发货"),
    AlreadyFinish(3, "已完成"),
    AlreadyCancel(4, "已取消"),
    Invalid(5, "已作废"),
    WaitReceived(6, "待收货");

    companion object {

        fun getOrder(status: Int, paymentStatus: Int): OrderEnum {
            when (status) {
                1 -> {
                    return WaitPayment
                }
                2 -> return if (paymentStatus == 1) {
                    WaitDelivery
                } else {
                    WaitReceived
                }
                3 -> {
                    return AlreadyFinish
                }
                4 -> {
                    return AlreadyCancel
                }
                5 -> {
                    return Invalid
                }
                else -> {
                    return WaitPayment
                }
            }
        }

    }
}