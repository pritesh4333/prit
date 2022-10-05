// ignore_for_file: constant_identifier_names

enum LoginImage {
  login_bg_image,
}

extension LoginImageExtension on LoginImage {
  String get name {
    switch (this) {
      case LoginImage.login_bg_image:
        return "lib/Resources/Images/login_bg_image.png";
    }
  }
}

enum CompanyImage {
  company_logo,
}

extension CompanyImageExtension on CompanyImage {
  String get name {
    switch (this) {
      case CompanyImage.company_logo:
        return "lib/Resources/Images/vishwas_logo.png";
    }
  }
}

enum DashboardImage { home_authorized_user_icon, home_completed_user_icon, home_finish_user_icon, home_first_time_user_icon, home_in_process_icon, home_otp_verified_icon, home_pan_verified_icon, email, phonecall, log_out }

extension DashboardImageExtension on DashboardImage {
  String get name {
    switch (this) {
      case DashboardImage.home_authorized_user_icon:
        return "lib/Resources/Images/home_authorized_user_icon.png";
      case DashboardImage.home_completed_user_icon:
        return "lib/Resources/Images/home_completed_user_icon.png";
      case DashboardImage.home_finish_user_icon:
        return "lib/Resources/Images/home_finish_user_icon.png";
      case DashboardImage.home_first_time_user_icon:
        return "lib/Resources/Images/home_first_time_user_icon.png";
      case DashboardImage.home_in_process_icon:
        return "lib/Resources/Images/home_in_process_icon.png";
      case DashboardImage.home_otp_verified_icon:
        return "lib/Resources/Images/home_otp_verified_icon.png";
      case DashboardImage.home_pan_verified_icon:
        return "lib/Resources/Images/home_pan_verified_icon.png";
      case DashboardImage.email:
        return "lib/Resources/Images/email.png";
      case DashboardImage.phonecall:
        return "lib/Resources/Images/phonecall.png";
      case DashboardImage.log_out:
        return "lib/Resources/Images/log_out.png";
    }
  }
}

enum CommonImage {
  empty_cart,
  placeholder_doc_image,
}

extension CommonImageExtension on CommonImage {
  String get name {
    switch (this) {
      case CommonImage.empty_cart:
        return "lib/Resources/Images/empty_cart.png";
      case CommonImage.placeholder_doc_image:
        return "lib/Resources/Images/placeholder_doc_image.png";
    }
  }
}
