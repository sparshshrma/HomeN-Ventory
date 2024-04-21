
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Account</title>
       <link rel="stylesheet" type="text/css" href="css/Account.css" />
    </head>
    <body>
        <div class="div1">
            
        <h1>User Account ${user.firstName}</h1>      
            <c:if test="${user ne null}">
                <form method="post" action="account">
                    <input type="text" name="firstName" placeholder="First Name" value="${user.firstName}"> <br>
                    <input type ="text" name="lastName" placeholder="Last Name" value="${user.lastName}"> <br>
                    <input type="text" name="email" placeholder="Email" value="${user.email}"><br>
                    <input type="text" name="password" placeholder="Password" value="${user.password}"><br>   
                            
                     <%--Update user's new information--%>
                    <input type="hidden" name="originalemail" value="${user.email}">
                    <input type="hidden" name="action" value="save">
                    <input type="submit" value="Save"> <br> <br>
                </form>
            </c:if>
           
        <%--Cancel button to go back to inventory page--%>
        <form method="post" action="account">
                    <input type="hidden" name="action" value="cancel">
                    <input type="submit" value="Cancel Edit"> <br> <br>
        </form>
        <%--deactivate button--%>
         <form method="post" action="account">
             <input type="hidden" name="originalemail" value="${user.email}">
            <input type="hidden" name="action" value="deactivate">
            <input type="submit" value="Deactivate Account">
        </form>

        <%--to log out--%>
        <a href="login?logout">Sign Out</a>
        
        <p>${message}</p>

        </div>
    </body>
</html>
