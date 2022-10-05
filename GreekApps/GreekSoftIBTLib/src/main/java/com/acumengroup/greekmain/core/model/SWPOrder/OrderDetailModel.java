
package com.acumengroup.greekmain.core.model.SWPOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OrderDetailModel {

    @SerializedName("iGreekCode")
    @Expose
    private Integer iGreekCode;
    @SerializedName("lLogTime")
    @Expose
    private Integer lLogTime;
    @SerializedName("iErrorCode")
    @Expose
    private Integer iErrorCode;
    @SerializedName("cMemberCode")
    @Expose
    private String cMemberCode;
    @SerializedName("cDate")
    @Expose
    private String cDate;
    @SerializedName("cTime")
    @Expose
    private String cTime;
    @SerializedName("cMFOrderNo")
    @Expose
    private String cMFOrderNo;
    @SerializedName("cSettNo")
    @Expose
    private String cSettNo;
    @SerializedName("cClientCode")
    @Expose
    private String cClientCode;
    @SerializedName("cClientName")
    @Expose
    private String cClientName;
    @SerializedName("cSchemeCode")
    @Expose
    private String cSchemeCode;
    @SerializedName("cSchemeName")
    @Expose
    private String cSchemeName;
    @SerializedName("cISIN")
    @Expose
    private String cISIN;
    @SerializedName("cBuySell")
    @Expose
    private String cBuySell;
    @SerializedName("dAmount")
    @Expose
    private Integer dAmount;
    @SerializedName("dUnits")
    @Expose
    private Integer dUnits;
    @SerializedName("cDPTrans")
    @Expose
    private String cDPTrans;
    @SerializedName("cDPFolioNo")
    @Expose
    private String cDPFolioNo;
    @SerializedName("cFolioNo")
    @Expose
    private String cFolioNo;
    @SerializedName("cEntryBy")
    @Expose
    private String cEntryBy;
    @SerializedName("cOrderStatus")
    @Expose
    private String cOrderStatus;
    @SerializedName("cOrderRemark")
    @Expose
    private String cOrderRemark;
    @SerializedName("cInternalRefNo")
    @Expose
    private String cInternalRefNo;
    @SerializedName("cSettlementType")
    @Expose
    private String cSettlementType;
    @SerializedName("cOrderType")
    @Expose
    private String cOrderType;
    @SerializedName("lSIPRegNo")
    @Expose
    private Integer lSIPRegNo;
    @SerializedName("cSIPRegDate")
    @Expose
    private String cSIPRegDate;
    @SerializedName("cSubBRCode")
    @Expose
    private String cSubBRCode;
    @SerializedName("cEUIN")
    @Expose
    private String cEUIN;
    @SerializedName("cEUINDecl")
    @Expose
    private String cEUINDecl;
    @SerializedName("cAllUnitsFlag")
    @Expose
    private String cAllUnitsFlag;
    @SerializedName("cDPCFlag")
    @Expose
    private String cDPCFlag;
    @SerializedName("cOrderSubType")
    @Expose
    private String cOrderSubType;
    @SerializedName("cFirstOrderToday")
    @Expose
    private String cFirstOrderToday;
    @SerializedName("cPurchaseRedeem")
    @Expose
    private String cPurchaseRedeem;
    @SerializedName("cMemberRemarks")
    @Expose
    private String cMemberRemarks;
    @SerializedName("cKYCFlag")
    @Expose
    private String cKYCFlag;
    @SerializedName("cMINredemptionflag")
    @Expose
    private String cMINredemptionflag;
    @SerializedName("cSubbrokerARN")
    @Expose
    private String cSubbrokerARN;
    @SerializedName("lOurOrderNo")
    @Expose
    private Integer lOurOrderNo;
    @SerializedName("cTransactionCode")
    @Expose
    private String cTransactionCode;
    @SerializedName("cUniqueRefNumber")
    @Expose
    private String cUniqueRefNumber;
    @SerializedName("lOrderId")
    @Expose
    private Integer lOrderId;
    @SerializedName("lUserId")
    @Expose
    private Integer lUserId;
    @SerializedName("cBuySellType")
    @Expose
    private String cBuySellType;
    @SerializedName("cAllRedeem")
    @Expose
    private String cAllRedeem;
    @SerializedName("cSuccessflag")
    @Expose
    private String cSuccessflag;

    public Integer getIGreekCode() {
        return iGreekCode;
    }

    public void setIGreekCode(Integer iGreekCode) {
        this.iGreekCode = iGreekCode;
    }

    public Integer getLLogTime() {
        return lLogTime;
    }

    public void setLLogTime(Integer lLogTime) {
        this.lLogTime = lLogTime;
    }

    public Integer getIErrorCode() {
        return iErrorCode;
    }

    public void setIErrorCode(Integer iErrorCode) {
        this.iErrorCode = iErrorCode;
    }

    public String getCMemberCode() {
        return cMemberCode;
    }

    public void setCMemberCode(String cMemberCode) {
        this.cMemberCode = cMemberCode;
    }

    public String getCDate() {
        return cDate;
    }

    public void setCDate(String cDate) {
        this.cDate = cDate;
    }

    public String getCTime() {
        return cTime;
    }

    public void setCTime(String cTime) {
        this.cTime = cTime;
    }

    public String getCMFOrderNo() {
        return cMFOrderNo;
    }

    public void setCMFOrderNo(String cMFOrderNo) {
        this.cMFOrderNo = cMFOrderNo;
    }

    public String getCSettNo() {
        return cSettNo;
    }

    public void setCSettNo(String cSettNo) {
        this.cSettNo = cSettNo;
    }

    public String getCClientCode() {
        return cClientCode;
    }

    public void setCClientCode(String cClientCode) {
        this.cClientCode = cClientCode;
    }

    public String getCClientName() {
        return cClientName;
    }

    public void setCClientName(String cClientName) {
        this.cClientName = cClientName;
    }

    public String getCSchemeCode() {
        return cSchemeCode;
    }

    public void setCSchemeCode(String cSchemeCode) {
        this.cSchemeCode = cSchemeCode;
    }

    public String getCSchemeName() {
        return cSchemeName;
    }

    public void setCSchemeName(String cSchemeName) {
        this.cSchemeName = cSchemeName;
    }

    public String getCISIN() {
        return cISIN;
    }

    public void setCISIN(String cISIN) {
        this.cISIN = cISIN;
    }

    public String getCBuySell() {
        return cBuySell;
    }

    public void setCBuySell(String cBuySell) {
        this.cBuySell = cBuySell;
    }

    public Integer getDAmount() {
        return dAmount;
    }

    public void setDAmount(Integer dAmount) {
        this.dAmount = dAmount;
    }

    public Integer getDUnits() {
        return dUnits;
    }

    public void setDUnits(Integer dUnits) {
        this.dUnits = dUnits;
    }

    public String getCDPTrans() {
        return cDPTrans;
    }

    public void setCDPTrans(String cDPTrans) {
        this.cDPTrans = cDPTrans;
    }

    public String getCDPFolioNo() {
        return cDPFolioNo;
    }

    public void setCDPFolioNo(String cDPFolioNo) {
        this.cDPFolioNo = cDPFolioNo;
    }

    public String getCFolioNo() {
        return cFolioNo;
    }

    public void setCFolioNo(String cFolioNo) {
        this.cFolioNo = cFolioNo;
    }

    public String getCEntryBy() {
        return cEntryBy;
    }

    public void setCEntryBy(String cEntryBy) {
        this.cEntryBy = cEntryBy;
    }

    public String getCOrderStatus() {
        return cOrderStatus;
    }

    public void setCOrderStatus(String cOrderStatus) {
        this.cOrderStatus = cOrderStatus;
    }

    public String getCOrderRemark() {
        return cOrderRemark;
    }

    public void setCOrderRemark(String cOrderRemark) {
        this.cOrderRemark = cOrderRemark;
    }

    public String getCInternalRefNo() {
        return cInternalRefNo;
    }

    public void setCInternalRefNo(String cInternalRefNo) {
        this.cInternalRefNo = cInternalRefNo;
    }

    public String getCSettlementType() {
        return cSettlementType;
    }

    public void setCSettlementType(String cSettlementType) {
        this.cSettlementType = cSettlementType;
    }

    public String getCOrderType() {
        return cOrderType;
    }

    public void setCOrderType(String cOrderType) {
        this.cOrderType = cOrderType;
    }

    public Integer getLSIPRegNo() {
        return lSIPRegNo;
    }

    public void setLSIPRegNo(Integer lSIPRegNo) {
        this.lSIPRegNo = lSIPRegNo;
    }

    public String getCSIPRegDate() {
        return cSIPRegDate;
    }

    public void setCSIPRegDate(String cSIPRegDate) {
        this.cSIPRegDate = cSIPRegDate;
    }

    public String getCSubBRCode() {
        return cSubBRCode;
    }

    public void setCSubBRCode(String cSubBRCode) {
        this.cSubBRCode = cSubBRCode;
    }

    public String getCEUIN() {
        return cEUIN;
    }

    public void setCEUIN(String cEUIN) {
        this.cEUIN = cEUIN;
    }

    public String getCEUINDecl() {
        return cEUINDecl;
    }

    public void setCEUINDecl(String cEUINDecl) {
        this.cEUINDecl = cEUINDecl;
    }

    public String getCAllUnitsFlag() {
        return cAllUnitsFlag;
    }

    public void setCAllUnitsFlag(String cAllUnitsFlag) {
        this.cAllUnitsFlag = cAllUnitsFlag;
    }

    public String getCDPCFlag() {
        return cDPCFlag;
    }

    public void setCDPCFlag(String cDPCFlag) {
        this.cDPCFlag = cDPCFlag;
    }

    public String getCOrderSubType() {
        return cOrderSubType;
    }

    public void setCOrderSubType(String cOrderSubType) {
        this.cOrderSubType = cOrderSubType;
    }

    public String getCFirstOrderToday() {
        return cFirstOrderToday;
    }

    public void setCFirstOrderToday(String cFirstOrderToday) {
        this.cFirstOrderToday = cFirstOrderToday;
    }

    public String getCPurchaseRedeem() {
        return cPurchaseRedeem;
    }

    public void setCPurchaseRedeem(String cPurchaseRedeem) {
        this.cPurchaseRedeem = cPurchaseRedeem;
    }

    public String getCMemberRemarks() {
        return cMemberRemarks;
    }

    public void setCMemberRemarks(String cMemberRemarks) {
        this.cMemberRemarks = cMemberRemarks;
    }

    public String getCKYCFlag() {
        return cKYCFlag;
    }

    public void setCKYCFlag(String cKYCFlag) {
        this.cKYCFlag = cKYCFlag;
    }

    public String getCMINredemptionflag() {
        return cMINredemptionflag;
    }

    public void setCMINredemptionflag(String cMINredemptionflag) {
        this.cMINredemptionflag = cMINredemptionflag;
    }

    public String getCSubbrokerARN() {
        return cSubbrokerARN;
    }

    public void setCSubbrokerARN(String cSubbrokerARN) {
        this.cSubbrokerARN = cSubbrokerARN;
    }

    public Integer getLOurOrderNo() {
        return lOurOrderNo;
    }

    public void setLOurOrderNo(Integer lOurOrderNo) {
        this.lOurOrderNo = lOurOrderNo;
    }

    public String getCTransactionCode() {
        return cTransactionCode;
    }

    public void setCTransactionCode(String cTransactionCode) {
        this.cTransactionCode = cTransactionCode;
    }

    public String getCUniqueRefNumber() {
        return cUniqueRefNumber;
    }

    public void setCUniqueRefNumber(String cUniqueRefNumber) {
        this.cUniqueRefNumber = cUniqueRefNumber;
    }

    public Integer getLOrderId() {
        return lOrderId;
    }

    public void setLOrderId(Integer lOrderId) {
        this.lOrderId = lOrderId;
    }

    public Integer getLUserId() {
        return lUserId;
    }

    public void setLUserId(Integer lUserId) {
        this.lUserId = lUserId;
    }

    public String getCBuySellType() {
        return cBuySellType;
    }

    public void setCBuySellType(String cBuySellType) {
        this.cBuySellType = cBuySellType;
    }

    public String getCAllRedeem() {
        return cAllRedeem;
    }

    public void setCAllRedeem(String cAllRedeem) {
        this.cAllRedeem = cAllRedeem;
    }

    public String getCSuccessflag() {
        return cSuccessflag;
    }

    public void setCSuccessflag(String cSuccessflag) {
        this.cSuccessflag = cSuccessflag;
    }

}
