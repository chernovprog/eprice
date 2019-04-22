package ua.eprice.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.eprice.model.menu.Group;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GroupDAOImpl implements GroupDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Group> popularProductsList() {
        TypedQuery<Group> query;
        List<Group> groups = new ArrayList<>();
        try {
            query = em.createQuery("SELECT g FROM Group AS g WHERE popular = 1", Group.class);
            groups = query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return groups;
    }

    @Override
    public List<Group> householdProductsList() {
        TypedQuery<Group> query;
        List<Group> groups = new ArrayList<>();
        try {
            query = em.createQuery("SELECT g FROM Group AS g WHERE household = 1", Group.class);
            groups = query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return groups;
    }

    @Override
    public List<Group> kitchenProductsList() {
        TypedQuery<Group> query;
        List<Group> groups = new ArrayList<>();
        try {
            query = em.createQuery("SELECT g FROM Group AS g WHERE kitchen = 1", Group.class);
            groups = query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return groups;
    }
}
