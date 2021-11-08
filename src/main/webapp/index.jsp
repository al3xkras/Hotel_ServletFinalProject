<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">
<head>
    <meta charset="UTF-8">
    <title>Hotel's Main Page</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_index.css">
</head>
<body>
    <div class="custom-navbar">
        <a class="active" href="${pageContext.request.contextPath}/">
            <fmt:message key="navbar.hotel"/></a>

        <div class="custom-navbar-dropdown" style="float: left">
            <button class="dropdown-btn"><fmt:message key="navbar.language"/>
                <i class="fa fa-caret-down"></i>
            </button>
            <div class="custom-dropdown-content">
                <a href="?lang=ua"><fmt:message key="navbar.language.ua"/></a>
                <a href="?lang=en"><fmt:message key="navbar.language.en"/></a>
            </div>
        </div>

        <a href="${pageContext.request.contextPath}/app/registration" style="float: right"><fmt:message key="navbar.registration"/></a>
        <a href="${pageContext.request.contextPath}/app/login" style="float: right"><fmt:message key="navbar.login"/></a>

    </div>

    <div style="margin-top: 15px; margin-left: 30px;">
        <a href="${pageContext.request.contextPath}/app/admin">/app/admin (test)</a><br/>
        <div style="margin-top: 15px">
            <a  href="${pageContext.request.contextPath}/app/user">/app/user (test)</a>
        </div>

    </div>
</body>
</html>