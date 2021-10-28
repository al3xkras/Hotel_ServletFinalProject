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
        <h1 class="form-signin-heading"><fmt:message key="registration.header"/></h1>

        <form action="${pageContext.request.contextPath}/app/registration"
            method="post" th:object="${registrationRequest}"
            class="form" autocomplete="off">

            <div class="form-group">
                <label for="name"><fmt:message key="form.name"/></label>
                <input id="name" type="text" th:pattern="#{regexp.name}" th:field="*{name}" required>
                <c:if test="${fields.hasErrors('name')}">
                    <div class="alert alert-warning" th:errors="*{name}"></div>
                </c:if>


            </div>

            <div class="form-group">
                <label for="surname"><fmt:message key="form.surname"/></label>
                <input id="surname" type="text" th:pattern="#{regexp.surname}" th:field="*{surname}" required>

                <c:if test="${fields.hasErrors('surname')}">
                    <div class="alert alert-warning" th:errors="*{surname}"></div>
                </c:if>
            </div>

            <div class="form-group">
                <label for="username"><fmt:message key="form.username"/></label>
                <input id="username" type="text" th:pattern="#{regexp.username}"  th:field="*{username}" required>
                <c:if test="${fields.hasErrors('username')}">
                    <div class="alert alert-warning" th:errors="*{username}"></div>
                </c:if>
                <c:if test="${usernameExists}">
                    <div class="alert alert-warning" th:if="${usernameExists}" th:text="#{form.alert.username_exists}"></div>
                </c:if>

            </div>

            <div class="form-group">
                <label for="password"><fmt:message key="form.password"/></label>
                <input id="password" type="password" th:field="*{password}" required>
                <c:if test="${fields.hasErrors('password')}">
                    <div class="alert alert-warning" th:errors="*{password}"></div>
                </c:if>
                <c:if test="${passwordMismatch}">
                    <div class="alert alert-warning" th:text="#{form.alert.password_mismatch}"></div>
                </c:if>
            </div>

            <div class="form-group">
                <label for="password-confirm"><fmt:message key="form.password_confirm"/></label>
                <input id="password-confirm" type="password" th:field="*{passwordConfirm}" required>
                <c:if test="${fields.hasErrors('passwordConfirm')}">
                    <div class="alert alert-warning" th:errors="*{passwordConfirm}"></div>
                </c:if>
                <c:if test="${passwordMismatch}">
                    <div class="alert alert-warning" th:text="#{form.alert.password_mismatch}"></div>
                </c:if>
            </div>

            <div class="form-group">
                <label for="birthday_date"><fmt:message key="form.birthday_date"/></label>
                <input id="birthday_date" type="date" th:field="*{birthdayDate}" required>
                <c:if test="${fields.hasErrors('birthdayDate')}">
                    <div class="alert alert-warning" th:errors="*{birthdayDate}"></div>
                </c:if>
            </div>

            <div class="form-group">
                <label th:text="#{form.gender}"><fmt:message key="form.gender"/></label>
                <div>
                    <input type="radio" th:field="*{gender}" value="Male"/><fmt:message key="form.gender.male"/>
                    <input type="radio" th:field="*{gender}" value="Female"/><fmt:message key="form.gender.female"/>
                </div>
                <c:if test="${fields.hasErrors('gender')}">
                    <div class="alert alert-warning" th:errors="*{gender}"></div>
                </c:if>
            </div>

            <div class="form-group">
                <label for="phone-number"><fmt:message key="form.phone_number"/></label>
                <input id="phone-number" type="text" th:pattern="#{regexp.phone_number}" th:field="*{phoneNumber}" required>
                <c:if test="${fields.hasErrors('phoneNumber')}">
                    <div class="alert alert-warning" th:errors="*{phoneNumber}"></div>
                </c:if>
            </div>

            <input type="text" th:value="${locale}" id="locale" name="locale" th:hidden="${true}"/>

            <button type="submit"><fmt:message key="form.submit"/></button>
        </form>
    </div>

    <script>
        document.getElementById("birthday_date").max = new Date().toLocaleDateString('en-ca')
    </script>
</body>
</html>