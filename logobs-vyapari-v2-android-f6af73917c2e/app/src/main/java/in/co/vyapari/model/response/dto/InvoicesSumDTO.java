package in.co.vyapari.model.response.dto;

import java.util.ArrayList;

import in.co.vyapari.model.request.filter.PagingItem;
import in.co.vyapari.model.response.summary.InvoiceSum;

/**
 * Created by Bekir.Dursun on 24.10.2017.
 */

public class InvoicesSumDTO {

    private ArrayList<InvoiceSum> Data;
    private PagingItem Paging;

    public InvoicesSumDTO(ArrayList<InvoiceSum> data) {
        this.Data = data;
    }

    public ArrayList<InvoiceSum> getInvoices() {
        return Data;
    }

    public PagingItem getPaging() {
        return Paging;
    }
}
