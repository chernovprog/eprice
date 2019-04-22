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

import javax.servlet.ServletRequest;
import java.util.List;

@Controller
public class ProductListController {

    @Autowired
    private ProductService productService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private PageVseceniService pageVseceniService;

    @Autowired
    private DocumentVseceniService documentVseceni;

    @RequestMapping(value = {"/ct/*/"}, method = RequestMethod.GET)
    public String getProductList(@RequestParam(required = false, defaultValue = "1") String page,
                                 ServletRequest request, Model model) {

        Document docVseceni = documentVseceni.getDocumentProductListPage(request, page);
        List<Product> productList = productService.getProductListPage(docVseceni);
        Page pageInfo = pageVseceniService.getInfoFromProductListPage(docVseceni);

        model.addAttribute("menu", menuService.getMenu());
        model.addAttribute("productList", productList);
        model.addAttribute("pageInfo", pageInfo);

        return "productList";
    }

}
