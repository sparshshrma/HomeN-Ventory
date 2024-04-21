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
import models.Item;
import models.Role;
import models.User;
import services.AccountService;
import services.InventoryService;


public class AccountServlet extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
        AccountService as = new AccountService();
        HttpSession session = request.getSession(); 

        String action = request.getParameter("action");
        
        if (action!= null){ 
            if (action.equals("update")) {
                try{    
                    String email = (String) session.getAttribute("email");
                    User user = as.get(email);
                    request.setAttribute("user", user);
      
                }
                catch (Exception e){
                    Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, e);
                }
    
            }    
        }
        getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").
                forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        String newEmail = request.getParameter("email"); 
        String userEmail = request.getParameter("originalemail");   
        String firstName = request.getParameter("firstName"); 
        String lastName = request.getParameter("lastName"); 
        String password = request.getParameter("password"); 
        
        AccountService as = new AccountService();
        
        String action = request.getParameter("action");       
        
 
        if(action !=null && action.equalsIgnoreCase("cancel")){
            response.sendRedirect("inventory");
            return;
        }        
               
        try{
            if (action!= null && action.equals("deactivate")){   
                User deactUser = as.get(userEmail);
                String first = deactUser.getFirstName();
                String last = deactUser.getLastName();
                String pass = deactUser.getPassword();
                Role role = deactUser.getRole();
                as.update(userEmail, false, first, last, pass, role);
                response.sendRedirect("login");
                return;
            }  
        } catch (Exception e){
             Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, e);
        }
        
        User newUser=null; 
        String message="";          
        
        if (newEmail == null){
            message ="Please enter your email";
            session.setAttribute("message", message);
            doGet(request, response);  
            return;
        }
           
        try {
            User originalUser = as.get(userEmail);
            newUser = as.get(newEmail);
            
            if(newUser==null){
                message ="Please fill all the form";
                session.setAttribute("message", message);
                doGet(request, response);
                return;
            }          
            
            if (action !=null && action.equals("save")){
                if(newUser.equals(originalUser)){  
                      Role role = newUser.getRole();
                      as.update(newEmail, true, firstName, lastName, password, role);
                    } else {
                        List<Item> itemList = originalUser.getItemList();
                        as.delete(originalUser.getEmail());
                        as.insert(newEmail, 2, firstName, lastName, password);
                        newUser = as.get(newEmail);
                        newUser.setItemList(itemList);
                    }
                    response.sendRedirect("inventory");
                    return;
            }
                       
        } catch (Exception e){
            Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, e);
        }
         
        getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").
                forward(request, response);
    }

}
