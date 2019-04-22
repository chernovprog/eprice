package ua.eprice.services.priceua;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import ua.eprice.model.product.Product;
import ua.eprice.model.product.offer.Offer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductPriceuaService {
    private static final String domain = "https://price.ua";
    private static final String resourceSource = "https://price.ua/favicon.ico";
    private static final Logger logger = Logger.getLogger(ProductPriceuaService.class);

    public Product parseProductPageWithOffers(String keyWord) {
        Document doc = this.getDocumentByProductName(keyWord);
        Product product = new Product();

        if (doc != null) {
            Element firstProduct = this.getFirstProductFromSearchResult(doc);
            if (firstProduct != null) {
                String linkOffers = this.getDirectLinkProductOffers(firstProduct);

                if (!"".equals(linkOffers)) {
                    List<Offer> listOffers = this.getListOffers(linkOffers);

                    if (!listOffers.isEmpty()) {
                        product.setName(this.getNameFirstProduct(firstProduct, keyWord));
                        product.setOffers(listOffers);
                    }
                }
            }
        }

        return product;
    }

    private Document getDocumentByProductName(String keyWord) {
        String source = "https://price.ua/search/?q=" + keyWord;
        Document doc = null;
        try {
            doc = Jsoup.connect(source).get();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("HTTP error fetching URL. Status=404. Search by keyword on Price.ua. Path=" + source);
        }
        return doc;
    }

    private Element getFirstProductFromSearchResult(Document doc) {
        return doc.selectFirst(".product-block");
    }

    // Get a direct link to the product offer
    private String getDirectLinkProductOffers(Element firstProduct) {
        String link = firstProduct.select(".model-name.ga_card_mdl_title").attr("href");
        String id = firstProduct.attr("data-tracker-mid");

        if (link != null && !"".equals(link) && link.contains("/")) {
            String[] split = link.split("/");
            return "https://price.ua/" + split[4] + "/prices/" + id + ".html";
        } else return "";
    }

    private String getNameFirstProduct(Element firstProduct, String keyWord) {
        String name = firstProduct.select("a.model-name.ga_card_mdl_title").text();
        if (name.equals(keyWord)) return name;
        else if (name.indexOf(keyWord) > 0) return name.substring(name.indexOf(keyWord));
        return name;
    }

    /* Parsing a list of offer */
    private List<Offer> getListOffers(String linkWithOffers) {
        Document doc = this.getDocumentWithOffers(linkWithOffers);
        List<Offer> offerList = new ArrayList<>();

        if (doc != null) {
            Elements blocksOffers = this.getBlocksOffers(doc);

            for (Element block : blocksOffers) {
                Offer offer = new Offer();

                offer.setNameStore(this.getNameStore(block));
                offer.setImgLogoStore(this.getImgLogoStore(block));
                offer.setImgProduct(this.getImgProductStore(block));
                offer.setLink(this.getLinkStore(block));
                offer.setDesc(this.getDescription(block, doc));
                offer.setPrice(this.getPriceFromSite(block));
                offer.setCurrency(this.getCurrencyStore(block));
                offer.setResourceSrc(resourceSource);

                offerList.add(offer);
            }
        }

        return offerList;
    }

    private Document getDocumentWithOffers(String linkWithOffers) {
        Document doc = null;
        try {
            doc = Jsoup.connect(linkWithOffers).get();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("HTTP error fetching URL. Status=404. Incorrect link generation (Price.ua). Link=" + linkWithOffers);
        }
        return doc;
    }

    private Elements getBlocksOffers(Document doc) {
        return doc.select("div.priceline-item");
    }

    private String getNameStore(Element el) {
        return el.select("span.logo img").attr("alt");
    }

    private String getImgLogoStore(Element el) {
        String res = el.select("span.logo img").attr("src");
        return "".equals(res) ? "" : "https:" + res;
    }

    private String getImgProductStore(Element el) {
        String res = el.select("div.photo-wrap img").attr("src");
        return "".equals(res) ? "" : "https:" + res;
    }

    private String getLinkStore(Element el) {
        String res = el.select("div.stores-block a").attr("href");
        return "".equals(res) ? "" : domain + res;
    }


    private String getDescription(Element el, Document doc) {
        String desc = el.select("span.descr-text").text();
        if ("".equals(desc)) {  /*  add a name if the field is empty */
            String productName = doc.select("[itemprop='name']").text();
            return productName;
        }

        String res = "";
        if (desc != null && !"".equals(desc)) {
            if (desc.contains("Подробнее")) {
                res = desc.substring(0, desc.indexOf("Подробнее"));
            }
            if (desc.length() > 200) {
                res = desc.substring(0, 200) + "...";
            }
        }

        return res;
    }

    private Integer getPriceFromSite(Element el) {
        String priceStr = el.select("span.price").text();

        if (priceStr != null && !"".equals(priceStr)) {
            if (priceStr.contains(" ")) {
                priceStr = priceStr.replace(" ", "");
            }

            if (priceStr.contains("грн.")) {
                priceStr = priceStr.replace("грн.", "");
            }

            if (NumberUtils.isDigits(priceStr)) {
                return Integer.valueOf(priceStr);
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    private String getCurrencyStore(Element el) {
        return el.select("span.price span").text();
    }

}

