<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Account</title>
        <link rel="stylesheet" type="text/css" href="css/Inventory.css" />
    </head>
    <body>
        <div class="div1">
            
        <h1>Hello ${user.firstName}</h1>
        
        <h2>Manage Inventory</h2>
        <table class="center" >
            <tr>
                <th>Category</th>
                <th>Item Name</th>
                <th>Price</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
          
            <c:forEach items="${items}" var="readItem">
                <tr>
                    <td>
                        <c:forEach items="${categories}" var="cat">
                            <c:if test="${readItem.category.categoryId eq cat.categoryId}">
                                ${cat.categoryId}
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>${readItem.itemName}</td>  
                    <td>${readItem.price}</td>

                    <td>
                        <form method ="post" action="inventory">
                            <input type="hidden" name="itemID" value="${readItem.itemId}">
                            <input type="hidden" name="action" value="edit">
                            <input type="submit" value="Edit">
                        </form>    
                    </td>
                    <td>
                        <form method="post" action="inventory">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="itemID" value="${readItem.itemId}">
                            <input type="submit" value="Delete">
                        </form>
                    </td>
                </tr>               
            </c:forEach>
        </table>
        
        <div>
            <c:if test="${empty editItem}">
                <h2>Add Item</h2>
                <form method="post" action="inventory">
                    <select name="category">
                        <c:forEach items="${categories}" var="cat">
                            <option value="${cat.categoryId}">${cat.categoryName}
                               
                            </option>
                        </c:forEach>
                    </select> <br>
                    <input type="text" name="itemname" placeholder="Item Name" value=""> <br>
                    <input type="text" name="price" placeholder="Price" value=""> <br>   <br>  
                                   
                    <input type="hidden" name="useremail" value="${user.email}">
                    <input type="hidden" name="itemID" value="0">
                    <input type="hidden" name="action" value="add">
                    <input type="submit" value="Add">
                </form>
            </c:if>
        </div>
        
        
        <div>  
            <c:if test="${not empty editItem}">
            
                <h1>Edit Item</h1> 
                <form method="post" action="inventory">
                    <select name="category">
                        <c:forEach items="${categories}" var="cat">
                            <option value="${cat.categoryId}"
                                <c:if test="${editItem.category.categoryId eq cat.categoryId}">selected</c:if>>${cat.categoryName}
                             </option>
                        </c:forEach>
                    </select> <br>
                    <input type="text" name="itemname" placeholder="Item Name" value="${editItem.itemName}"><br>
                    <input type="text" name="price" placeholder="Price" value="${editItem.price}"> <br>   <br>     
               
                    <input type="hidden" name="useremail" value="${user.email}">
                    <input type="hidden" name="itemID" value="${editItem.itemId}">
                    <input type="hidden" name="action" value="save">
                    <input type="submit" value="Save">
                </form>
            </c:if>
        </div>
        
        <form method="get" action="account">
            <input type="hidden" name="useremail" value="${user.email}">
            <input type="hidden" name="action" value="update">
            <input type="submit" value="Edit Account">
        </form>
         <br>
        <%--to log out--%>
        <a href="login?logout">Sign Out</a>
        
        </div>
    </body>
    </html>