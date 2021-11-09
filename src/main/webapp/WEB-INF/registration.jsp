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
    <c:set var="name_regex">
        <fmt:message key="regexp.name"/>
    </c:set>
    <c:set var="surname_regex">
        <fmt:message key="regexp.surname"/>
    </c:set>
    <c:set var="username_regex">
        <fmt:message key="regexp.username"/>
    </c:set>
    <c:set var="phone_regex">
        <fmt:message key="regexp.phone_number"/>
    </c:set>

    <c:set var="data_invalid_format">
        <fmt:message key="data.invalid_format"/>
    </c:set>

    <div style="width: 70%; height: 70%; margin: 15%">
        <h1 class="form-signin-heading"><fmt:message key="registration.header"/></h1>

        <form action="${pageContext.request.contextPath}/app/registration"
            method="post" class="form" autocomplete="off">

            <div class="form-group">
                <label for="name"><fmt:message key="form.name"/></label>
                <input id="name" minlength="2" name="name" type="text" pattern="${name_regex}"
                       title="${data_invalid_format} ${name_regex}"
                       required>
                <c:if test='${requestScope.errorField.equals("name")}'>
                    <div class="alert alert-warning"><fmt:message key="${requestScope.errorMessage}"/></div>
                </c:if>
            </div>

            <div class="form-group">
                <label for="surname"><fmt:message key="form.surname"/></label>
                <input id="surname" minlength="2" name="surname" type="text" pattern="${surname_regex}"
                       title="${data_invalid_format} ${name_regex}"
                       required>

                <c:if test='${requestScope.errorField.equals("surname")}'>
                    <div class="alert alert-warning"><fmt:message key="${requestScope.errorMessage}"/></div>
                </c:if>
            </div>

            <div class="form-group">
                <label for="username"><fmt:message key="form.username"/></label>
                <input id="username" minlength="3" maxlength="15" name="username" type="text" pattern="${username_regex}"
                       title="${data_invalid_format} ${username_regex}" required>
                <c:if test='${requestScope.errorField.equals("username")}'>
                    <div class="alert alert-warning"><fmt:message key="${requestScope.errorMessage}"/></div>
                </c:if>
            </div>

            <div class="form-group">
                <label for="password"><fmt:message key="form.password"/></label>
                <input id="password" minlength="8" maxlength="20" name="password" type="password"
                       required>
                <c:if test='${requestScope.errorField.equals("password")}'>
                    <div class="alert alert-warning"><fmt:message key="${requestScope.errorMessage}"/></div>
                </c:if>
            </div>

            <div class="form-group">
                <label for="password-confirm"><fmt:message key="form.password_confirm"/></label>
                <input id="password-confirm" minlength="8" maxlength="20" name="passwordConfirm" type="password"
                       required>
                <c:if test='${requestScope.errorField.equals("password")}'>
                    <div class="alert alert-warning"><fmt:message key="${requestScope.errorMessage}"/></div>
                </c:if>
            </div>

            <div class="form-group">
                <label for="birthday_date"><fmt:message key="form.birthday_date"/></label>
                <input id="birthday_date" name="birthdayDate" type="date" required>
                <c:if test='${requestScope.errorField.equals("birthdayDate")}'>
                    <div class="alert alert-warning"><fmt:message key="${requestScope.errorMessage}"/></div>
                </c:if>
            </div>

            <div class="form-group">
                <label><fmt:message key="form.gender"/></label>
                <div>
                    <input type="radio" name="gender" value="Male"
                            /><fmt:message key="form.gender.male"/>
                    <input type="radio" name="gender" value="Female"
                            /><fmt:message key="form.gender.female"/>
                </div>
                <c:if test='${requestScope.errorField.equals("gender")}'>
                    <div class="alert alert-warning"><fmt:message key="${requestScope.errorMessage}"/></div>
                </c:if>
            </div>

            <div class="form-group">
                <label for="phone-number"><fmt:message key="form.phone_number"/></label>
                <input id="phone-number" name="phoneNumber" type="text" pattern="${phone_regex}"
                       title="${data_invalid_format} ${phone_regex}" required>
                <c:if test='${requestScope.errorField.equals("phoneNumber")}'>
                    <div class="alert alert-warning"><fmt:message key="${requestScope.errorMessage}"/></div>
                </c:if>
            </div>

            <button type="submit" onclick="savePageVariables()"><fmt:message key="form.submit"/></button>
        </form>
    </div>

    <script>
        const clear_session_storage = ${requestScope.clear_session_storage==null?false:true};
        if (clear_session_storage){
            sessionStorage.clear();
        }
    </script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/registration.js"></script>
</body>
</html>