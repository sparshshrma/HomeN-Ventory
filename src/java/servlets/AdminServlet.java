package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Category;
import models.Item;
import models.Role;
import models.User;
import services.AccountService;
import services.CategoryService;
import services.InventoryService;
import services.RoleService;


public class AdminServlet extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        AccountService as = new AccountService();
        CategoryService cs = new CategoryService();
     
        RoleService rs = new RoleService();
        
        HttpSession session = request.getSession();
        
        String email = (String) session.getAttribute("email");
        if (email==null){
            response.sendRedirect("login");
            return;
        }
               
        try{
            User user = as.get(email);
            List<User> users = as.getAll();
          
            List<Role> roles = rs.getAll();
            List<Category> categories=cs.getAll();
            request.setAttribute("user", user);
            request.setAttribute("users", users);
        
            request.setAttribute("roles", roles);
            request.setAttribute("categories", categories);
            session=request.getSession();
        }
        catch (Exception e){
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, e);
        }       
        
        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").
                forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String userEmail = request.getParameter("useremail");
        String newEmail = request.getParameter("email");
        String firstName = request.getParameter("firstName"); 
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password"); 
        String categoryId= request.getParameter("catID"); 
        String categoryName = request.getParameter("catName"); 
        
        String userRole = request.getParameter("role");
        int userRoleId;
        
        User newUser=null; 
        String message=""; 
        
        AccountService as = new AccountService();
        CategoryService cs=new CategoryService();
        
        
        String catMessage="";
        Category category;
        int catId;
 
        try{
            if (action!= null && action.equals("reactivate")){   
                User reactUser = as.get(userEmail);
                boolean status= reactUser.getActive();
                String first = reactUser.getFirstName();
                String last = reactUser.getLastName();
                String pass = reactUser.getPassword();
                Role role = reactUser.getRole();
                
                if (!status)              
                    as.update(userEmail, true, first, last, pass, role);
                    
                response.sendRedirect("admin");
                return;
            }  
        } catch (Exception e){
             Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, e);
        }
        

        if (action!=null){
            try {
                switch(action) {
                    case "edit":
                        if (userEmail !=null || !userEmail.isEmpty()){
                            User editUser = as.get(userEmail);
                            if (editUser==null){
                                message = "user can't be edited";
                                request.setAttribute("message", message);
                            } 
                            session.setAttribute("editUser",editUser);
                            response.sendRedirect("admin");
                            return;
                        }
                    case "delete":
                        if (userEmail == null || userEmail.isEmpty()){
                             message = "user can't be edited";
                             request.setAttribute("message", message);
                             response.sendRedirect("admin");
                             return;
                        }
                        User editUser = as.get(userEmail);
                        as.delete(userEmail);
                        response.sendRedirect("admin");
                        return;
                                              
                    case "add":
                        if (newEmail == null || firstName ==null || lastName ==null || password==null ||
                            newEmail.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || password.isEmpty()){
                                message ="Please complete the form";
                                session.setAttribute("message", message);
                                response.sendRedirect("admin");
                                return;
                        }
                        if (userRole==null || userRole.isEmpty()){
                            userRole = "2";
                        }
                        userRoleId = Integer.parseInt(userRole);
                        
                        as.insert(newEmail, userRoleId, firstName, lastName, password);
                        response.sendRedirect("admin");
                        return;
                        
                    case "update":
                        if (userEmail==null || userEmail.isEmpty()){
                            userEmail = request.getParameter("originalemail");
                        }                
                        User originalUser = as.get(userEmail);
                       
                
                        if (userRole==null || userRole.isEmpty()){
                            userRole = "2";
                        }
                        
                        userRoleId = Integer.parseInt(userRole);
                        Role role = originalUser.getRole();
                        role.setRoleId(userRoleId);
                        originalUser.setRole(role);
                        
                   
                        if(newEmail==null || newEmail.isEmpty()){
                            message ="Please complete the form";
                            session.setAttribute("message", message);
                            response.sendRedirect("admin");
                            return;
                        }              
                        
                        newUser = as.get(newEmail); 
        
                        if (newUser==null){
                            List<Item> itemList = originalUser.getItemList();
                            as.delete(originalUser.getEmail());
                            as.insert(newEmail, 2, firstName, lastName, password);
                            newUser = as.get(newEmail);
                            newUser.setItemList(itemList);
                        }
          
                        else if(newUser.equals(originalUser)){       
                            
                            as.update(newEmail, true, firstName, lastName, password, role);
                        }                      
                        session.setAttribute("editUser", null);
                        response.sendRedirect("admin");
                        return;
                    case "addCat" :
                        if (categoryId==null || categoryId.isEmpty()){
                            categoryId="0";
                        }
                        catId = Integer.parseInt(categoryId);
                        category = new Category(catId);
                        if (categoryName == null || categoryName.isEmpty()) {
                            catMessage="Please enter category name";
                            request.setAttribute("catMessage", catMessage);
                            response.sendRedirect("admin");
                            return;
                        }
                        category.setCategoryName(categoryName);
                        cs.insert(categoryName);
                        response.sendRedirect("admin");
                        return;
                        
                    case "editCat":
                        if (categoryId==null || categoryId.isEmpty()){ 
                            categoryId="0";
                        }
                        catId = Integer.parseInt(categoryId);
                        category = cs.get(catId);
                        session.setAttribute("editCategory", category);
                        response.sendRedirect("admin");
                        return;
                        
                    case "updateCat":
                        if (categoryId==null || categoryId.isEmpty()){
                            categoryId="0";
                        }
                        catId = Integer.parseInt(categoryId);
                        category = cs.get(catId);
                        if (categoryName == null || categoryName.isEmpty()) {
                            catMessage="Please enter category name";
                            request.setAttribute("catMessage", catMessage);
                            response.sendRedirect("admin");
                            return;
                        }
                        category.setCategoryName(categoryName);
                        cs.update(catId,categoryName);
                        session.setAttribute("editCategory", null);
                        response.sendRedirect("admin");
                        return;
                }  
            } catch (Exception e){
                Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, e);
            }
        }  
     
        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").
                forward(request, response);
    }
}
