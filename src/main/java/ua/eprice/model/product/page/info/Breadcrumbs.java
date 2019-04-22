package ua.eprice.model.product.page.info;

public class Breadcrumbs {
    private String url;
    private String name;

    public Breadcrumbs() {
    }

    public Breadcrumbs(String name) {
        this.name = name;
    }

    public Breadcrumbs(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Breadcrumbs[" + "url=" + url + ",name=" + name + "]";
    }
}