package com.sea.user.constant

enum class OrderEnum(val status: Int, val statusTitle: String, val content: String = "") {
    WaitPayment(1, "待付款", "您的订单待付款,快去付款吧!"),
    WaitDelivery(2, "待发货", "您的订单已揽收"),
    AlreadyFinish(3, "已完成", "您的订单已完成"),
    AlreadyCancel(4, "已取消", "您的订单已取消"),
    Invalid(5, "已作废", "您的订单已作废"),
    WaitReceived(6, "待收货", "您的订单正在运输当中");

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