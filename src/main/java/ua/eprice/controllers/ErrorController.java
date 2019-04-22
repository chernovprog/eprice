package ua.eprice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ua.eprice.services.MenuService;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@RestController
public class ErrorController {

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "errors", method = RequestMethod.GET)
    public ModelAndView renderErrorPage(HttpServletRequest servletRequest) {
        ModelAndView errorPage = new ModelAndView("errors/errorPage");
        String errorMsg = "";
        int httpErrorCode = getErrorCode(servletRequest);

        switch (httpErrorCode) {
            case 400: {
                errorMsg = "Http Error Code: 400. Bad Request";
                break;
            }
            case 401: {
                errorMsg = "Http Error Code: 401. Unauthorized";
                break;
            }
            /*case 404: {
                errorMsg = "Http Error Code: 404. Resource not found";
                break;
            }*/
            case 405: {
                errorMsg = "Http Error Code: 405. Method Not Allowed";
                break;
            }
            case 500: {
                errorMsg = "Http Error Code: 500. Internal Server Error";
                break;
            }
            case 501: {
                errorMsg = "Http Error Code: 501. Not Implemented";
                break;
            }
            case 504: {
                errorMsg = "Http Error Code: 504. Gateway Timeout";
                break;
            }
        }

        errorPage.addObject("errorMsg", errorMsg);
        errorPage.addObject("menu", menuService.getMenu());

        return errorPage;
    }

    private int getErrorCode(HttpServletRequest servletRequest) {
        return (Integer) servletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    }

    @GetMapping("404")
    public ModelAndView error404() {
        ModelAndView view = new ModelAndView("errors/404");
        view.addObject("menu", menuService.getMenu());

        return view;
    }

    @GetMapping("emptyProductList")
    public ModelAndView emptyProductList() {
        ModelAndView view = new ModelAndView("errors/errorPage");
        view.addObject("errorMsg", "Вашему выбору соответствует 0 товаров.");
        view.addObject("menu", menuService.getMenu());

        return view;
    }


}