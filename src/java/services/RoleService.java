package services;

import dataaccess.RoleDB;
import dataaccess.UserDB;
import java.util.List;
import models.Role;
import models.User;


public class RoleService {
    

    public List<Role> getAll() throws Exception{
        RoleDB roleDB = new RoleDB();
        List<Role> roles = roleDB.getAll();
        return roles;
    }
    

    public Role get(int roleId) throws Exception {
        RoleDB roleDB = new RoleDB();
        
        try {
            Role role = roleDB.get(roleId);
            return role;
        } catch (Exception e){
            
        }
        return null;
    }
}
