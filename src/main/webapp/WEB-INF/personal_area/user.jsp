<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<head>
    <meta charset="UTF-8">
    <title>Personal area</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">
    <link rel="stylesheet" th:href="@{/css/style_navbar.css}" />
    <link rel="stylesheet" th:href="@{/css/style_reservation_status.css}" />
    <link rel="stylesheet" th:href="@{/css/style_personal_area.css}" />
    <link rel="stylesheet" th:href="@{/css/style_apartment.css}" />

</head>
<body style="background: #f6f6f6;">
    <div class="custom-navbar">
        <a class="active" href="/" th:text="#{navbar.hotel}"></a>
        <div class="custom-navbar-dropdown" style="float: left">
            <button class="dropdown-btn" th:text="#{navbar.language}">
                <i class="fa fa-caret-down"></i>
            </button>
            <div class="custom-dropdown-content">
                <a href="?lang=ua" th:text="#{navbar.language.ua}"></a>
                <a href="?lang=en" th:text="#{navbar.language.en}"></a>
            </div>
        </div>

        <div class="custom-navbar-dropdown" style="float: right">
            <button class="dropdown-btn" th:text="#{navbar.user}">
                <i class="fa fa-caret-down"></i>
            </button>
            <div class="custom-dropdown-content" style="left: 80%; width: 19.5%">
                <a href="/create_reservation" th:text="#{navbar.new_reservation}"></a>
                <a href="/apartments" th:text="#{navbar.all_apartments}"></a>
                <a href="/personal_data" th:text="#{navbar.personal_data}"></a>
            </div>
        </div>

        <form class="custom-navbar-dropdown" action="/auth/logout" method="post" style="float: right">
            <button class="dropdown-btn" type="submit" th:text="#{navbar.logout}"></button>
        </form>
    </div>

    <div class="reservations-list">
        <h2 style="text-align: center" th:text="#{navbar.my_reservations}"></h2>
        <table class="reservations">
            <tr class="align-middle">
                <th th:text="#{reservation.submit_date}"></th>
                <th th:text="#{new_apartment.places}"></th>
                <th th:text="#{reservation.form.from_date}"></th>
                <th th:text="#{reservation.form.to_date}"></th>
                <th th:text="#{new_apartment.class}"></th>
                <th th:text="#{apartment.id}"></th>
                <th th:text="#{new_apartment.price}"></th>
                <th th:text="#{reservation.status}"></th>
                <th th:text="#{reservation.payment_status}"></th>
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