package ua.eprice.model.product.offer;

import org.springframework.stereotype.Component;

@Component
public class Offer implements Comparable<Offer> {
    private String nameStore;
    private String link;
    private String imgLogoStore;
    private String imgProduct;
    private String desc;
    private Integer price;
    private String currency;
    private String resourceSrc;

    public Offer() {
    }

    public String getNameStore() {
        return nameStore;
    }

    public void setNameStore(String nameStore) {
        this.nameStore = nameStore;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImgLogoStore() {
        return imgLogoStore;
    }

    public void setImgLogoStore(String imgLogoStore) {
        this.imgLogoStore = imgLogoStore;
    }

    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getResourceSrc() {
        return resourceSrc;
    }

    public void setResourceSrc(String resourceSrc) {
        this.resourceSrc = resourceSrc;
    }

    @Override
    public int compareTo(Offer o) {
        return this.getPrice() - o.getPrice();
    }
}
