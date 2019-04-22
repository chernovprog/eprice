package ua.eprice.controllers;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.eprice.model.product.Product;
import ua.eprice.model.product.page.Page;
import ua.eprice.services.MenuService;
import ua.eprice.services.ProductService;
import ua.eprice.services.vseceni.DocumentVseceniService;
import ua.eprice.services.vseceni.PageVseceniService;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private ProductService productService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private PageVseceniService pageVseceniService;

    @Autowired
    private DocumentVseceniService documentVseceni;

    @RequestMapping(value = "/search/", method = RequestMethod.GET)
    public String search(@RequestParam(name = "query") String query,
                         @RequestParam(required = false, defaultValue = "1") String page, Model model) {
        model.addAttribute("menu", menuService.getMenu());
        model.addAttribute("query", query);

        Document docVseceni = documentVseceni.getDocumentBySearchQuery(query, page);
        List<Product> productList = productService.getProductListPageBySearch(docVseceni);

        if (productList.isEmpty()) return "errors/productNotFound";

        Page pageInfo = pageVseceniService.getInfoFromProductListPage(docVseceni);

        model.addAttribute("productList", productList);
        model.addAttribute("pageInfo", pageInfo);
        return "search";
    }
}
