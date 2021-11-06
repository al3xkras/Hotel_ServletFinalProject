<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">
<head>
    <meta charset="UTF-8">
    <title>Admin</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_navbar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_reservation_status.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_personal_area.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_apartment.css" />

    <style>
        span.border{
            margin: 2px;
        }

        span{
            padding: 5px;
        }

        div.d-flex{
            margin-top: 3px;
            margin-bottom: 16px;
        }
    </style>
</head>
<body>

    <c:set var="page">
        ${param.page==null?1:param.page}
    </c:set>

    <div class="custom-navbar" style="">
        <a class="active" href="${pageContext.request.contextPath}/app/admin">
            <fmt:message key="navbar.hotel"/>
        </a>

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
                <fmt:message key="navbar.admin"/>
                <i class="fa fa-caret-down"></i>
            </button>
            <div class="custom-dropdown-content" style="left: 85%; width: 14.5%">
                <a href="${pageContext.request.contextPath}/app/admin/add_apartment"><fmt:message key="new_apartment.header"/></a>
                <a href="${pageContext.request.contextPath}/app/apartments"><fmt:message key="navbar.all_apartments"/></a>
                <a href="${pageContext.request.contextPath}/app/personal_data"><fmt:message key="navbar.personal_data"/></a>
            </div>
        </div>

        <a href="${pageContext.request.contextPath}/app/logout" style="float:right;">
            <fmt:message key="navbar.logout"/>
        </a>

    </div>

    <div style="flex: available; justify-content: center; margin: 5% 5% 5% 5%;">

        <h2 style="text-align: center"><fmt:message key="navbar.pending_reservations"/></h2>

        <table class="reservations">
            <tr class="align-middle">
                <th><fmt:message key="table.id"/></th>
                <th><fmt:message key="table.user.id"/></th>
                <th><fmt:message key="new_apartment.places"/></th>
                <th><fmt:message key="reservation.form.from_date"/></th>
                <th><fmt:message key="reservation.form.to_date"/></th>
                <th><fmt:message key="reservation.submit_date"/></th>
                <th><fmt:message key="new_apartment.class"/></th>
                <th><fmt:message key="apartment.id"/></th>
                <th><fmt:message key="new_apartment.price"/></th>
                <th><fmt:message key="reservation.status"/></th>
                <th><fmt:message key="reservation.payment_status"/></th>
            </tr>

            <c:forEach items="${requestScope.pendingReservations}" var="reservation">
                <tr class="align-middle"
                    onclick="window.location='${pageContext.request.contextPath}/app/admin/reservation/${reservation.id}';">
                    <td>${reservation.id}</td>
                    <td>${reservation.userId}</td>
                    <td>${reservation.places}</td>
                    <td>${reservation.fromDate.toString()}</td>
                    <td>${reservation.toDate.toString()}</td>
                    <td>${reservation.submitDate.toString().replace("T"," ")}</td>

                    <td class="${reservation.apartmentClass.htmlClass}">
                        <fmt:message key="${reservation.apartmentClass.resName}"/>
                    </td>

                    <td class="${reservation.apartmentId==null?'red':'green'}">${reservation.apartmentId==null?'-':reservation.apartmentId}</td>
                    <td class="${reservation.apartmentPrice==null?'red':'green'}">${reservation.apartmentPrice==null?'-':reservation.apartmentPrice}</td>
                    <td class="${reservation.reservationStatus.htmlClass}"><fmt:message key="${reservation.reservationStatus.resourceName}"/></td>

                    <c:if test="${reservation.paid}">
                        <td class="reservation-paid"><fmt:message key="reservation.paid"/></td>
                    </c:if>
                    <c:if test="${reservation.expired and !reservation.paid}">
                        <td class="reservation-expired"><fmt:message key="reservation.status.expired"/></td>
                    </c:if>
                    <c:if test="${!reservation.expired and !reservation.paid and reservation.daysUntilExpiration!=null}">
                        <td class="reservation-unpaid"><fmt:message key="reservation.unpaid1"/> ${reservation.daysUntilExpiration} <fmt:message key="reservation.unpaid2"/></td>
                    </c:if>
                    <c:if test="${!reservation.expired and !reservation.paid and reservation.daysUntilExpiration==null}">
                        <td class="reservation-unpaid"><fmt:message key="reservation.unpaid"/></td>
                    </c:if>

                </tr>
            </c:forEach>
        </table>
    </div>

    <c:if test="${requestScope.pageable.totalPages>1}">
        <div class="d-flex flex-row justify-content-center">
            <c:if test="${requestScope.pageable.hasPrevious()}">
            <span class="border">
                <a href="${pageContext.request.contextPath}/app/admin?page=${page-1}">Previous</a>
            </span>
            </c:if>

            <c:forEach begin="1" end="${requestScope.pageable.totalPages}" var="pageIndex">
                <c:if test="${page==pageIndex}">
                    <span class="border selected">${pageIndex}</span>
                </c:if>
                <c:if test="${!(page==pageIndex)}">
                <span class="border">
                     <a class="page-number" href="${pageContext.request.contextPath}/app/admin?page=${pageIndex}">${pageIndex}</a>
                </span>
                </c:if>
            </c:forEach>

            <c:if test="${requestScope.pageable.hasNext()}">
            <span class="border">
                <a href="${pageContext.request.contextPath}/app/admin?page=${page+1}">Next</a>
            </span>
            </c:if>
        </div>
    </c:if>
</body>
</html>