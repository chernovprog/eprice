package ua.eprice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.eprice.dao.GroupDAO;
import ua.eprice.dao.MarketDAO;
import ua.eprice.dao.RubricDAO;
import ua.eprice.model.menu.Group;
import ua.eprice.model.menu.Market;

import java.util.List;

@Service
public class MenuService {
    @Autowired
    private MarketDAO marketDAO;

    @Autowired
    private RubricDAO rubricDAO;

    @Autowired
    private GroupDAO groupDAO;

    public List<Market> getMenu() {
        List<Market> marketList =  marketDAO.marketList();

        return marketList;
    }

    public List<Group> getPopularProductsList() {
        List<Group> groupList = groupDAO.popularProductsList();
        return groupList;
    }

    public List<Group> getHouseholdProductsList() {
        List<Group> groupList = groupDAO.householdProductsList();
        return groupList;
    }

    public List<Group> getKitchenProductsList() {
        List<Group> groupList = groupDAO.kitchenProductsList();
        return groupList;
    }
}
