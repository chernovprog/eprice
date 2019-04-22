package ua.eprice.services;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.eprice.exceptions.EmptyProductListException;
import ua.eprice.model.product.Product;
import ua.eprice.model.product.offer.Offer;
import ua.eprice.services.priceua.ProductPriceuaService;
import ua.eprice.services.vseceni.ProductListVseceniService;
import ua.eprice.services.vseceni.ProductVseceniService;

import java.util.Collections;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductPriceuaService prodPriceuaService;

    @Autowired
    private ProductVseceniService prodVseceniService;

    @Autowired
    private ProductListVseceniService prodListVseceniService;

    public List<Product> getProductListPage(Document documentVseceni) {
        List<Product> productListVseceni = prodListVseceniService.parseProductListPage(documentVseceni);
        if (productListVseceni.isEmpty()) throw new EmptyProductListException("Product list is empty");

        return productListVseceni;
    }

    public List<Product> getProductListPageBySearch(Document documentVseceni) {
        List<Product> productListVseceni = prodListVseceniService.parseProductListPage(documentVseceni);

        return productListVseceni;
    }

    public Product getProductWithOffersPage(Document documentVseceni) {
        Product productVseceni = prodVseceniService.parseProductPageWithOffers(documentVseceni);
        Product productPriceua = prodPriceuaService.parseProductPageWithOffers(productVseceni.getKeyWord());

        if (productPriceua.getName() != null && !"".equals(productPriceua.getName())) {
            if (isItSameProduct(productVseceni, productPriceua)) {
                List<Offer> priceuaOffers = productPriceua.getOffers();
                priceuaOffers.forEach(offer -> productVseceni.addOffer(offer));
                productVseceni.setQuantityOffers(productVseceni.getQuantityOffers() + priceuaOffers.size());
            }
        }

        Collections.sort(productVseceni.getOffers());

        return productVseceni;
    }

    private boolean isItSameProduct(Product prodVseceni, Product prodPriceua) {
        String prodNameVseceni = prodVseceni.getName().toLowerCase();
        String prodNamePriceua = prodPriceua.getName().toLowerCase();

        if (prodNameVseceni.equals(prodNamePriceua)) return true;
        else if (transformName(prodNameVseceni).equals(transformName(prodNamePriceua))) return true;
        else return false;
    }

    private String transformName(String name) {
        StringBuilder nameSB = new StringBuilder(name);

        for (int i = 0; i < nameSB.length(); i++) {
            if (nameSB.charAt(i) == ' ' || nameSB.charAt(i) == '-') {
                nameSB.deleteCharAt(i);
                i--;
            }
        }

        if (nameSB.toString().contains("(") && nameSB.toString().contains(")")) {
            if (nameSB.indexOf("(") < nameSB.indexOf(")")) {
                nameSB.delete(nameSB.indexOf("("), nameSB.indexOf(")") + 1);
            }
        }

        if (nameSB.toString().contains("/")) {
            if (nameSB.indexOf("/") < nameSB.length() - 1) {
                nameSB.delete(nameSB.indexOf("/"), nameSB.length());
            } else {
                nameSB.deleteCharAt(nameSB.length() - 1);
            }
        }

        return nameSB.toString();
    }

}
