package com.manyu.cashcard

data class SellData(
    var currentUser: String? = null,
    var spinner: String? = null,
    var cardNumber: String? = null,
    var cardCode: String? = null,
    var expDate: String? = null,
    var sellingPrice: String? = null,
    var actualPrice: String? = null,
    var email:String? = null,
)
