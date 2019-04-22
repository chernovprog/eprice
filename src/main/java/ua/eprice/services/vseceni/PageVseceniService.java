package ua.eprice.services.vseceni;

import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import ua.eprice.model.product.page.Page;
import ua.eprice.model.product.page.info.Breadcrumbs;
import ua.eprice.model.product.page.info.Pagination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PageVseceniService {
    private static final String domain = "https://vseceni.ua";

    public Page getInfoFromProductListPage(Document doc) {
        Page page = new Page();
        page.setTitle(this.parseTitle(doc));
        page.setPagination(this.parsePagination(doc));
        page.setBreadcrumbs(this.parseBreadcrumbsList(doc));
        page.setCategory(this.parseCategory(doc));
        page.setPath(this.getPath(doc));

        return page;
    }

    public Page getInfoFromProductPage(Document doc) {
        Page page = new Page();
        page.setTitle(this.parseTitle(doc));
        page.setPagination(this.parsePagination(doc));
        page.setBreadcrumbs(this.parseBreadcrumbsList(doc));

        return page;
    }

    private Pagination parsePagination(Document doc) {
        Pagination pagination = new Pagination();
        List<String> rangPages = this.parseRangPages(doc);
        pagination.setActivePageNumber(this.getActivePageNumber(doc));

        if (!rangPages.isEmpty()) {
            pagination.setRangePages(rangPages);
        }

        if (rangPages.size() >= 5) {
            String lastPageRangStr = rangPages.get(rangPages.size() - 1);
            String lastPageSiteStr = doc.select(".catalog-panel:nth-child(1) .pagination ul :nth-last-child(3)").text();
            if (NumberUtils.isDigits(lastPageRangStr) && NumberUtils.isDigits(lastPageSiteStr)) {
                int lastPageRang = Integer.parseInt(lastPageRangStr);
                int lastPageSite = Integer.parseInt(lastPageSiteStr);
                if (lastPageRang <= (lastPageSite - 2)) {
                    pagination.setNumberLastPage(lastPageSiteStr);
                }
            }
        }

        return pagination;
    }

    private List<Breadcrumbs> parseBreadcrumbsList(Document doc) {
        List<Breadcrumbs> breadcrumbsList = new ArrayList<>();

        Elements elements = doc.select(".breadcrumb li");
        Breadcrumbs breadcrumbs;
        for (Element e : elements) {
            if (!"".equals(e.text())) {
                breadcrumbs = new Breadcrumbs(e.text());
                breadcrumbsList.add(breadcrumbs);
            }
        }

        return breadcrumbsList;
    }

    private List<String> parseRangPages(Document doc) {
        List<String> list = doc.select(".catalog-panel:nth-child(1) .pagination .page").eachText();
        if (list.size() == 9) list = list.subList(2, list.size());
        List<String> result = new ArrayList<>();
        for (String s : list) {
            if (s.equals("...")) break;
            result.add(s);
        }
        return result;
    }

    private String parseTitle(Document doc) {
        String title = doc.select(".breadcrumb h1").html();
        if (!"".equals(title) && title.indexOf("<small>") > 0) {
            title = title.substring(0, title.indexOf("<small>"));
        }
        return title.replace("<span>", "");
    }

    private String parseCategory(Document doc) {
        String category = doc.select(".breadcrumb h1").html();
        if (!"".equals(category) && category.indexOf("<small>") > 0) {
            category = category.substring(0, category.indexOf("<small>"));
        }
        return category.replace("<span>", "");
    }

    private String getActivePageNumber(Document doc) {
        String baseUri = doc.baseUri();

        if (baseUri != null) {
            int indexParam = baseUri.indexOf("page=");
            if (indexParam < 0) {
                return "1";
            } else {
                if (indexParam + 5 < baseUri.length()) {
                    String pageNumberStr = baseUri.substring(indexParam + 5);
                    if (NumberUtils.isDigits(pageNumberStr)) {
                        int pageNumber = Integer.parseInt(pageNumberStr);
                        return "" + pageNumber;
                    } else {
                        String[] split = pageNumberStr.split("");
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < split.length; i++) {
                            if (NumberUtils.isDigits(split[i])) sb.append(split[i]);
                            else break;
                        }
                        if (sb.length() > 0) {
                            int pageNumber = Integer.parseInt(sb.toString());
                            return "" + pageNumber;
                        }
                    }
                }
            }
        }
        return "";
    }

    private String getPath(Document doc) {
        String baseUri = doc.baseUri().replace(domain, "");
        //if (baseUri.contains("/sr/")) baseUri = baseUri.replace("/sr/", "/search/");

        return !baseUri.contains("?") ? baseUri : baseUri.substring(0, baseUri.indexOf("?"));
    }

}