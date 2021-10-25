<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
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

    <h1 style="text-align: center; margin-top: 5%; margin-bottom: 30px" th:text="#{reservation.form.header}"></h1>

    <div class="d-flex justify-content-center">

        <form class="form" action="#" th:action="@{/create_reservation}" th:object="${reservationRequest}" method="post" autocomplete="off">


            <div class="form-group">
                <label for="apartment-class" th:text="#{reservation.form.apartment_class}">Apartment class</label>
                <select id="apartment-class" th:field="*{apartmentClass}" required>
                    <option th:each="class : ${T(ua.alexkras.hotel.model.ApartmentClass).values()}"
                            th:value="${class.name()}"
                            th:text="#{${class.resName}}">
                    </option>
                </select>
            </div>

            <div class="form-group">
                <label for="places" th:text="#{reservation.form.places}"></label>
                <input type="number" min="1" max="5" id="places" th:field="*{places}" required>
            </div>

            <div class="form-group">
                <label for="from_date" th:text="#{reservation.form.from_date}"></label>
                <input type="datetime-local" id="from_date" th:field="*{fromDate}" required>
            </div>

            <div class="form-group">
                <label for="to_date" th:text="#{reservation.form.to_date}"></label>
                <input type="datetime-local" id="to_date" th:field="*{toDate}" required>
                <div class="alert alert-warning" th:if="${fromDateIsGreaterThanToDate}"
                     th:text="#{reservation.alert.date}"></div>
            </div>

            <button type="submit" class="btn btn-lg btn-primary btn-block" th:text="#{form.submit}"></button>
        </form>
    </div>

    <script type="text/javascript" th:src="@{/js/reservation/create_reservation.js}"></script>
</body>
</html>