package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Category;
import models.User;

public class CategoryDB {
    

    public Category get(int id) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try{
            Category category=em.find(Category.class, id);
            return category;
        }
        
        finally{
            em.close();
        }
    }
    

    public List<Category> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List<Category> categories = em.createNamedQuery("Category.findAll", Category.class).getResultList();
            return categories;
        }
        finally{
            em.close();
        }
    }
    

    public void insert(Category category) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans= em.getTransaction();
        
        try{
            trans.begin();
            em.persist(category);
            em.merge(category);
            trans.commit();
        }
        
        catch (Exception e){
            trans.rollback();
        }
        
        finally {
            em.close();
        }
    }
    

    public void update(Category category) throws Exception{
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans= em.getTransaction();
        
        try {
            trans.begin();
            em.merge(category);
            trans.commit();
        } 
        
        catch (Exception e){
            trans.rollback();
        } 
        
        finally {
            em.close();
        }
    }
    
}
