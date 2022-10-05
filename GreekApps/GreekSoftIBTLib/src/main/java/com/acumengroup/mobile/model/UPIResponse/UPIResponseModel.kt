package com.acumengroup.mobile.model.UPIResponse


import com.google.gson.annotations.SerializedName

data class UPIResponseModel(
    @SerializedName("amt")
    val amt: String,
    @SerializedName("auth_code")
    val authCode: String,
    @SerializedName("bank_name")
    val bankName: String,
    @SerializedName("bank_txn")
    val bankTxn: String,
    @SerializedName("CardNumber")
    val cardNumber: String,
    @SerializedName("clientcode")
    val clientcode: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("desc")
    val desc: String,
    @SerializedName("discriminator")
    val discriminator: String,
    @SerializedName("f_code")
    val fCode: String,
    @SerializedName("ipg_txn_id")
    val ipgTxnId: String,
    @SerializedName("mer_txn")
    val merTxn: String,
    @SerializedName("merchant_id")
    val merchantId: String,
    @SerializedName("mmp_txn")
    val mmpTxn: String,
    @SerializedName("prod")
    val prod: String,
    @SerializedName("signature")
    val signature: String,
    @SerializedName("surcharge")
    val surcharge: String,
    @SerializedName("udf1")
    val udf1: String,
    @SerializedName("udf10")
    val udf10: String,
    @SerializedName("udf11")
    val udf11: String,
    @SerializedName("udf12")
    val udf12: String,
    @SerializedName("udf13")
    val udf13: String,
    @SerializedName("udf14")
    val udf14: String,
    @SerializedName("udf15")
    val udf15: String,
    @SerializedName("udf2")
    val udf2: String,
    @SerializedName("udf3")
    val udf3: String,
    @SerializedName("udf4")
    val udf4: String,
    @SerializedName("udf5")
    val udf5: String,
    @SerializedName("udf6")
    val udf6: String,
    @SerializedName("udf9")
    val udf9: String
)