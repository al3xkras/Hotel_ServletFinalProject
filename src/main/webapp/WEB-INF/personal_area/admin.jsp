<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<head>
    <meta charset="UTF-8">
    <title>Admin</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">
    <link rel="stylesheet" th:href="@{/css/style_navbar.css}">
    <link rel="stylesheet" th:href="@{/css/style_reservation_status.css}" />
    <link rel="stylesheet" th:href="@{/css/style_personal_area.css}" />
    <link rel="stylesheet" th:href="@{/css/style_apartment.css}" />

</head>
<body>
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
            <button class="dropdown-btn" th:text="#{navbar.admin}">
                <i class="fa fa-caret-down"></i>
            </button>
            <div class="custom-dropdown-content" style="left: 85%; width: 14.5%">
                <a href="/add_apartment" th:text="#{new_apartment.header}"></a>
                <a href="/apartments" th:text="#{navbar.all_apartments}"></a>
                <a href="/personal_data" th:text="#{navbar.personal_data}"></a>
            </div>
        </div>

        <form class="custom-navbar-dropdown" action="/auth/logout" method="post" style="float: right">
            <button class="dropdown-btn" type="submit" th:text="#{navbar.logout}"></button>
        </form>
    </div>

    <div style="flex: available; justify-content: center; margin: 5% 5% 5% 5%;">

        <h2 th:text="#{navbar.pending_reservations}" style="text-align: center"></h2>

        <table class="reservations">
            <tr class="align-middle">
                <th th:text="#{table.id}"></th>
                <th th:text="#{table.user.id}"></th>
                <th th:text="#{new_apartment.places}"></th>
                <th th:text="#{reservation.form.from_date}"></th>
                <th th:text="#{reservation.form.to_date}"></th>
                <th th:text="#{reservation.submit_date}"></th>
                <th th:text="#{new_apartment.class}"></th>
                <th th:text="#{apartment.id}"></th>
                <th th:text="#{new_apartment.price}"></th>
                <th th:text="#{reservation.status}"></th>
                <th th:text="#{reservation.payment_status}"></th>
            </tr>

            <tr class="align-middle"
                th:each="reservation : ${pendingReservations}"
                th:onclick="'window.location=window.location+\'/reservation/'+${reservation.id}+'\';'">
                <td class="align-middle" th:text="${reservation.id}"></td>
                <td class="align-middle" th:text="${reservation.userId}"></td>
                <td class="align-middle" th:text="${reservation.places}"></td>
                <td class="align-middle"
                    th:text='${reservation.fromDate.toString().replace("T"," time: ")}'></td>
                <td class="align-middle"
                    th:text='${reservation.toDate.toString().replace("T"," time: ")}'></td>
                <td class="align-middle"
                    th:text='${reservation.submitDate.toString().replace("T"," time: ")}'></td>
                <td th:class="${reservation.apartmentClass.htmlClass}"
                    th:text="#{${reservation.apartmentClass.resName}}"></td>
                <td th:class="${reservation.apartmentId==null?'red':'green'}"
                    th:text="${reservation.apartmentId==null?'indefined by admin':reservation.apartmentId}"></td>
                <td th:class="${reservation.apartmentPrice==null?'red':'green'}"
                    th:text="${reservation.apartmentPrice==null?'indefined':reservation.apartmentPrice}"></td>
                <td th:class="${reservation.reservationStatus.htmlClass}"
                    th:text="#{${reservation.reservationStatus.resourceName}}"></td>
                <td th:text="#{${reservation.paid?'reservation.paid':'reservation.unpaid'}}"
                    th:class="${reservation.paid?'reservation-paid':'reservation-unpaid'}"></td>
            </tr>
        </table>
    </div>
</body>
</html>