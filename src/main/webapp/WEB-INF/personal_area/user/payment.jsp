<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Make payment</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.bundle.min.js">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js">
</head>
<body>
    <div class="container py-5">
        <div class="row mb-4">
            <div class="col-lg-8 mx-auto text-center">
                <h1 class="display-6" th:text="#{payment.header}"></h1>
            </div>
        </div>



        <div class="row">
            <div class="col-lg-6 mx-auto">
                <div class="card ">
                    <div class="card-header">
                        <div class="tab-content">
                            <div id="credit-card" class="tab-pane fade show active pt-3">
                                <form action="#" th:action="@{'/user/reservation/'+${payment.reservationId}+'/make_payment'}" th:object="${payment}" method="post">

                                    <div class="form-group">
                                        <label for="cardNumber">
                                            <h6 th:text="#{payment.card.number}"></h6>
                                        </label>

                                        <div class="input-group">
                                            <input type="text" id="cardNumber"
                                                   name="cardNumber"
                                                   th:placeholder="#{payment.card.number.placeholder}"
                                                   th:field="*{cardNumber}"
                                                   class="form-control " required>

                                            <div class="input-group-append">
                                                <span class="input-group-text text-muted">
                                                    <i class="fab fa-cc-visa mx-1"></i>
                                                    <i class="fab fa-cc-mastercard mx-1"></i>
                                                    <i class="fab fa-cc-amex mx-1"></i>
                                                </span>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col">
                                            <label>
                                                <span class="hidden-xs">
                                                    <h6 th:text="#{payment.expiration_date}"></h6>
                                                </span>
                                            </label>
                                            <div class="input-group">
                                                <input id="expirationDate" type="date" th:field="*{cardExpirationDate}" required>
                                            </div>
                                        </div>

                                        <div class="col-sm-4">
                                                <label>
                                                <span class="hidden-xs">
                                                    <h6>CVV</h6>
                                                </span>
                                                </label>
                                                <div class="input-group">
                                                    <input id="cardCvv" type="text" minlength="3" maxlength="3"
                                                           style="width: 30%; min-width: 50px"
                                                           th:field="*{cardCvv}" placeholder="CVV" required><br/>
                                                </div>

                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col">

                                        </div>
                                        <div class="col-sm-4">
                                            <div th:if="${invalidCvv!=null and invalidCvv}"
                                                 class="alert alert-danger" role="alert" style="padding: 3px; margin-top: 1px" th:text="#{payment.invalid_cvv}"></div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <label th:text="#{table.user.id}+': '+${payment.userId}"></label>
                                    </div>

                                    <div class="row">
                                        <label th:text="#{reservation.id}+': '+${payment.reservationId}"></label>
                                    </div>

                                    <div class="row">
                                        <label th:text="#{new_apartment.price}+': '+${payment.value}"></label>
                                    </div>

                                    <div class="card-footer">
                                        <button type="submit" class="subscribe btn btn-primary btn-block shadow-sm" th:text="#{payment.confirm}"></button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript" th:src="@{/js/payment.js}"></script>

</body>

</html>