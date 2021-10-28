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

    <style>
        label {
            display: inline-block;
            width: 200px;
            margin: 5px;
            text-align: left;
        }

        input[type=text], input[type=password], select {
            display: inline-block;
            width: 200px;
        }
        input[type=radio] {
            margin-left: 45px;
        }
        input[type=checkbox] {
            margin-right: 190px;
        }
        button {
            padding: 5px;
            margin: 10px;
        }

        /* Add a black background color to the top navigation */
        .custom-navbar {
            background-color: #333;
            overflow: hidden;
        }

        /* Style the links inside the navigation bar */
        .custom-navbar a{
            float: left;
            color: #f2f2f2;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
            font-size: 17px;
        }

        /* Change the color of links on hover */
        .custom-navbar a:hover {
            background-color: #ddd;
            color: black;
        }

        /* Add a color to the active/current link */
        .custom-navbar a.active {
            background-color: #005e2a;
            color: white;
        }

        /* The dropdown container */
        .custom-navbar-dropdown {
            float: left;
            overflow: hidden;
        }

        /* Dropdown button */
        .custom-navbar-dropdown .dropdown-btn {
            font-size: 16px;
            border: none;
            outline: none;
            color: white;
            padding: 15px 16px;

            background-color: inherit;
            font-family: inherit; /* Important for vertical align on mobile phones */
            margin: 0; /* Important for vertical align on mobile phones */
        }

        /* Add a red background color to navbar links on hover */
        .custom-navbar a:hover, .custom-navbar-dropdown:hover .dropdown-btn {
            background-color: #007335;
        }

        /* Dropdown content (hidden by default) */
        .custom-dropdown-content {
            display: none;
            position: absolute;

            background-color: #f9f9f9;
            min-width: 160px;

            box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);
            z-index: 1;
        }

        /* Links inside the dropdown */
        .custom-dropdown-content a {
            float: none;
            color: black;
            padding: 12px 16px;
            text-decoration: none;
            display: block;
            text-align: left;
        }

        /* Add a grey background color to dropdown links on hover */
        .custom-dropdown-content a:hover {
            background-color: rgb(0, 66, 45);
        }

        /* Show the dropdown menu on hover */
        .custom-navbar-dropdown:hover .custom-dropdown-content {
            display: block;
        }
    </style>
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
</body>
</html>