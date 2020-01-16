package database;

import data.Status;
import java.sql.*;

public class Dao {
    private Dao() {
    }

    public static void clearAllTables(){
        try (Connection cn = ConnectionFactory.getConnection();
             Statement st1 = cn.createStatement();
             Statement st2 = cn.createStatement();
             Statement st3 = cn.createStatement();) {
             st1.executeUpdate("delete from payment_entity;");
             st2.executeUpdate("delete from credit_request_entity;");
             st3.executeUpdate("delete from order_entity;");
        } catch (SQLException e){
            throw new RuntimeException("failed to co execute statement");
        }
    }

    public static boolean checkPayment(Status status, String tableName, String columnName) {
        try(Connection cn = ConnectionFactory.getConnection();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM order_entity INNER JOIN " + tableName)
        ) {
            int i = 0;
            while (rs.next()){
                i++;
            }
            rs.beforeFirst();
            rs.next();
            return rs.getString(columnName).equals(rs.getString("payment_id")) &
                rs.getString("status").equals(status.toString()) & i==1;
        }catch (SQLException e){
            throw new RuntimeException("failed to execute query");
        }
    }

    public static boolean acceptApprovedPayment(){
        return checkPayment(Status.APPROVED,"payment_entity", "transaction_id");
    }

    public static boolean acceptDeclinedPayment(){
        return checkPayment(Status.DECLINED, "payment_entity", "transaction_id");
    }

    public static boolean acceptDeclinedCreditPayment(){
        return checkPayment(Status.DECLINED, "credit_request_entity", "bank_id");
    }

    public static boolean acceptApprovedCreditPayment(){
        return checkPayment(Status.APPROVED, "credit_request_entity", "bank_id");
    }

}
