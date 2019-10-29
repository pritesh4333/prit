package in.co.vyapari.middleware;

import java.util.ArrayList;
import java.util.List;

import in.co.vyapari.model.ArApCollection;
import in.co.vyapari.model.Company;
import in.co.vyapari.model.Currency;
import in.co.vyapari.model.Dashboard;
import in.co.vyapari.model.Firm;
import in.co.vyapari.model.Invoice;
import in.co.vyapari.model.KeyValue;
import in.co.vyapari.model.Login;
import in.co.vyapari.model.Product;
import in.co.vyapari.model.Register;
import in.co.vyapari.model.UpdateLogo;
import in.co.vyapari.model.UpdatePassword;
import in.co.vyapari.model.User;
import in.co.vyapari.model.VersionControl;
import in.co.vyapari.model.request.Crash;
import in.co.vyapari.model.request.ImageUpload;
import in.co.vyapari.model.request.NotificationConfig;
import in.co.vyapari.model.request.filter.Filter;
import in.co.vyapari.model.response.BaseModel;
import in.co.vyapari.model.response.BaseModelV;
import in.co.vyapari.model.response.dto.ArApCollectionDTO;
import in.co.vyapari.model.response.dto.CollectionTypeDTO;
import in.co.vyapari.model.response.dto.CompanyDTO;
import in.co.vyapari.model.response.dto.ExchangeRateDTO;
import in.co.vyapari.model.response.dto.FirmDTO;
import in.co.vyapari.model.response.dto.FirmsSumDTO;
import in.co.vyapari.model.response.dto.GstinDTO;
import in.co.vyapari.model.response.dto.GstinForVyapariDTO;
import in.co.vyapari.model.response.dto.InvoiceDTO;
import in.co.vyapari.model.response.dto.InvoicesSumDTO;
import in.co.vyapari.model.response.dto.LoginDTO;
import in.co.vyapari.model.response.dto.PlaceofSupplyDTO;
import in.co.vyapari.model.response.dto.ProductDTO;
import in.co.vyapari.model.response.dto.ProductsSumDTO;
import in.co.vyapari.model.response.dto.SectorDTO;
import in.co.vyapari.model.response.dto.UserDTO;
import in.co.vyapari.model.response.dto.promocodeDTO;
import in.co.vyapari.model.response.summary.CollectionSum;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Bekir.Dursun on 3.10.2017.
 */
public interface ServiceRequest {

    // User
    @POST("/api/v1.0/user/signup")
    Call<BaseModelV<String>> doRegister(@Body Register register);   // portal

    @POST("/api/user/login")
    Call<BaseModel<LoginDTO>> doLogin(@Body Login login);

    @POST("/api/user/logout")
    Call<BaseModel<String>> logout();

    @POST("/api/user/forgot")
    Call<BaseModel<String>> lostPW(@Query("Email") String email);

    @PUT("/api/user")
    Call<BaseModel<User>> updateProfile(@Body User userRequest);

    @POST("/api/tenant/uploadlogo")
    Call<BaseModel<String>> uploadLogo(@Body ImageUpload imageUpload);

    @POST("/api/tenant/removelogo")
    Call<BaseModel<String>> removeLogo();

    @GET("/api/v1.0/common/terms")
    Call<BaseModelV<String>> getUsageContract();    // portal
    // User


    // Common
    @POST("/api/Common/NotificationConfig")
    Call<BaseModel<Boolean>> setNotificationConfig(@Body NotificationConfig notificationConfig);

    @GET("/api/Common/exchangeRates")
    Call<BaseModel<ExchangeRateDTO>> doExchangeRate(@Query("from") String from, @Query("to") String to);

    @GET("/api/Common/productTypes")
    Call<BaseModel<ArrayList<KeyValue>>> getProductTypes();

    @GET("/api/Common/productUnits")
    Call<BaseModel<ArrayList<KeyValue>>> getUnitTypes();

    @GET("/api/Common/ArpSlipNumber")
    Call<BaseModel<String>> getArapNumber();

    @GET("/api/Common/GetPortalToken")
    Call<BaseModel<KeyValue>> GetPortalToken();

