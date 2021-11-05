package ua.alexkras.hotel.model.mysql;

public interface PaymentTableStrings {
    String tablePayments = "payments";

    String colPaymentId = "id";
    String colUserId="user_id";
    String colReservationId = "reservation_id";
    String colValue="value";
    String colPaymentDate="payment_date";
    String colCardNumber="card_number";
    String colCardExpirationDate="card_expiration_date";
    String colCardCvv="card_cvv";

    String createPaymentsTableIfNotExists = "CREATE TABLE IF NOT EXISTS "+
            MySqlStrings.databaseName+"."+tablePayments+" ("+
            colPaymentId+" int unique primary key auto_increment, "+
            colUserId+" int not null,"+
            colReservationId+" int not null,"+
            colValue+" int not null,"+
            colPaymentDate+" DATETIME not null,"+
            colCardNumber+" varchar(40) not null,"+
            colCardExpirationDate+" DATE not null,"+
            colCardCvv+" varchar(3) not null);";

}
