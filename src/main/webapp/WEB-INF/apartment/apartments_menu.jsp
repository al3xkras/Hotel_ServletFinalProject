<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Apartments</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">
    <link rel="stylesheet" th:href="@{/css/style_navbar.css}" />
    <link rel="stylesheet" th:href="@{/css/style_table.css}" />
    <link rel="stylesheet" th:href="@{/css/style_apartment.css}"/>

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
        <a class="active" href="/" th:text="#{navbar.home}"></a>

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
            <button class="dropdown-btn" th:text="#{${userAccount!=null and userAccount?'navbar.user':'navbar.admin'}}">
                <i class="fa fa-caret-down"></i>
            </button>
            <div class="custom-dropdown-content" style="left: 80%; width: 19.5%">
                <a href="/personal_data" th:text="#{navbar.personal_data}"></a>
            </div>
        </div>
    </div>

    <h1 style="text-align: center" th:text="#{apartments.header}"></h1>

    <label for="sort_by" th:text="#{apartments.sort_by}"
        style="margin-left: 16px"></label>
    <select id="sort_by" onchange="onSortChange(event)">
        <option th:text="#{apartments.sort_by.price}"
            id="price" value="price"></option>
        <option th:text="#{apartments.sort_by.places}"
                id="places" value="places"></option>
        <option th:text="#{apartments.sort_by.class}"
                id="class" value="apartmentClass"></option>
        <option th:text="#{apartments.sort_by.status}"
                id="status" value="status"></option>
    </select>

    <div class="d-flex justify-content-center">
        <table>
            <tr>
                <th th:text="#{table.id}"></th>
                <th th:text="#{new_apartment.name}"></th>
                <th th:text="#{new_apartment.places}"></th>
                <th th:text="#{new_apartment.price}"></th>
                <th th:text="#{new_apartment.class}"></th>
                <th th:text="#{new_apartment.status}"></th>
            </tr>

            <tr class="align-middle"
                th:each="apartment : ${allApartments}"
                th:onclick="'window.location=\'/apartment/'+${apartment.id}+'\';'">
                <td class="align-middle"  th:text="${apartment.id}"></td>
                <td class="align-middle" th:text="${apartment.name}"></td>
                <td class="align-middle" th:text="${apartment.places}"></td>
                <td class="align-middle" th:text="${apartment.price}"></td>
                <td th:class="${apartment.apartmentClass.htmlClass}"
                    th:text="#{${apartment.apartmentClass.resName}}"></td>
                <td th:class="${apartment.status.htmlClass}"
                    th:text="#{${apartment.status.resName}}"></td>
            </tr>
        </table>
    </div>

    <div class="d-flex flex-row justify-content-center">
        <span class="border" th:if="${allApartments.hasPrevious()}">
            <a th:href="@{/apartments(page=${allApartments.number-1})}">Previous</a>
        </span>
        <th:block th:each="i: ${#numbers.sequence(0, allApartments.totalPages - 1)}">
            <span class="border selected" th:if="${allApartments.number == i}">[[${i}+1]]</span>
            <span class="border"  th:unless="${allApartments.number == i}">
                <a class="page-number" th:href="@{/apartments(page=${i})}">[[${i}+1]]</a>
            </span>
        </th:block>

        <span class="border" th:if="${allApartments.hasNext()}">
            <a th:href="@{/apartments(page=${allApartments.number+1})}">Next</a>
        </span>
    </div>

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