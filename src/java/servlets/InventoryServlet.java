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
import models.User;
import services.AccountService;
import services.CategoryService;
import services.InventoryService;


public class InventoryServlet extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        AccountService as = new AccountService();
        CategoryService cs = new CategoryService();
        InventoryService is = new InventoryService();
        
        HttpSession session = request.getSession();
        
        String email = (String) session.getAttribute("email");
        if (email==null){
            response.sendRedirect("login");
            return;
        }
               
        try{
            User user = as.get(email);
            List<Item> items= user.getItemList();
            List<Category> categories=cs.getAll();
            request.setAttribute("user", user);
            request.setAttribute("items", items);
            request.setAttribute("categories", categories);
            session=request.getSession();
        }
        catch (Exception e){
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, e);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
        HttpSession session = request.getSession();
        AccountService as = new AccountService();
        InventoryService is = new InventoryService();
        CategoryService cs = new CategoryService();       
        

        String action = request.getParameter("action");
        String itemID = request.getParameter("itemID");  
        String itemName= request.getParameter("itemname"); 
        String email= request.getParameter("useremail"); 
        String price = request.getParameter("price"); 
        String catID = request.getParameter("category");      
        int itemId;
        
        if (action !=null){
            try {             
                if (itemID==null || itemID.isEmpty()){
                    itemID = (String) session.getAttribute("itemID");   
                }
                
                itemId = Integer.parseInt(itemID);   
                Item item = is.get(itemId);
                
                if (action.equals("edit")){               
                    List<Category> categories=cs.getAll();
                    request.setAttribute("categories", categories);
                    session.setAttribute("editItem", item);
                    response.sendRedirect("inventory");
                    return;
                }
                else if (action.equals("delete")) { 
                    is.delete(itemId);
                    response.sendRedirect("inventory");
                    return;
                }
                                            
                double itemPrice = Double.parseDouble(price);
                
                if (catID==null || catID.isEmpty()){
                    catID="1";
                }
                int categoryID = Integer.parseInt(catID);  
                Category category = cs.get(categoryID);
                User user= as.get(email); 
                            
                if (action.equals("add")){             
                    is.insert(itemName, itemPrice, category, user);
                    List<Item> items=user.getItemList();
                    List<Category> categories=cs.getAll();
                    request.setAttribute("categories", categories);
                    request.setAttribute("items", items);
                }
                else if (action.equals("save")){
                    is.update(itemId, itemName, itemPrice, categoryID);
                    List<Item> items=user.getItemList();
                    List<Category> categories=cs.getAll();
                    request.setAttribute("categories", categories);
                    request.setAttribute("items", items);
                }
                else if (action.equals("update")){ 
                    session.setAttribute("useremail", email);
                    response.sendRedirect("account");
                    return;
                }        
            }
            catch (Exception e){
                Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, e);
            }         
        }       
        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp")
            .forward(request, response);         
    }
}