    @GET("/api/Common/GetUnits")
    Call<BaseModel<ArrayList<KeyValue>>> getSubUnitTypes(@Query("unitSetCode") String unitSetCode);

    @GET("/api/Common/HSNSACCodes")
    Call<BaseModel<ArrayList<KeyValue>>> getHSNSACCodes(@Query("productType") String productType, @Query("text") String text);

    @GET("/api/Common/GSTCodes")
    Call<BaseModel<ArrayList<KeyValue>>> getGSTCodes();

    @GET("/api/Common/CESS")
    Call<BaseModel<ArrayList<KeyValue>>> getCESS();

    @GET("/api/Common/PaymentTypes")
    Call<BaseModel<ArrayList<KeyValue>>> getPaymentTypes();

    @GET("/api/Common/CurrencyCodes")
    Call<BaseModel<ArrayList<Currency>>> getCurrencyCodes();

    @GET("/api/Common/Countries")
    Call<BaseModel<ArrayList<KeyValue>>> getCountries();

    @GET("/api/Common/States")
    Call<BaseModel<ArrayList<KeyValue>>> getStates(@Query("parentId") String parentId);

    @GET("/api/Common/DashbordInformation")
    Call<BaseModel<ArrayList<KeyValue>>> DashbordInformation();

    @GET("/api/Common/TopItems")
    Call<BaseModel<ArrayList<KeyValue>>> TopItems(@Query("QueryBase") String parentId);

    @GET("/api/Common/TopCustomers")
    Call<BaseModel<ArrayList<KeyValue>>> TopCustomers();

    @GET("/api/Common/GetCollectionTypes")
    Call<BaseModel<List<CollectionTypeDTO>>> GetCollectionTypes();

    @GET("/api/Common/Cities")
    Call<BaseModel<ArrayList<KeyValue>>> getCities(@Query("parentId") String parentId);

    @GET("/api/Common/Districts")
    Call<BaseModel<ArrayList<KeyValue>>> getDistricts(@Query("parentId") String parentId);

    @GET("/api/Common/ZipCodes")
    Call<BaseModel<ArrayList<KeyValue>>> getZipCodes(@Query("parentId") String parentId);

    @GET("/api/Common/Warehouses")
    Call<BaseModel<ArrayList<KeyValue>>> getWarehouses();

    @GET("/api/Common/invoiceNumber")
    Call<BaseModel<String>> getInvoiceNumber();

    @GET("/api/Common/validateTaxNumber")
    Call<BaseModel<GstinDTO>> validGSTIN(@Query("taxNumber") String gstin);

    @GET("/api/Common/validateTaxNumber")
    Call<BaseModel<GstinForVyapariDTO>> validGSTINForVyapari(@Query("taxNumber") String gstin); // portal

    @GET("/api/v1.0/distributor/promocode/validate/{promoCode}")
    Call<BaseModelV<promocodeDTO>> validPromoCode(@Path("promoCode") String promo); // portal

    @PUT("/api/v1.0/tenant/{tenantId}/promocode/{promoCode}")
    Call<BaseModelV<String>> validPromoCodecall(@Path("tenantId") String tententid,@Path("promoCode") String promocode); // portal

    @PUT("/api/v1.0/tenant")
    Call<BaseModelV<String>> updatecompanylogo(@Body   UpdateLogo updatelogo); // portal

    @POST("/api/v1.0/user/password/change")
    Call<BaseModelV<String>> updatepassword(@Body   UpdatePassword updatepass); // portal

    @PUT("/api/v1.0/tenant/{tenantId}/state")
    Call<BaseModelV<String>> Deactiveaccount(@Path("tenantId") String tenentid, @Body String state); // portal

    @GET("/api/v1.0/user/validate/{token}")
    Call<BaseModelV<UserDTO>> getUserDatacall( @Path("token") String token ); // portal


    @POST("/api/Common/crash")
    Call<Boolean> addCrashLog(@Body Crash crash);

    @GET("/api/Common/VersionControl")
    Call<BaseModel<VersionControl>> getVersionControl();

    @GET("/api/v1.0/common/sectors")
    Call<BaseModelV<List<SectorDTO>>> getSectorList();  // portal

