<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">

<head>
    <meta charset="UTF-8">
    <title>Personal area</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_navbar.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_reservation_status.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_personal_area.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_apartment.css" />

</head>
<body style="background: #f6f6f6;">
    <div class="custom-navbar">
        <a class="active" href="${pageContext.request.contextPath}/"><fmt:message key="navbar.hotel"/> </a>
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
                <fmt:message key="navbar.user"/>
                <i class="fa fa-caret-down"></i>
            </button>
            <div class="custom-dropdown-content" style="left: 80%; width: 19.5%">
                <a href="${pageContext.request.contextPath}/app/create_reservation"><fmt:message key="navbar.new_reservation"/></a>
                <a href="${pageContext.request.contextPath}/app/apartments"><fmt:message key="navbar.all_apartments"/></a>
                <a href="${pageContext.request.contextPath}/app/personal_data"><fmt:message key="navbar.personal_data"/></a>
            </div>
        </div>

        <a href="${pageContext.request.contextPath}/app/logout" style="float:right;">
            <fmt:message key="navbar.logout"/>
        </a>
    </div>

    <div class="reservations-list">
        <h2 style="text-align: center"><fmt:message key="navbar.my_reservations"/></h2>
        <table class="reservations">
            <tr class="align-middle">
                <th><fmt:message key="reservation.submit_date"/></th>
                <th><fmt:message key="new_apartment.places"/></th>
                <th><fmt:message key="reservation.form.from_date"/></th>
                <th><fmt:message key="reservation.form.to_date"/></th>
                <th><fmt:message key="new_apartment.class"/></th>
                <th><fmt:message key="apartment.id"/></th>
                <th><fmt:message key="new_apartment.price"/></th>
                <th><fmt:message key="reservation.status"/></th>
                <th><fmt:message key="reservation.payment_status"/></th>
            </tr>


            <tr class="align-middle"
                th:each="reservation : ${allReservations}"
                th:onclick="'window.location=\'/user/reservation/'+${reservation.id}+'\';'">
                <td th:text='${reservation.submitDate.toString().split("T")[0]}'></td>
                <td th:text="${reservation.places}"></td>
                <td th:text='${reservation.fromDate.toString().replace("T"," ")}'></td>
                <td th:text='${reservation.toDate.toString().replace("T"," ")}'></td>
                <td th:class="${reservation.apartmentClass.htmlClass}"
                    th:text="#{${reservation.apartmentClass.resName}}"></td>
                <td th:class="${reservation.apartmentId==null?'red':'green'}"
                    th:text="${reservation.apartmentId==null?'-':reservation.apartmentId}"></td>
                <td th:class="${reservation.apartmentPrice==null?'red':'green'}"
                    th:text="${reservation.apartmentPrice==null?'-':reservation.apartmentPrice}"></td>
                <td th:class="${reservation.reservationStatus.htmlClass}"
                    th:text="#{${reservation.reservationStatus.resourceName}}"></td>
                <td>
                    <label class="reservation-paid" th:if="${reservation.paid}" th:text="#{reservation.paid}"></label>
                    <label class="reservation-expired" th:if="${reservation.expired and !reservation.paid}" th:text="#{reservation.status.expired}"></label>
                    <label class="reservation-unpaid" th:if="${!reservation.expired and !reservation.paid and reservation.daysUntilExpiration!=null}"
                           th:text="#{reservation.unpaid1}+${reservation.daysUntilExpiration}+' '+#{reservation.unpaid2}"></label>
                    <label class="reservation-unpaid" th:if="${!reservation.expired and !reservation.paid and reservation.daysUntilExpiration==null}"
                           th:text="#{reservation.unpaid}"></label>
                </td>
            </tr>
        </table>
    </div>

</body>
</html>