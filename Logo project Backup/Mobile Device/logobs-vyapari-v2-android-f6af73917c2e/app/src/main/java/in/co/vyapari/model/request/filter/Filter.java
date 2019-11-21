package in.co.vyapari.model.request.filter;

import java.util.HashMap;

/**
 * Created by bekirdursun on 7.12.2017.
 */

public class Filter {

    private PagingItem Paging;
    private HashMap<String, Integer> Sorting;
    private HashMap<String, Integer> ExtraFilters;
    private String SearchText;
    private int ActiveRecords;

    public Filter(FilterBuilder filterBuilder) {
        this.Paging = filterBuilder.Paging;
        this.Sorting = filterBuilder.Sorting;
        this.ExtraFilters = filterBuilder.ExtraFilters;
        this.SearchText = filterBuilder.SearchText;
        this.ActiveRecords = filterBuilder.ActiveRecords;
    }

    public PagingItem getPaging() {
        return Paging;
    }

    public void setPaging(PagingItem paging) {
        Paging = paging;
    }

    public HashMap<String, Integer> getSorting() {
        return Sorting;
    }

    public void setSorting(HashMap<String, Integer> sorting) {
        Sorting = sorting;
    }

    public HashMap<String, Integer> getExtraFilters() {
        return ExtraFilters;
    }

    public void setExtraFilters(HashMap<String, Integer> extraFilters) {
        ExtraFilters = extraFilters;
    }

    public String getSearchText() {
        return SearchText;
    }

    public void setSearchText(String searchText) {
        SearchText = searchText;
    }

    public int getActiveRecords() {
        return ActiveRecords;
    }

    public void setActiveRecords(int activeRecords) {
        ActiveRecords = activeRecords;
    }

    public static class FilterBuilder {

        private PagingItem Paging;
        private HashMap<String, Integer> Sorting;
        private HashMap<String, Integer> ExtraFilters;
        private String SearchText;
        private int ActiveRecords;

        public FilterBuilder() {
        }

        public FilterBuilder Paging(PagingItem paging) {
            this.Paging = paging;
            return this;
        }

        public FilterBuilder Sorting(HashMap<String, Integer> sorting) {
            this.Sorting = sorting;
            return this;
        }

        public FilterBuilder ExtraFilters(HashMap<String, Integer> extraFilters) {
            this.ExtraFilters = extraFilters;
            return this;
        }

        public FilterBuilder SearchText(String searchText) {
            this.SearchText = searchText;
            return this;
        }

        public FilterBuilder ActiveRecords(int activeRecords) {
            this.ActiveRecords = activeRecords;
            return this;
        }

        public Filter build() {
            return new Filter(this);
        }
    }

}
