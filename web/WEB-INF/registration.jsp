<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration Page</title>
        <link rel="stylesheet" type="text/css" href="css/Registration.css" />
    </head>
    <body>
        <div class="div1">
            
        <h1>Home nVentory</h1>
        <h2>Registration</h2>
        <form method="post" action="registration">
            
            First Name: <input type="text" name="firstName"> <br>
            Last Name: <input type="text" name="lastName" > <br>
            Email: <input type="text" name="email" > <br>
            Password: <input type="password" name="password" > <br><br>
            
            <input type="hidden" name="action" value="register">
            <input type="submit" value="Register">  <br>
        
            
        </form>
        
        <form method="post" action="registration">
                <input type="hidden" name="action" value="cancel">
                <input type="submit" value="Cancel">
        </form>
        <p>${message}</p>
        
        </div>
    </body>
</html>
