package ua.alexkras.hotel.model.mysql;

import static ua.alexkras.hotel.model.mysql.MySqlStrings.databaseName;

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
            databaseName+"."+tablePayments+" ("+
            colPaymentId+" int unique primary key auto_increment, "+
            colUserId+" int not null,"+
            colReservationId+" int not null,"+
            colValue+" int not null,"+
            colPaymentDate+" DATETIME not null,"+
            colCardNumber+" varchar(40) not null,"+
            colCardExpirationDate+" DATE not null,"+
            colCardCvv+" varchar(3) not null);";

    String truncatePaymentsTable = "TRUNCATE "+databaseName+'.'+tablePayments;

    String createPayment = "INSERT INTO "+databaseName+'.'+tablePayments+" ("+
            colPaymentId+','+
            colUserId+','+
            colReservationId+','+
            colValue+','+
            colPaymentDate+','+
            colCardNumber+','+
            colCardExpirationDate+','+
            colCardCvv+
            ") VALUES (null,?,?,?,?,?,?,?);";

    String findPaymentById = "SELECT " +
            colUserId+','+
            colReservationId+','+
            colValue+','+
            colPaymentDate+','+
            colCardNumber+','+
            colCardExpirationDate+','+
            colCardCvv+
            " FROM "+databaseName+'.'+tablePayments +
            " WHERE "+colPaymentId+"=?";

    String findAllPayments="SELECT " +
            colPaymentId+','+
            colUserId+','+
            colReservationId+','+
            colValue+','+
            colPaymentDate+','+
            colCardNumber+','+
            colCardExpirationDate+','+
            colCardCvv+','+
            " FROM "+databaseName+'.'+tablePayments+" limit ?,?";

    String updatePayment="UPDATE "+databaseName+'.'+tablePayments+" SET " +
            colUserId+"=?,"+
            colReservationId+"=?,"+
            colValue+"=?,"+
            colPaymentDate+"=?,"+
            colCardNumber+"=?,"+
            colCardExpirationDate+"=?,"+
            colCardCvv+"=? "+
            " WHERE "+colPaymentId+"=?";

    String deletePayment="DELETE FROM "+databaseName+'.'+tablePayments+
            " WHERE "+colPaymentId+"=?";


}
