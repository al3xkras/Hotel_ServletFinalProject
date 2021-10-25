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
    <h1 style="text-align: center; margin-top: 5%; margin-bottom: 30px" th:text="#{new_apartment.header}"></h1>

    <div class="d-flex justify-content-center">

        <form class="form" action="#" th:action="@{/add_apartment}"
              th:object="${apartment}" method="post" autocomplete="off">

            <div class="form-group">
                <label for="name" th:text="#{new_apartment.name}"></label>
                <input type="text" id="name"  minlength="3" maxlength="25"
                       th:field="*{name}" required>
            </div>

            <div class="form-group">
                <label for="places" th:text="#{reservation.form.places}"></label>
                <input type="number" min="1" max="5" id="places" th:field="*{places}" required>
            </div>

            <div class="form-group">
                <label for="apartment-class" th:text="#{new_apartment.class}"></label>
                <select id="apartment-class" th:field="*{apartmentClass}" required>
                    <option th:each="class : ${T(ua.alexkras.hotel.model.ApartmentClass).values()}"
                            th:value="${class.name()}"
                            th:text="#{${class.resName}}">
                    </option>
                </select>
            </div>

            <div class="form-group">
                <label for="apartment-status" th:text="#{new_apartment.status}"></label>
                <select id="apartment-status" th:field="*{status}" required>
                    <option th:each="status : ${T(ua.alexkras.hotel.model.ApartmentStatus).values()}"
                            th:value="${status.name()}"
                            th:text="#{${status.resName}}">
                    </option>
                </select>
            </div>


            <div class="form-group">
                <label for="price" th:text="#{new_apartment.price}"></label>
                <input type="number" min="0" id="price" th:field="*{price}" required>
            </div>

            <button type="submit" class="btn btn-lg btn-primary btn-block" th:text="#{new_apartment.add}"></button>
        </form>
    </div>
</body>
</html>