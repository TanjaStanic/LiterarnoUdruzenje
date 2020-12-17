<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Registration</title>
        <link rel="stylesheet"
              href="webjars/bootstrap/css/bootstrap.min.css"/>
        <script src="webjars/jquery/jquery.min.js"></script>
        <script src="webjars/bootstrap/js/bootstrap.min.js"></script>

    </head>
    <body>
        <div class="container h-100">
            <div class="row h-100 justify-content-center align-items-center">

                <div>
                    <form:form action="register" method="post" class="was-validated" modelAttribute="registrationDTO">
                        <div class="form-group col-sm-12" >
                            <form:label path="email">Email</form:label> <br/>
                            <form:input path="email" type="email" required = "true"/> <br/>
                            <div class="invalid-feedback">Please fill out this field.</div>
                        </div>

                        <div class="form-group col-sm-12">
                            <form:label path="clientSecret">Paypal Client Secret</form:label> <br/>
                            <form:input path="clientSecret" type="text" required = "true"/> <br/>
                            <div class="invalid-feedback">Please fill out this field.</div>
                        </div>

                        <div class="form-group col-sm-12">
                            <form:label path="clientId">Paypal Client Id</form:label> <br/>
                            <form:input path="clientId" type="text" required = "true"/> <br/>
                            <div class="invalid-feedback">Please fill out this field.</div>
                        </div>
                        <div class="col-sm-12">
                            <input class="btn btn-primary btn-block" type="submit" value="Submit"/>
                        </div>

                    </form:form>
                </div>
            </div>
        </div>
    </body>
</html>