package com.acumengroup.mobile.model

data class SecurityInfoModel(
    val ErrorCode: Int,
    val `data`: ArrayList<SecurityData>,
    val message: String,
    val success: String
) {
    data class SecurityData(
        val bsecode: String,
        val bsemcap: String,
        val bv: String,
        val co_code: String,
        val co_name: String,
        val complongname: String,
        val divyield: String,
        val eps: String,
        val fv: String,
        val industryname: String,
        val isin: String,
        val nsemcap: String,
        val pb: String,
        val pe: String,
        val symbol: String
    )
}