package ua.eprice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.eprice.services.MenuService;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private MenuService menuService;

    @RequestMapping("/")
    public String index(Model model) {

        model.addAttribute("menu", menuService.getMenu());

        model.addAttribute("popularProductsList", menuService.getPopularProductsList());
        model.addAttribute("householdProductsList", menuService.getHouseholdProductsList());
        model.addAttribute("kitchenProductsList", menuService.getKitchenProductsList());

        return "index";
    }

}

