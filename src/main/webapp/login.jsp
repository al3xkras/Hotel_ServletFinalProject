<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">
<head>
    <meta charset="UTF-8">
    <title>Login page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">

    <style>
        div.alert{
            background: red;
            stroke: black;
            color: black;
            font-size: 100%;
            width: fit-content;
            height: fit-content;

            padding: 4px;

            margin-left: 15px;
            margin-top: 3px;
            margin-bottom: 3px;
        }

        h2.form-signin-heading{
            text-align: center;
        }
    </style>
</head>
<body>
    <c:set var="username_regex">
        <fmt:message key="regexp.username"/>
    </c:set>

    <div style="width: 70%; height: 70%; margin: 15%">
        <form class="form-signin" method="post" action="${pageContext.request.contextPath}/app/login" autocomplete="off">
            <h2 class="form-signin-heading"><fmt:message key="login.header"/></h2>
            <p>
                <label for="username"><fmt:message key="form.username"/></label>
                <input type="text" id="username" name="username"
                       class="form-control" pattern="${username_regex}" required/><fmt:message key="form.username"/>

                <c:if test="${param.error != null}">
                    <div class="alert alert-warning" style="margin-left: 15px"><fmt:message key="login.alert.invalid_data"/></div>
                </c:if>
            </p>

            <p>
                <label for="password"><fmt:message key="form.password"/></label>
                <input type="password" id="password" name="password"
                       class="form-control" required>
            </p>
            <button class="btn btn-lg btn-primary btn-block"
                    type="submit"><fmt:message key="form.sign_in"/></button>
        </form>
    </div>

</body>
</html>