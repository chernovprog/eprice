package ua.eprice.services.vseceni;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import ua.eprice.model.product.Product;
import ua.eprice.model.product.offer.Offer;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductVseceniService {
    private static final String domain = "https://vseceni.ua";
    private static final String resourceSource = "https://vseceni.ua/i/favicon.ico";
    private static final Logger logger = Logger.getLogger(ProductVseceniService.class);

    //  Getting the whole list of offer (information is loaded using JavaScript)
    public Product parseProductPageWithOffers(Document doc) {
        Product product = null;

        if (doc != null) {
            product = new Product();
            product.setName(this.getName(doc));
            product.setQuantityOffers(this.getQuantityOffers(doc));
            product.setImgS(this.getImgProduct(doc));
            product.setDesc(this.getDescription(doc));
            product.setKeyWord(product.getName());
            product.setPriceAverage(this.getPriceAverage(doc));
            product.setCurrency(this.getCurrency(doc));
            product.setOffers(getListOffers(doc, product));
        }

        return product;
    }

    private String getName(Document doc) {
        String name = doc.select("h1 span").html();
        if (name.indexOf("<small>") >= 0) name = name.substring(0, name.indexOf("<small>"));
        return name;
    }

    private Integer getQuantityOffers(Document doc) {
        String numOffers = doc.select(".average-price > div strong:last-child").text();
        if (NumberUtils.isDigits(numOffers)) return Integer.valueOf(numOffers);
        return 1;
    }

    private String getImgProduct(Document doc) {
        return doc.select(".product-img img").attr("src");
    }

    private String getDescription(Document doc) {
        return doc.select(".pb-body-content").outerHtml();
    }

    private Integer getPriceAverage(Document doc) {
        String priceStr = doc.select(".average-price-big").text().replaceAll(" ", "");
        if (priceStr.indexOf("грн") > 0) {
            priceStr = priceStr.substring(0, priceStr.indexOf("грн"));
            if (NumberUtils.isDigits(priceStr)) return Integer.valueOf(priceStr);
        }
        return 0;
    }

    private String getCurrency(Document doc) {
        String currency = doc.select(".average-price-big").text();
        if (currency.contains(" ")) return currency.substring(currency.lastIndexOf(" ") + 1);
        return "";
    }

    // Getting product offer information
    private List<Offer> getListOffers(Document doc, Product product) {
        List<Offer> offers = new ArrayList<>();
        Elements elements = doc.select("[data-is-delivery]");

        for (Element element : elements) {
            Offer offer = new Offer();
            offer.setNameStore(getNameStore(element));
            offer.setImgLogoStore(getLogoStore(element));
            offer.setLink(getLinkStore(element));
            offer.setPrice(getProductPriceStore(element));
            offer.setCurrency(getCurrencyStore(element));
            String desc = getProductDescriptionStore(element);
            if ("".equals(desc)) desc = product.getName();
            offer.setDesc(desc);
            offer.setImgProduct(product.getImgS());
            offer.setResourceSrc(resourceSource);
            offers.add(offer);
        }

        return offers;
    }

    private String getNameStore(Element element) {
        return element.attr("data-name");
    }

    private String getLogoStore(Element element) {
        return element.select("img").attr("src");
    }

    private String getLinkStore(Element element) {
        String link = element.select(".td-block.td-block--product1 :nth-child(1)").attr("href");
        return domain + link;
    }

    private Integer getProductPriceStore(Element element) {
        String priceStr = element.select(".product-price .price-click span").text().replaceAll(" ", "");
        if (NumberUtils.isDigits(priceStr)) return Integer.valueOf(priceStr);
        return 0;
    }

    private String getCurrencyStore(Element element) {
        return element.select(".price-click small").text();
    }

    private String getProductDescriptionStore(Element element) {
        return element.select(".pph-body").text();
    }
}
