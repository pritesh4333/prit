package in.co.vyapari.util;

import java.util.HashMap;

import in.co.vyapari.constant.Constants;
import in.co.vyapari.model.request.filter.Filter;
import in.co.vyapari.model.request.filter.PagingItem;

/**
 * Created by bekirdursun on 7.12.2017.
 */

public class FilterUtil {

    public static Filter createFilter(int currentPage) {
        return createFilter(currentPage, null, null, null, 0);
    }

    public static Filter createFilter(int currentPage, HashMap<String, Integer> sorting) {
        return createFilter(currentPage, sorting, null, null, 0);
    }

    public static Filter createFilter(int currentPage, String searchText) {
        return createFilter(currentPage, null, null, searchText, 0);
    }

    public static Filter createFilter(int currentPage, boolean isOnlyActive, String searchText) {
        int value = isOnlyActive ? 1 : 0;
        return createFilter(currentPage, null, null, searchText, value);
    }
    public static Filter createCollectionFilter(int currentPage, String searchText) {
        HashMap<String, Integer> extraFilters = new HashMap<>();
        extraFilters.put("slipType", 2);
        return createFilter(currentPage, null, extraFilters, searchText, 0);
    }

    public static Filter createPaymentFilter(int currentPage, String searchText) {
        HashMap<String, Integer> extraFilters = new HashMap<>();
        extraFilters.put("slipType", 3);
        return createFilter(currentPage, null, extraFilters, searchText, 0);
    }
    public static Filter createSaleInvoiceFilter(int currentPage, String searchText) {
        HashMap<String, Integer> extraFilters = new HashMap<>();
        extraFilters.put("InvoiceType", 1);
        return createFilter(currentPage, null, extraFilters, searchText, 0);
    }

    public static Filter createPurchaseInvoiceFilter(int currentPage, String searchText) {
        HashMap<String, Integer> extraFilters = new HashMap<>();
        extraFilters.put("InvoiceType", 2);
        return createFilter(currentPage, null, extraFilters, searchText, 0);
    }

    private static Filter createFilter(int currentPage, HashMap<String, Integer> sorting, HashMap<String, Integer> extraFilters, String searchText, int activeRecords) {
        PagingItem paging = new PagingItem();
        paging.setCurrentPage(currentPage);
        paging.setPageSize(Constants.MAX_PAGE_SIZE);

        return new Filter.FilterBuilder()
                .Paging(paging)
                .Sorting(sorting)
                .ExtraFilters(extraFilters)
                .SearchText(searchText)
                .ActiveRecords(activeRecords)
                .build();
    }
}
