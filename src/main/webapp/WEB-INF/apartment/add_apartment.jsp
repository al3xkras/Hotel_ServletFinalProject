<%@ page import="ua.alexkras.hotel.model.ApartmentStatus" %>
<%@ page import="ua.alexkras.hotel.model.ApartmentClass" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">
<head>
    <meta charset="UTF-8">
    <title>New reservation</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">

    <style>
        div.alert{
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
    </style>
</head>
<body>
    <h1 style="text-align: center; margin-top: 5%; margin-bottom: 30px"><fmt:message key="new_apartment.header"/></h1>

    <div class="d-flex justify-content-center">

        <form class="form" action="${pageContext.request.contextPath}/app/admin/add_apartment" method="post" autocomplete="off">

            <div class="form-group">
                <label for="name"><fmt:message key="new_apartment.name"/></label>
                <input type="text" id="name"  minlength="3" maxlength="25"
                       name="name" required>
            </div>

            <div class="form-group">
                <label for="places"><fmt:message key="reservation.form.places"/></label>
                <input type="number" min="1" max="5" id="places" name="places" required>
            </div>

            <div class="form-group">
                <label for="apartmentClass"><fmt:message key="new_apartment.class"/></label>
                <select id="apartmentClass" name="apartmentClass" required>
                    <c:forEach items="<%=ApartmentClass.values()%>" var="apartmentClass">
                        <option value="${apartmentClass.name()}">
                            <fmt:message key="${apartmentClass.resName}"/>
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="status"><fmt:message key="new_apartment.status"/></label>
                <select id="status" name="status" required>
                    <c:forEach items="<%=ApartmentStatus.values()%>" var="status">
                        <option value="${status.name()}">
                            <fmt:message key="${status.resName}"/>
                        </option>
                    </c:forEach>
                </select>
            </div>


            <div class="form-group">
                <label for="price"><fmt:message key="new_apartment.price"/></label>
                <input type="number" name="price" id="price" min="0" required>
            </div>

            <button type="submit" class="btn btn-lg btn-primary btn-block"><fmt:message key="new_apartment.add"/></button>
        </form>
    </div>
</body>
</html>