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

    <h1 style="text-align: center; margin-top: 5%; margin-bottom: 30px"><fmt:message key="reservation.form.header"/></h1>

    <div class="d-flex justify-content-center">

        <form class="form" action="${pageContext.request.contextPath}/app/user/create_reservation" method="post" autocomplete="off">


            <div class="form-group">
                <label for="apartment-class"><fmt:message key="reservation.form.apartment_class"/></label>
                <select id="apartment-class" name="apartmentClass" required>
                    <c:forEach items="<%=ApartmentClass.values()%>" var="apartmentClass">
                        <option value="${apartmentClass.name()}">
                            <fmt:message key="${apartmentClass.resName}"/>
                        </option>
                    </c:forEach>

                </select>
            </div>

            <div class="form-group">
                <label for="places"><fmt:message key="reservation.form.places"/></label>
                <input type="number" min="1" max="5" id="places" name="places" required>
            </div>

            <div class="form-group">
                <label for="from_date"><fmt:message key="reservation.form.from_date"/></label>
                <input type="date" id="from_date" name="fromDate" required>
            </div>

            <div class="form-group">
                <label for="to_date"><fmt:message key="reservation.form.to_date"/></label>
                <input type="date" id="to_date" name="toDate" required>
                <c:if test="${requestScope.fromDateIsGreaterThanToDate}">
                    <div class="alert alert-warning"><fmt:message key="reservation.alert.date"/></div>
                </c:if>

            </div>

            <button type="submit" class="btn btn-lg btn-primary btn-block"><fmt:message key="form.submit"/></button>
        </form>
    </div>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/reservation/create_reservation.js"></script>
</body>
</html>