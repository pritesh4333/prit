package in.co.vyaparienterprise.model.request.filter;

/**
 * Created by bekirdursun on 7.12.2017.
 */

public class PagingItem {

    private int CurrentPage;
    private int PageSize;

    public int getCurrentPage() {
        return CurrentPage;
    }

    public void setCurrentPage(int currentPage) {
        CurrentPage = currentPage;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }
}
