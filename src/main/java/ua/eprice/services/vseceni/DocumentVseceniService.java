package ua.eprice.services.vseceni;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import ua.eprice.exceptions.JsoupDocumentException;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
public class DocumentVseceniService {
    private static final String domain = "https://vseceni.ua";
    private static final Logger logger = Logger.getLogger(DocumentVseceniService.class);

    public Document getDocumentProductListPage(ServletRequest request, String page) {
        String path = getPath(request);
        page = validationNumberPage(page);
        String url = page.equals("0") ? domain + path : domain + path + "?page=" + page;

        Document doc;

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.error("HTTP error fetching URL. URL=" + path);
            throw new JsoupDocumentException("HTTP error fetching URL. URL=" + path);
        }

        return doc;
    }

    // In current page does not exist lint with offer. Create this one.
    public Document getDocumentProductWithOffersPage(ServletRequest request) {
        String path = getPath(request);
        String linkWithOffers = domain + path;

        Document doc;
        try {
            doc = Jsoup.connect(linkWithOffers).get();
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("HTTP error fetching URL. URL=" + path);
            throw new JsoupDocumentException("HTTP error fetching URL. URL=" + linkWithOffers);
        }

        return doc;
    }

    public Document getDocumentBySearchQuery(String query, String page) {
        page = validationNumberPage(page);
        String url = page.equals("1") ? domain + "/search/?fn=" + query : domain + "/search/?page=" + page + "&fn=" + query;

        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("HTTP error fetching URL. URL=" + url);
            throw new JsoupDocumentException("HTTP error fetching URL. URL=" + url);
        }

        return doc;
    }

    private String validationNumberPage(String page) {
        if (!NumberUtils.isDigits(page)) {
            return "0";
        } else {
            int pageInt = Integer.parseInt(page);
            if (pageInt < 1) {
                return "1";
            } else {
                return "" + pageInt;
            }
        }
    }

    private String getPath(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        return httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
    }

}