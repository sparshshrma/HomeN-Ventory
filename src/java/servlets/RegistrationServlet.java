package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Role;
import models.User;
import services.AccountService;


public class RegistrationServlet extends HttpServlet {


    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        

        getServletContext().getRequestDispatcher("/WEB-INF/registration.jsp").
                forward(request, response);
    }


    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          
        HttpSession session=request.getSession();

        
        String action = request.getParameter("action");
        String message="";
        
        AccountService as = new AccountService();
                
        if (action.equalsIgnoreCase("cancel")){
            message="";
            session.setAttribute("message", message);
            response.sendRedirect("login");
            return;
        }
              
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email=request.getParameter("email");
        String password=request.getParameter("password");
                
        boolean active = true;      
        
        if (action!= null && action.equals("register")){    
            if (firstName.isEmpty() || firstName==null || lastName.isEmpty() || lastName == null || 
                email.isEmpty() || email == null || password.isEmpty() || password == null) 
            {
                message = "Please fill all the fields";
                request.setAttribute("message", message);
                response.sendRedirect("registration");
                return;
            }
            
            
            else {                
                try{
                    as.insert(email, 2, firstName, lastName, password);
                    message="create";
                    session.setAttribute("message", message);
                    response.sendRedirect("login");
                    return;
                } 
                
                catch (Exception e){
                    Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, e);
                    }             
            }
        }
       
        getServletContext().getRequestDispatcher("/WEB-INF/registration.jsp").
                forward(request, response);   
    }      
}
