// ignore_for_file: constant_identifier_names

enum AppPlatform {
  mobile,
  web,
}

enum GreekLicenseValidity {
  monthly,
  quarterly,
}

enum GreekLicenseMode {
  server, // 1
  user, // 0
}

enum LicenseRequestStatus {
  Pending,
  Approved,
  Rejected,
  Cancel,
  Completed,
  Mail_Sent,
  Mail_Not_Sent,
  Invalid,
}

enum APINames {
  get_server_details,
  getReportData,
  authenticate_license_user,
  get_product_details,
  get_license_max_issue_no,
  CTCL_Confirmation_Details,
  get_license_request,
  license_request,
  update_license_request,
  server_settings,
  get_pending_license_request_count,
  get_client_name_list,
  get_license_request_by_status,
  get_license_request_status,
  updateEkycFromAdmin,
  logout,
  Product_Mapping_Details,
  eKycAdminLogin,
  getAllStats,
  getUserDetails,
  getStatusReport,
  getPennyDropReport,
  getReferalReport,
  getuserRights,
  downloadUserDoc,
  downloadZipBackup,
  //Stub names
  getGlobalSearch,
  getPaymentReport,
  getDataAnalysisReport,
  getReIpvReport,
  getAuthorizeUserReport,
  getRejectionReport,
  viewDocuments,
  //ReKyc
  getReKycStatusReport,
  getReKycDataAnalysisReport,
  getReKycGlobalSearchReport,
}

enum DocumentType {
  pancard,
  signature,
  bankproof,
  addressproof,
  incomeproof,
  photograph,
}
