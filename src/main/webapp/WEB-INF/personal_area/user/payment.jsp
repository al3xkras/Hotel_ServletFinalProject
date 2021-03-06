<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">
<head>
    <meta charset="UTF-8">
    <title>Make payment</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
    <div class="container py-5">
        <div class="row mb-4">
            <div class="col-lg-8 mx-auto text-center">
                <h1 class="display-6">
                    <fmt:message key="payment.header"/>
                </h1>
            </div>
        </div>

        <%pageContext.setAttribute("reservation", request.getAttribute("reservation"));%>

        <div class="row">
            <div class="col-lg-6 mx-auto">
                <div class="card ">
                    <div class="card-header">
                        <div class="tab-content">
                            <div id="credit-card" class="tab-pane fade show active pt-3">
                                <form action="/app/user/make_payment/${reservation.id}" method="post">

                                    <div class="form-group">
                                        <label for="cardNumber">
                                            <h6><fmt:message key="payment.card.number"/></h6>
                                        </label>

                                        <div class="input-group">
                                            <input type="text" id="cardNumber"
                                                   name="cardNumber"
                                                   pattern="<fmt:message key="card_number.regexp"/>"
                                                   title="<fmt:message key="card_number.regexp"/>"
                                                   placeholder="<fmt:message key="payment.card.number.placeholder"/>"
                                                   class="form-control " required>

                                            <div class="input-group-append">
                                                <span class="input-group-text text-muted">
                                                </span>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col">
                                            <label>
                                                <span class="hidden-xs">
                                                    <h6><fmt:message key="payment.expiration_date"/></h6>
                                                </span>
                                            </label>
                                            <div class="input-group">
                                                <input id="expirationDate" name="expirationDate" type="date" required>
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
                                                           name="cardCvv" placeholder="CVV" required><br/>
                                                </div>

                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col">

                                        </div>
                                        <div class="col-sm-4">
                                            <c:if test="${requestScope.invalidCvv}">
                                                <div class="alert alert-danger" role="alert" style="padding: 3px; margin-top: 1px">
                                                    <fmt:message key="payment.invalid_cvv"/>
                                                </div>
                                            </c:if>
                                        </div>
                                    </div>

                                    <div style="margin-top: 24px; margin-bottom: 16px">
                                        <div class="row">
                                            <div class="col">
                                                <label><fmt:message key="table.user.id"/></label>
                                            </div>
                                            <div class="col-sm-4">
                                                <label>${reservation.userId}</label>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col">
                                                <label><fmt:message key="reservation.id"/></label>
                                            </div>
                                            <div class="col-sm-4">
                                                <label>${reservation.id}</label>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col">
                                                <label><fmt:message key="new_apartment.price"/></label>
                                            </div>
                                            <div class="col-sm-4">
                                                <label>${reservation.apartmentPrice}</label>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col">
                                                <label><fmt:message key="payment.price"/></label>
                                            </div>
                                            <div class="col-sm-4">
                                                <label>${requestScope.totalValue}</label>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">

                                    </div>

                                    <div class="row">

                                    </div>

                                    <div class="row">

                                    </div>

                                    <div class="row">

                                    </div>

                                    <div class="card-footer">
                                        <button type="submit" class="subscribe btn btn-primary btn-block shadow-sm"
                                            onclick="savePageVariables()">
                                            <fmt:message key="payment.confirm"/>
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/payment.js"></script>
</body>

</html>