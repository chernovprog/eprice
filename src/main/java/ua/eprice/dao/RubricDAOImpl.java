package ua.eprice.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.eprice.model.menu.Rubric;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RubricDAOImpl implements RubricDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Rubric> rubricList() {
        TypedQuery<Rubric> query = null;
        List<Rubric> rubrics = new ArrayList<>();
        try {
            query = em.createQuery("SELECT r FROM Rubric AS r", Rubric.class);
            rubrics = query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return rubrics;
    }



}
