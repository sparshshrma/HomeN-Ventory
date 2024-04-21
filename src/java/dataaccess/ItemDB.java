package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Category;
import models.Item;
import models.User;


public class ItemDB {
    
    public Item get(int id) throws Exception{
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Item item = em.find(Item.class, id);
            return item;
        } 
        
        finally{
            em.close();
        }
    }

    
    public List<Item> getAll() throws Exception{
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
           List<Item> items = em.createNamedQuery("Item.findAll", Item.class).getResultList();
           return items;
        }
        
        finally{
            em.close();
        }
    }
    

    public void insert(Item item)throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            Item newItem = item;
            Category category = newItem.getCategory();
            List<Item> items=category.getItemList();
            items.add(newItem);
            User user = newItem.getOwner();
            trans.begin();
            em.persist(newItem);
            em.merge(newItem);
            em.merge(category);
            em.merge(user);
            
            trans.commit();
            
        }
        
        catch (Exception e){
            trans.rollback();
        }
          
        
        finally{
            em.close();
        }
    }
    
      public void update(Item item) throws Exception{
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try{
            trans.begin();
            em.merge(item);
            trans.commit();
            
        } 
        
        catch (Exception e){
            trans.rollback();
        }
        finally {
            em.close();
        }
    }
    

    public void delete(Item item) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            User user = item.getOwner();
            Category category = item.getCategory();
            user.getItemList().remove(item);
            category.getItemList().remove(item);
            
            trans.begin();
            
            em.remove(em.merge(item));
            em.merge(category);
            em.merge(user);
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
