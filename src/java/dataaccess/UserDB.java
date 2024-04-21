package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Category;
import models.Item;
import models.Role;
import models.User;


public class UserDB {

    public List<User> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try{
            List<User> users = em.createNamedQuery("User.findAll", User.class).getResultList();
            return users;
        } 
        
        finally {
            em.close();
        }
    }
    

    public User get(String email) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try{
            User user = em.find(User.class, email);
            return user;
        } 
        
        finally{
            em.close();
        }
    }
    

    public void insert(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans=em.getTransaction();
        
        try {
          trans.begin();
          em.persist(user);
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
    

    public void update(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans=em.getTransaction();
        
        try {
            trans.begin();
            em.merge(user);
            trans.commit();            
        } 
        
        catch (Exception e) {
            trans.rollback();
        } 
        
        finally{
            em.close();
        }
    }
    

    public void delete(String email) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();  
        EntityTransaction trans=em.getTransaction();
        
        try{
            User user = em.find(User.class, email);
            Role role = user.getRole();
            List<Item> itemList = user.getItemList();
            Item item;
            Category category ;
            role.getUserList().remove(user);
            
            trans.begin();
              em.remove(em.merge(user));     
              em.merge(role);
              
              for (int i=0; i<itemList.size(); i++){
                  item = itemList.get(i);
                  category = item.getCategory();
                  category.getItemList().remove(item);
                  em.merge(category);
                  itemList.remove(i); 
                  em.remove(em.merge(item));
              }
             
              trans.commit();
        } 
        
        catch (Exception e) {
          trans.rollback();
        }
        
        finally {
            em.close();
        }
    }
}