    @GET("/api/Common/purchaseInvoiceNumber")
    Call<BaseModel<String>> getPurchaseInvoiceNumber();
    // Common


    // Firm
    @POST("/api/firm/search")
    Call<BaseModel<FirmsSumDTO>> getFirms(@Body Filter filter);

    @GET("/api/firm/{id}")
    Call<BaseModel<FirmDTO>> getFirmDetail(@Path("id") String id);

    @POST("/api/firm")
    Call<BaseModel<FirmDTO>> addFirm(@Body Firm firm);

    @PUT("/api/firm")
    Call<BaseModel<FirmDTO>> updateFirm(@Body Firm firmRequest);

    @DELETE("/api/firm/{id}")
    Call<BaseModel<FirmDTO>> deleteFirm(@Path("id") String id);

    @DELETE("/api/firm/ShipmentAddress")
    Call<BaseModel<String>> deleteAddress(@Query("id") String id, @Query("firmId") String firmId);

    @DELETE("/api/firm/Employee/{id}")
    Call<BaseModel<String>> deleteEmployee(@Path("id") String id);

    @POST("/api/firm/ArpSlip")
    Call<BaseModel<ArApCollectionDTO>> saveArap(@Query("slipType") int i, @Body ArApCollection ArapCollection);

    @POST("/api/firm/searchArpSlip")
    Call<BaseModel<List<CollectionSum>>> getARAPSlips(@Body Filter filter);
    // Firm


    // Product
    @POST("/api/Product/search")
    Call<BaseModel<ProductsSumDTO>> getProducts(@Body Filter filter);

    @GET("/api/Product/{id}")
    Call<BaseModel<ProductDTO>> getProductDetail(@Path("id") String id);

    @GET("/api/Product/GetProductByBarcode")
    Call<BaseModel<ProductDTO>> getProductDetailWithCode(@Query("barcode") String barcode);



    @POST("/api/Product")
    Call<BaseModel<ProductDTO>> addProduct(@Body Product product);

    @PUT("/api/Product")
    Call<BaseModel<ProductDTO>> updateProduct(@Body Product product);

    @DELETE("/api/Product/{id}")
    Call<BaseModel<ProductDTO>> deleteProduct(@Path("id") String id);
    // Product


    // Company
    @PUT("/api/tenant")
    Call<BaseModel<CompanyDTO>> updateCompany(@Body Company companyRequest);
    // Company


    // Invoice
    @POST("/api/Invoice/calculateInvoice")
    Call<BaseModel<InvoiceDTO>> calculateInvoice(@Body Invoice invoice);

    @GET("/api/Invoice/printUrl")
    Call<BaseModel<String>> getPrintUrl(@Query("id") String id);

    @POST("/api/Invoice/search")
    Call<BaseModel<InvoicesSumDTO>> getInvoices(@Body Filter filter);

    @GET("/api/Invoice/getByType")
    Call<BaseModel<InvoiceDTO>> getInvoiceDetail(@Query("id") String id, @Query("invoiceType") int invoiceType);

    @GET("/api/Invoice/getByType")
    Call<BaseModel<Invoice>> getInvoiceDetailEdit(@Query("id") String id, @Query("invoiceType") int invoiceType);

    @GET("/api/firm/getArpSlip")
    Call<BaseModel<ArApCollectionDTO>> getArpSlip(@Query("id") String id);

    @GET("/api/Common/InvoicPrintInfo")
    Call<BaseModel<PlaceofSupplyDTO>> InvoicPrintInfo(@Query("InvoiceNumber") String id);

    @POST("/api/Invoice")
    Call<BaseModel<InvoiceDTO>> addInvoice(@Body Invoice invoice);

    @PUT("/api/Invoice")
    Call<BaseModel<InvoiceDTO>> updateInvoice(@Body Invoice invoice);

    @DELETE("/api/Invoice/deleteByType")
    Call<BaseModel<InvoiceDTO>> deleteInvoice(@Query("id") String id, @Query("invoiceType") int invoiceType);

    @GET("/api/Invoice/changeStatus")
    Call<BaseModel<Boolean>> changeStatus(@Query("id") String id, @Query("invoiceType") int invoiceType);
    // Invoice
}