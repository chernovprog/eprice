package ua.eprice.model.product.page.info;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Pagination {
    private List<String> rangePages;
    private String numberLastPage;
    private String activePageNumber;

    public Pagination() {
        this.rangePages = new ArrayList<>();
    }

    public List<String> getRangePages() {
        return rangePages;
    }

    public void setRangePages(List<String> rangePages) {
        this.rangePages = rangePages;
    }

    public String getNumberLastPage() {
        return numberLastPage;
    }

    public void setNumberLastPage(String numberLastPage) {
        this.numberLastPage = numberLastPage;
    }

    public String getActivePageNumber() {
        return activePageNumber;
    }

    public void setActivePageNumber(String activePageNumber) {
        this.activePageNumber = activePageNumber;
    }
}
