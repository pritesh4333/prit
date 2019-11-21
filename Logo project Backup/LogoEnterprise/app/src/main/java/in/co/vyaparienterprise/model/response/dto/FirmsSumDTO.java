package in.co.vyaparienterprise.model.response.dto;

import java.util.ArrayList;

import in.co.vyaparienterprise.model.request.filter.PagingItem;
import in.co.vyaparienterprise.model.response.summary.FirmSum;

/**
 * Created by Bekir.Dursun on 26.10.2017.
 */

public class FirmsSumDTO {

    private ArrayList<FirmSum> Data;
    private PagingItem Paging;

    public FirmsSumDTO(ArrayList<FirmSum> data) {
        this.Data = data;
    }

    public ArrayList<FirmSum> getFirms() {
        return Data;
    }

    public PagingItem getPaging() {
        return Paging;
    }
}
