package ua.eprice.model.product;

import org.springframework.stereotype.Component;
import ua.eprice.model.product.offer.Offer;
import ua.eprice.model.product.page.Page;

import java.util.ArrayList;
import java.util.List;

@Component
public class Product {
    private String name;
    private String url;
    private String imgS;
    private String imgL;
    private String desc;
    private String spec;
    private Integer priceFrom;
    private Integer priceAverage;
    private Integer priceTo;
    private String currency;
    private List<Offer> offers;
    private Integer quantityOffers;
    private Page page;
    private String keyWord;

    public Product() {
        offers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgS() {
        return imgS;
    }

    public void setImgS(String imgS) {
        this.imgS = imgS;
    }

    public String getImgL() {
        return imgL;
    }

    public void setImgL(String imgL) {
        this.imgL = imgL;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Integer getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(Integer priceFrom) {
        this.priceFrom = priceFrom;
    }

    public Integer getPriceAverage() {
        return priceAverage;
    }

    public void setPriceAverage(Integer priceAverage) {
        this.priceAverage = priceAverage;
    }

    public Integer getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(Integer priceTo) {
        this.priceTo = priceTo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public void addOffer(Offer offer) {
        this.offers.add(offer);
    }

    public Integer getQuantityOffers() {
        return quantityOffers;
    }

    public void setQuantityOffers(Integer quantityOffers) {
        this.quantityOffers = quantityOffers;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    public String toString() {
        return "name = " + name +
                ", img = " + imgS +
                ", offer = " + offers;
    }
}
