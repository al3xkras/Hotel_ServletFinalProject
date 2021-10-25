<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html xmlns:th="http://www.thymeleaf.org">
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

    <div style="width: 70%; height: 70%; margin: 15%">
        <form class="form-signin" method="post" action="/auth/login" autocomplete="off">
            <h2 class="form-signin-heading" th:text="#{login.header}"></h2>
            <p>
                <label for="username" th:text="#{form.username}"></label>
                <input type="text" id="username" name="username"
                       class="form-control" th:pattern="#{regexp.username}" required>

            <div class="alert alert-warning" style="margin-left: 15px"
                 th:if="${(param.error != null)}" th:text="#{login.alert.invalid_data}"></div>
            </p>

            <p>
                <label for="password" th:text="#{form.password}"></label>
                <input type="password" id="password" name="password"
                       class="form-control" required>
            </p>
            <button class="btn btn-lg btn-primary btn-block" type="submit" th:text="#{form.sign_in}"></button>
        </form>
    </div>

</body>
</html>