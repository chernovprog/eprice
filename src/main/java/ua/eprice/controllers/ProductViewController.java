package ua.eprice.controllers;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.eprice.model.product.Product;
import ua.eprice.model.product.page.Page;
import ua.eprice.services.MenuService;
import ua.eprice.services.ProductService;
import ua.eprice.services.vseceni.DocumentVseceniService;
import ua.eprice.services.vseceni.PageVseceniService;

import javax.servlet.ServletRequest;

@Controller
public class ProductViewController {

    @Autowired
    private ProductService productService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private PageVseceniService pageVseceniService;

    @Autowired
    private DocumentVseceniService documentVseceni;

    @RequestMapping(value = {"/md/*/"}, method = RequestMethod.GET)
    public String getProductView(Model model, ServletRequest request) {

        Document docVseceni = documentVseceni.getDocumentProductWithOffersPage(request);
        Product product = productService.getProductWithOffersPage(docVseceni);
        Page pageInfo = pageVseceniService.getInfoFromProductPage(docVseceni);

        model.addAttribute("menu", menuService.getMenu());
        model.addAttribute("product", product);
        model.addAttribute("pageInfo", pageInfo);

        return "product";
    }
}
