package in.co.vyapari.model.response.dto;

import java.util.ArrayList;

import in.co.vyapari.model.request.filter.PagingItem;
import in.co.vyapari.model.response.summary.CollectionSum;
import in.co.vyapari.model.response.summary.InvoiceSum;


public class CollectionListDTO {

    private CollectionSum Data;
    private PagingItem Paging;

    public CollectionListDTO(CollectionSum data) {
        this.Data = data;
    }

    public CollectionSum getCollectionData() {
        return Data;
    }

    public PagingItem getPaging() {
        return Paging;
    }
}
