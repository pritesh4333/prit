package in.co.vyapari.model.response.dto;

import in.co.vyapari.model.request.filter.PagingItem;
import in.co.vyapari.model.response.summary.CollectionSum;


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
