<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home nVentory</title>
           <link rel="stylesheet" type="text/css" href="css/Login.css" />
    </head>
    <body>
     
        <div class="div1">
            
        <h1>Home nVentory </h1>
        
       <form action="login" method="post">
            email: <input type="text" name="email"><br>
            password: <input type="password" name="password"><br>
            <input type="hidden" name="action" value="signin">
            <input type="submit" value="Sign in"> <br>           
       </form>
        
        <form method="get" action="registration"> 
                <input type="hidden" name="action" value="register">
                <input type="submit" value="Register">
        </form>
        <p>
             <c:if test="${message eq 'notfound'}">User not found </c:if>
            <c:if test="${message eq 'logout'}"> successfully logged out</c:if>
            <c:if test="${message eq 'create'}">Account is created successfully </c:if>
            <c:if test="${message eq 'inactive'}">Account disabled. Please contact admin to reactivate </c:if>
            <c:if test="${message eq 'empty'}">Please enter username and password</c:if>
        </p>
        
        </div>
    </body>
</html>
