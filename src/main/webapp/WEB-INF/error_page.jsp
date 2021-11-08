<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">
<head>
    <meta charset="UTF-8">
    <title>Error</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">
</head>
<body>
    <div class="d-flex flex-column align-items-center" style="margin-top: 10%">
        <h1><fmt:message key="message.error"/></h1>
        <h2><fmt:message key="message.error.main_page"/></h2>
        <div class="form-group">
            <a href="${pageContext.request.contextPath}/"><fmt:message key="message.error.main"/></a>
        </div>
    </div>


</body>
</html>