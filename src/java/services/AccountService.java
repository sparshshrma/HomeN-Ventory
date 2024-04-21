package services;

import dataaccess.UserDB;
import java.util.ArrayList;
import java.util.List;
import models.Item;
import models.Role;
import models.User;


public class AccountService {
    

    public User login (String email, String password) {
        UserDB userDB = new UserDB();
        
        try {
            User user = userDB.get(email);
            if (password.equals(user.getPassword())) {
                return user;
            }
     
        }
        
        catch (Exception e) {
            
        }
            return null;
    }
    

    public List<User> getAll() throws Exception{
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAll();
        return users;
    }
    

    public User get(String email) throws Exception {
        UserDB userDB = new UserDB();
        
        try {
            User user = userDB.get(email);
            return user;
        } 
        
        catch (Exception e){
            
        }
        return null;
    }
    

    public void insert (String email, int roleId, String firstName, String lastName, String password) throws Exception {
        UserDB userDB = new UserDB();
        boolean active = true;
        User user = new User(email, active, firstName, lastName, password);
        Role role = new Role(roleId);
        List<Item> items= new ArrayList<Item>();
        user.setItemList(items);
        user.setRole(role);
        userDB.insert(user);
    }
    

    public void update (String email, boolean status, String firstName, String lastName, String password, Role role) throws Exception{
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        List<Item> items = user.getItemList();
        
        user.setRole(role);
        user.setActive(status);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        userDB.update(user);
    }
    
    public void delete(String email) throws Exception{
        UserDB userDB = new UserDB();
        userDB.delete(email);
    }
}
