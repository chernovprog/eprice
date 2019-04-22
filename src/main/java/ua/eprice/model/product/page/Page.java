package ua.eprice.model.product.page;

import org.springframework.stereotype.Component;
import ua.eprice.model.product.page.info.Breadcrumbs;
import ua.eprice.model.product.page.info.Pagination;

import java.util.List;

@Component
public class Page {
    private String title;
    private String category;
    private Pagination pagination;
    private List<Breadcrumbs> breadcrumbs;
    private String path;

    public Page() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<Breadcrumbs> getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setBreadcrumbs(List<Breadcrumbs> breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}