<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title th:text="'Apartment: '+${apartment.name}"></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" crossorigin="anonymous">
    <link rel="stylesheet" th:href="@{/css/style_navbar.css}" />
    <link rel="stylesheet" th:href="@{/css/style_apartment.css}"/>

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

        dialog form{
            margin-top: 30px;
        }

        dialog div.form-body{
            margin-top: 30px;
        }
    </style>
</head>
<body>
    <div class="custom-navbar">
        <a class="active" href="/apartments" th:text="#{navbar.all_apartments}"></a>

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

    <div style="margin-bottom: 100px">
        <h1 style="text-align: center; margin-top: 5%; margin-bottom: 30px" th:text="${apartment.name}"></h1>

        <div class="d-flex justify-content-left" style="margin: 5px; min-height: 40%;">
            <img src="data:image/jpeg;base64,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"
                 alt="Apartment 1" style="min-width: 30%; max-width: 60%">

            <div class="d-flex flex-column" style="margin: 5px; min-height: 40%;">
                <div>
                    <label th:text="#{new_apartment.class}+':'"></label><label th:text="#{${apartment.apartmentClass.resName}}" th:class="${apartment.apartmentClass.htmlClass}"></label><br/>
                </div>
                <div>
                    <label th:text="#{new_apartment.status}+':'"></label><label th:text="#{${apartment.status.resName}}" th:class="${apartment.status.htmlClass}"></label><br/>
                </div>

                <label th:text="#{new_apartment.places}+': '+${apartment.places}"></label><br/>
                <label th:text="#{new_apartment.price}+': '+${apartment.price}"></label><br/>
                <label th:if="${!apartment.status.name().equalsIgnoreCase('available')}"
                    style="font-size: 110%; color: red" th:text="#{message.apartment.unavailable}"></label><br/>

                <div class="d-flex flex-row-reverse" th:if="${apartment.status.name().equalsIgnoreCase('available')}">
                    <button type="button"
                            class="btn btn-primary"
                            style="margin-right: 24px; margin-top: 8px"
                            th:text="#{apartment.create_reservation}"
                            id="create_reservation"></button>
                </div>
            </div>

        </div>

        <dialog id="confirmation_dialog">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Confirmation</h5>
            </div>

            <div class="form-body">
                <p>Create reservation for this apartment?</p>
            </div>

            <form action="#" th:action="@{'/apartment/'+${apartment.id}}"
                  th:object="${reservation}" method="post">

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

                <button id="cancel" type="button" class="btn btn-primary">Cancel</button>
                <button type="submit" class="btn btn-secondary" style="float: right">Confirm</button>
            </form>
        </dialog>

    </div>

    <script th:inline="javascript">
        /*<![CDATA[*/
        const fromDateIsGreaterThanToDate = /*[[${fromDateIsGreaterThanToDate}]]*/ false;
        /*]]>*/

        const updateButton = document.getElementById('create_reservation');
        const cancelButton = document.getElementById('cancel');
        const confirmationDialog = document.getElementById('confirmation_dialog');

        if (fromDateIsGreaterThanToDate) confirmationDialog.showModal();

        // Update button opens a modal dialog
        updateButton.addEventListener('click', function() {
            confirmationDialog.showModal();
        });

        // Form cancel button closes the dialog box
        cancelButton.addEventListener('click', function() {
            confirmationDialog.close();
        });


        let today = new Date();
        let dd = today.getDate();
        let mm = today.getMonth() + 1; //January is 0!
        const yyyy = today.getFullYear();
        let hh = today.getHours();
        let minutes = today.getMinutes();

        if (dd < 10) {
            dd = '0' + dd;
        }

        if (mm < 10) {
            mm = '0' + mm;
        }

        if (hh < 10){
            hh = '0'+ hh;
        }

        if (minutes < 10){
            minutes = '0'+minutes;
        }

        today = yyyy + '-' + mm + '-' + dd + 'T' + hh + ':' + minutes;
        const max_date = (yyyy + 5) + '-' + mm + '-' + dd + 'T00:00' + hh + ':' + minutes;

        document.getElementById("from_date").min = today;
        document.getElementById("from_date").max = max_date;
        document.getElementById("to_date").min = today;
        document.getElementById("to_date").max = max_date;
    </script>


</body>
</html>