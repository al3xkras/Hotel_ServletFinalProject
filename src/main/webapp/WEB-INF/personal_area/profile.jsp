<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">
<head>
    <meta charset="UTF-8">
    <title>My profile</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_navbar.css">

</head>
<body>
<div class="custom-navbar">
    <c:set var="userType">
        ${requestScope.user.userType.toString().equalsIgnoreCase("admin")?"admin":"user"}
    </c:set>
    <a class="active" href="${pageContext.request.contextPath}/app/${userType}"><fmt:message key="navbar.hotel"/></a>

    <div class="custom-navbar-dropdown" style="float: left">
        <button class="dropdown-btn">
            <fmt:message key="navbar.language"/>
            <i class="fa fa-caret-down"></i>
        </button>
        <div class="custom-dropdown-content">
            <a href="?lang=ua"><fmt:message key="navbar.language.ua"/></a>
            <a href="?lang=en"><fmt:message key="navbar.language.en"/></a>
        </div>
    </div>

    <div class="custom-navbar-dropdown" style="float: right">
        <button class="dropdown-btn">
            <fmt:message key='${userType.equals("user")?"navbar.admin":"navbar.user"}'/>
            <i class="fa fa-caret-down"></i>
        </button>
    </div>

</div>
<div style="margin-top: 20px; margin-left: 24px;">
    <div class="d-flex flex-column align-items-left">

        <div class="row">
            <div class="col">
                <h2><fmt:message key="form.name"/>:</h2>
            </div>
            <div class="col-sm-4">
                <h2>${requestScope.user.name}</h2>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <h2><fmt:message key="form.surname"/>:</h2>
            </div>
            <div class="col-sm-4">
                <h2>${requestScope.user.surname}</h2>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <h2><fmt:message key="form.birthday_date"/>:</h2>
            </div>
            <div class="col-sm-4">
                <h2>${requestScope.user.birthday.toString()}</h2>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <h2><fmt:message key="form.phone_number"/>:</h2>
            </div>
            <div class="col-sm-4">
                <h2 style="float:left">${requestScope.user.phoneNumber}</h2>
            </div>
        </div>
    </div>
</div>
</body>
</html>
