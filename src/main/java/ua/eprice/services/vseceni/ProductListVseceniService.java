package ua.eprice.services.vseceni;

import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import ua.eprice.model.product.Product;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductListVseceniService {
    private static final String domain = "https://vseceni.ua";

    //  Getting a list of products of one of the pages
    public List<Product> parseProductListPage(Document doc) {

        List<Product> products = new ArrayList<>();

        if (doc != null) {

            Elements elements = doc.select("article.item");//.td-table
            //if (elements.isEmpty()) elements.select("");

            for (Element element : elements) {
                Product product = new Product();
                product.setName(this.getName(element));
                product.setImgS(this.getImgSmall(element));
                product.setDesc(this.getDescription(element));
                product.setPriceAverage((this.getPriceAverage(element)));
                product.setCurrency(this.getCurrency(element));
                product.setQuantityOffers(this.getQuantityOffers(element));
                product.setUrl(this.getUrl(element));

                products.add(product);
            }

        }

        return products;
    }

    private String getName(Element element) {
        return element.select(".catalog-block-head").text();
    }

    private String getImgSmall(Element element) {
        String imgS = element.select(".img img").attr("src");//.td-block
        return imgS;
    }

    private String getDescription(Element element) {
        String desc = element.select(".description").text();
        if (!"".equals(desc)) {
            return desc;
        } else {
            desc = element.select(".img a").attr("title");
            if (!"".equals(desc)) return desc;
            else return getName(element);
        }
    }

    private Integer getPriceAverage(Element element) {
        String priceFrom = element.select(".price span strong").text().replaceAll(" ", "");

        if (!"".equals(priceFrom) && priceFrom.indexOf("грн") >= 0) {
            String subStr = priceFrom.substring(0, priceFrom.indexOf("грн"));
            if (NumberUtils.isDigits(subStr)) return Integer.valueOf(subStr);
            else return 0;
        }
        return 0;
    }

    private String getCurrency(Element element) {
        String curr = element.select(".price span strong").text();

        if (!"".equals(curr)) {
            int spaceIndex = curr.lastIndexOf(" ");
            if (spaceIndex >= 0 && spaceIndex < curr.length() - 1)
                return curr.substring(curr.lastIndexOf(" ") + 1);
            return "";
        }
        return "";
    }

    private Integer getQuantityOffers(Element element) {
        String countOffer = element.select(".price > strong:last-child").text();
        if ("".equals(countOffer)) countOffer = element.select(".prices-min-max small :last-child").text();
        if (NumberUtils.isDigits(countOffer)) return Integer.valueOf(countOffer);
        return 1;
    }

    private String getUrl(Element element) {
        return element.select(".catalog-block-head a").attr("href");
    }
}