<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">
<head>
    <meta charset="UTF-8">
    <title>Apartments</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_navbar.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_table.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_apartment.css"/>

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
    <div class="custom-navbar">
        <a class="active" href="${pageContext.request.contextPath}/app/${sessionScope.user.userType.name().toLowerCase()}"><fmt:message key="navbar.home"/></a>


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
                <fmt:message key='${sessionScope.user.userType.name().equalsIgnoreCase("user")?"navbar.user":"navbar.admin"}'/>
                <i class="fa fa-caret-down"></i>
            </button>
            <div class="custom-dropdown-content" style="left: 80%; width: 19.5%">
                <a href="${pageContext.request.contextPath}/app/personal_data"><fmt:message key="navbar.personal_data"/></a>
            </div>
        </div>
    </div>

    <h1 style="text-align: center"><fmt:message key="apartments.header"/></h1>

    <label for="sort_by" style="margin-left: 16px"><fmt:message key="apartments.sort_by"/></label>
    <select id="sort_by" onchange="onSortChange(event)">
        <option id="price" value="price"><fmt:message key="apartments.sort_by.price"/></option>
        <option id="places" value="places"><fmt:message key="apartments.sort_by.places"/></option>
        <option id="class" value="apartmentClass"><fmt:message key="apartments.sort_by.class"/></option>
        <option id="status" value="status"><fmt:message key="apartments.sort_by.status"/></option>
    </select>

    <div class="d-flex justify-content-center">
        <table>
            <tr>
                <th><fmt:message key="table.id"/></th>
                <th><fmt:message key="new_apartment.name"/></th>
                <th><fmt:message key="new_apartment.places"/></th>
                <th><fmt:message key="new_apartment.price"/></th>
                <th><fmt:message key="new_apartment.class"/></th>
                <th><fmt:message key="new_apartment.status"/></th>
            </tr>

            <c:forEach items="${requestScope.apartments}" var="apartment">
                <tr class="align-middle"
                    onclick="window.location='${pageContext.request.contextPath}/app/apartments/apartment/'+${apartment.id};">
                    <td class="align-middle">${apartment.id}</td>
                    <td class="align-middle">${apartment.name}</td>
                    <td class="align-middle">${apartment.places}</td>
                    <td class="align-middle">${apartment.price}</td>
                    <td class="${apartment.apartmentClass.htmlClass}"><fmt:message key="${apartment.apartmentClass.resName}"/></td>
                    <td class="${apartment.status.htmlClass}"><fmt:message key="${apartment.status.resName}"/></td>
                </tr>
            </c:forEach>

        </table>
    </div>

    <!--
    <div class="d-flex flex-row justify-content-center" hidden>
        <span class="border" th:if="$/{allApartments.hasPrevious()}">
            <a th:href="@{/apartments(page=$/{allApartments.number-1})}">Previous</a>
        </span>
        <th://block th:each="i: $/{#numbers.sequence(0, allApartments.totalPages - 1)}">
            <span class="border selected" th:if="$/{allApartments.number == i}">[[$/{i}+1]]</span>
            <span class="border"  th:unless="$/{allApartments.number == i}">
                <a class="page-number" th:href="@{/apartments(page=$/{i})}">[[$/{i}+1]]</a>
            </span>
        </th://block>

        <span class="border" th:if="$/{allApartments.hasNext()}">
            <a th:href="@{/apartments(page=$//={allApartments.number+1})}">Next</a>
        </span>
    </div>
    -->


    <script>
        const url = new URL(window.location);
        const sort = url.searchParams.get("sort");

        if (sort!=null){
            document.getElementById('sort_by').value = sort;
        }

        function onSortChange(event) {
            const newLocation="/apartments?sort=";
            window.location=newLocation+event.target.value;
        }
    </script>
</body>
</html>