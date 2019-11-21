package in.co.vyaparienterprise.model.response.dto;

import in.co.vyaparienterprise.model.request.filter.PagingItem;
import in.co.vyaparienterprise.model.response.summary.CollectionSum;


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
