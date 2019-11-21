package in.co.vyapari.middleware.service;

import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import in.co.vyapari.VyapariApp;
import in.co.vyapari.middleware.ServiceManager;
import in.co.vyapari.middleware.listener.ServiceCall;
import in.co.vyapari.model.ArApCollection;
import in.co.vyapari.model.Product;
import in.co.vyapari.model.request.filter.Filter;
import in.co.vyapari.model.response.BaseModel;
import in.co.vyapari.model.response.dto.ArApCollectionDTO;
import in.co.vyapari.model.response.dto.CollectionTypeDTO;
import in.co.vyapari.model.response.dto.ProductDTO;
import in.co.vyapari.model.response.dto.ProductsSumDTO;
import retrofit2.Call;

/**
 * Created by bekirdursun on 5.02.2018.
 */

public class ProductService {

    public static synchronized void getProducts(Filter filter, ServiceCall<BaseModel<ProductsSumDTO>> serviceCall) {
        serviceCall.start();
        getProductsCall(filter, serviceCall);
    }

    private static synchronized void getProductsCall(final Filter filter, final ServiceCall<BaseModel<ProductsSumDTO>> serviceCall) {
        Gson gson = new Gson();
        String jsn= gson.toJson(filter);
        //Log.e("JSON OBJECT-",jsn);
        Call<BaseModel<ProductsSumDTO>> call = VyapariApp.getApiService().getProducts(filter);
        new ServiceManager<BaseModel<ProductsSumDTO>>().ServiceCall(call, new ServiceCall<BaseModel<ProductsSumDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ProductsSumDTO> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //getProductListSQLite(filter, serviceCall);
            }
        });
    }

    private static void getProductListSQLite(Filter filter, ServiceCall<BaseModel<ProductsSumDTO>> serviceCall) {
        //VyapariApp.getDbBridge().getProductList(filter, serviceCall);
    }


    public static synchronized void getProductDetail(String id, ServiceCall<BaseModel<ProductDTO>> serviceCall) {
        serviceCall.start();
        getProductDetailCall(id, serviceCall);
    }

    private static synchronized void getProductDetailCall(final String id, final ServiceCall<BaseModel<ProductDTO>> serviceCall) {

        //Log.e("JSON OBJECT-",id);
        Call<BaseModel<ProductDTO>> call = VyapariApp.getApiService().getProductDetail(id);
        new ServiceManager<BaseModel<ProductDTO>>().ServiceCall(call, new ServiceCall<BaseModel<ProductDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ProductDTO> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //getProductDetailSQLite(id, serviceCall);
            }
        });
    }

    public static synchronized void getCollectionType(ServiceCall<BaseModel<List<CollectionTypeDTO>>> serviceCall) {
        serviceCall.start();
        getCollectionTypeCall( serviceCall);
    }

    private static synchronized void getCollectionTypeCall( final ServiceCall<BaseModel<List<CollectionTypeDTO>>> serviceCall) {


        Call<BaseModel<List<CollectionTypeDTO>>> call = VyapariApp.getApiService().GetCollectionTypes();
        new ServiceManager<BaseModel<List<CollectionTypeDTO>>>().ServiceCall(call, new ServiceCall<BaseModel<List<CollectionTypeDTO>>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<List<CollectionTypeDTO>> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //getProductDetailSQLite(id, serviceCall);
            }
        });
    }

    public static synchronized void getArpSlipNumber( ServiceCall  serviceCall) {
        serviceCall.start();
        getArpSlipNumberCall(  serviceCall);
    }

    private static synchronized void getArpSlipNumberCall( final ServiceCall serviceCall) {


        Call call = VyapariApp.getApiService().getArapNumber();
        new ServiceManager<BaseModel<String>>().ServiceCall(call, new ServiceCall<BaseModel<String>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<String> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //getProductDetailSQLite(id, serviceCall);
            }
        });
    }

    public static synchronized void saveCollection(int i,ArApCollection arapCollection, ServiceCall<BaseModel<ArApCollectionDTO>> serviceCall) {
        serviceCall.start();
        saveCollectionCALL(i,arapCollection, serviceCall);
    }

    private static synchronized void saveCollectionCALL(int i,final ArApCollection arapCollection, final ServiceCall<BaseModel<ArApCollectionDTO>> serviceCall) {
        Gson gson = new Gson();
        String jsn= gson.toJson(arapCollection);
       // Log.e("JSON OBJECT-",jsn);
        Call<BaseModel<ArApCollectionDTO>> call = VyapariApp.getApiService().saveArap(i,arapCollection);
        new ServiceManager<BaseModel<ArApCollectionDTO>>().ServiceCall(call, new ServiceCall<BaseModel<ArApCollectionDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ArApCollectionDTO> response) {
                /*if (!response.isError()) {
                    product.setId(response.getData().getId());
                    addProductSQLite(product, null);
                }*/
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //addProductSQLite(product, serviceCall);
            }
        });
    }

    private static void getProductDetailSQLite(String id, ServiceCall<BaseModel<ProductDTO>> serviceCall) {
        //VyapariApp.getDbBridge().getProduct(id, serviceCall);
    }


    public static synchronized void getProductDetailWithBarcode(String barcode, ServiceCall<BaseModel<ProductDTO>> serviceCall) {
        serviceCall.start();
        getProductDetailWithBarcodeCall(barcode, serviceCall);
    }

    private static synchronized void getProductDetailWithBarcodeCall(String barcode, final ServiceCall<BaseModel<ProductDTO>> serviceCall) {
        Call<BaseModel<ProductDTO>> call = VyapariApp.getApiService().getProductDetailWithCode(barcode);
        new ServiceManager<BaseModel<ProductDTO>>().ServiceCall(call, new ServiceCall<BaseModel<ProductDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ProductDTO> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
            }
        });
    }

    private static void getProductDetailWithCodeSQLite(String id, ServiceCall<BaseModel<ProductDTO>> serviceCall) {
    }


    public static synchronized void addProduct(Product product, ServiceCall<BaseModel<ProductDTO>> serviceCall) {
        serviceCall.start();
        addProductCall(product, serviceCall);
    }

    private static synchronized void addProductCall(final Product product, final ServiceCall<BaseModel<ProductDTO>> serviceCall) {
        Gson gson = new Gson();
        String jsn= gson.toJson(product);
        //Log.e("JSON OBJECT-",jsn);
        Call<BaseModel<ProductDTO>> call = VyapariApp.getApiService().addProduct(product);
        new ServiceManager<BaseModel<ProductDTO>>().ServiceCall(call, new ServiceCall<BaseModel<ProductDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ProductDTO> response) {
                /*if (!response.isError()) {
                    product.setId(response.getData().getId());
                    addProductSQLite(product, null);
                }*/
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //addProductSQLite(product, serviceCall);
            }
        });
    }

    private static void addProductSQLite(Product product, ServiceCall<BaseModel<ProductDTO>> serviceCall) {
        //VyapariApp.getDbBridge().addProduct(product, serviceCall);
    }


    public static synchronized void updateProduct(Product product, ServiceCall<BaseModel<ProductDTO>> serviceCall) {
        serviceCall.start();
        updateProductCall(product, serviceCall);
    }

    private static synchronized void updateProductCall(final Product product, final ServiceCall<BaseModel<ProductDTO>> serviceCall) {
        Gson gson = new Gson();
        String jsn= gson.toJson(product);
        //Log.e("JSON OBJECT-",jsn);
        Call<BaseModel<ProductDTO>> call = VyapariApp.getApiService().updateProduct(product);
        new ServiceManager<BaseModel<ProductDTO>>().ServiceCall(call, new ServiceCall<BaseModel<ProductDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ProductDTO> response) {
                /*if (!response.isError()) {
                    product.setId(response.getData().getId());
                    updateProductSQLite(product, serviceCall);
                }*/
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //updateProductSQLite(product, serviceCall);
            }
        });
    }

    private static void updateProductSQLite(Product product, ServiceCall<BaseModel<ProductDTO>> serviceCall) {
        //VyapariApp.getDbBridge().updateProduct(product, serviceCall);
    }


    public static synchronized void deleteProduct(String id, ServiceCall<BaseModel<ProductDTO>> serviceCall) {
        serviceCall.start();
        deleteProductCall(id, serviceCall);
    }

    private static synchronized void deleteProductCall(final String id, final ServiceCall<BaseModel<ProductDTO>> serviceCall) {

        //Log.e("JSON OBJECT-",id);
        Call<BaseModel<ProductDTO>> call = VyapariApp.getApiService().deleteProduct(id);
        new ServiceManager<BaseModel<ProductDTO>>().ServiceCall(call, new ServiceCall<BaseModel<ProductDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ProductDTO> response) {
                /*if (!response.isError()) {
                    deleteProductSQLite(id, serviceCall);
                }*/
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //serviceCall.onFailure(true, throwable);
            }
        });
    }

    private static void deleteProductSQLite(String id, ServiceCall<BaseModel<ProductDTO>> serviceCall) {
        //VyapariApp.getDbBridge().deleteProduct(id, serviceCall);
    }
}