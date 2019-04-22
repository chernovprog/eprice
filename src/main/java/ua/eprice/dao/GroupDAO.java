package ua.eprice.dao;

import ua.eprice.model.menu.Group;

import java.util.List;

public interface GroupDAO {
    List<Group> popularProductsList();
    List<Group> householdProductsList();
    List<Group> kitchenProductsList();
}
