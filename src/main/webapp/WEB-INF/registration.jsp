<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Login page</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">

    <style>
        .alert{
            background: red;
            stroke: black;
            color: black;
            font-size: 70%;
            width: fit-content;
            height: fit-content;

            padding: 1px;

            margin-left: 15px;
            margin-top: 3px;
            margin-bottom: 3px;
        }

        div .form-group{
            margin-top: 5px;
            margin-bottom: 5px;
            margin-left: 10px;
        }

    </style>
</head>
<body>

    <div style="width: 70%; height: 70%; margin: 15%">
        <h1 class="form-signin-heading" th:text="#{registration.header}"></h1>

        <form action="#" th:action="@{/registration}"
            method="post" th:object="${registrationRequest}"
            class="form" autocomplete="off">

            <div class="form-group">
                <label for="name" th:text="#{form.name}"></label>
                <input id="name" type="text" th:pattern="#{regexp.name}" th:field="*{name}" required>
                <div class="alert alert-warning" th:if="${#fields.hasErrors('name')}" th:errors="*{name}"></div>

            </div>

            <div class="form-group">
                <label for="surname" th:text="#{form.surname}"></label>
                <input id="surname" type="text" th:pattern="#{regexp.surname}" th:field="*{surname}" required>
                <div class="alert alert-warning" th:if="${#fields.hasErrors('surname')}" th:errors="*{surname}"></div>
            </div>

            <div class="form-group">
                <label for="username" th:text="#{form.username}"></label>
                <input id="username" type="text" th:pattern="#{regexp.username}"  th:field="*{username}" required>
                <div class="alert alert-warning" th:if="${#fields.hasErrors('username')}" th:errors="*{username}"></div>
                <div class="alert alert-warning" th:if="${usernameExists}" th:text="#{form.alert.username_exists}"></div>
            </div>

            <div class="form-group">
                <label for="password" th:text="#{form.password}"></label>
                <input id="password" type="password" th:field="*{password}" required>
                <div class="alert alert-warning" th:if="${#fields.hasErrors('password')}" th:errors="*{password}"></div>
                <div class="alert alert-warning" th:if="${passwordMismatch}" th:text="#{form.alert.password_mismatch}"></div>
            </div>

            <div class="form-group">
                <label for="password-confirm" th:text="#{form.password_confirm}"></label>
                <input id="password-confirm" type="password" th:field="*{passwordConfirm}" required>
                <div class="alert alert-warning" th:if="${#fields.hasErrors('passwordConfirm')}" th:errors="*{passwordConfirm}"></div>
                <div class="alert alert-warning" th:if="${passwordMismatch}" th:text="#{form.alert.password_mismatch}"></div>
            </div>

            <div class="form-group">
                <label for="birthday_date" th:text="#{form.birthday_date}"></label>
                <input id="birthday_date" type="date" th:field="*{birthdayDate}" required>
                <div class="alert alert-warning" th:if="${#fields.hasErrors('birthdayDate')}" th:errors="*{birthdayDate}"></div>
            </div>

            <div class="form-group">
                <label th:text="#{form.gender}"></label>
                <input type="radio" th:field="*{gender}" value="Male" th:text="#{form.gender.male}">
                <input type="radio" th:field="*{gender}" value="Female" th:text="#{form.gender.female}">
                <div class="alert alert-warning" th:if="${#fields.hasErrors('gender')}" th:errors="*{gender}"></div>
            </div>

            <div class="form-group">
                <label for="phone-number" th:text="#{form.phone_number}"></label>
                <input id="phone-number" type="text" th:pattern="#{regexp.phone_number}" th:field="*{phoneNumber}" required>
                <div class="alert alert-warning" th:if="${#fields.hasErrors('phoneNumber')}" th:errors="*{phoneNumber}"></div>
            </div>

            <input type="text" th:value="${#locale}" id="locale" name="locale" th:hidden="${true}"/>

            <button type="submit" th:text="#{form.submit}"></button>
        </form>
    </div>

    <script>
        document.getElementById("birthday_date").max = new Date().toLocaleDateString('en-ca')
    </script>
</body>
</html>