package in.co.vyapari.model.response.dto;

import java.util.ArrayList;

import in.co.vyapari.model.request.filter.PagingItem;
import in.co.vyapari.model.response.summary.ProductSum;

/**
 * Created by Bekir.Dursun on 26.10.2017.
 */

public class ProductsSumDTO {

    private ArrayList<ProductSum> Data;
    private PagingItem Paging;

    public ProductsSumDTO(ArrayList<ProductSum> data) {
        Data = data;
    }

    public ArrayList<ProductSum> getProducts() {
        return Data;
    }

    public PagingItem getPaging() {
        return Paging;
    }
}