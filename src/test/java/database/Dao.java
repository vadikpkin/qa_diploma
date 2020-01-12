package database;

import java.sql.*;
import java.util.ArrayList;

public class Dao {

    public static void clearTable(String tableName) throws SQLException {
        try (Connection cn = ConnectionFactory.getConnection();
             Statement st = cn.createStatement()) {
            st.executeUpdate("delete from " + tableName + ";");
        }
    }

    private static String getLast(String tableName, String columnName) throws SQLException {
        try (Connection cn = ConnectionFactory.getConnection();
             Statement st = cn.createStatement()) {
            try (ResultSet rs = st.executeQuery("select " + columnName + " from " + tableName + " order by created;")) {
                ArrayList<String> codes = new ArrayList<>();
                while (rs.next()) {
                    codes.add(rs.getString(columnName));
                }

                if (codes.isEmpty()) return "table is empty";

                return codes.get(codes.size() - 1);
            }
        }
    }

    public static String getLastPaymentStatus() throws SQLException {
        return getLast("payment_entity", "status");
    }

    public static String getLastCreditPaymentStatus() throws SQLException {
        return getLast("credit_request_entity", "status");
    }

    public static String getLastTransactionId() throws SQLException {
        return getLast("payment_entity", "transaction_id");
    }

    public static String getLastBankId() throws SQLException {
        return getLast("credit_request_entity", "bank_id");
    }

    public static String getLastOrderId() throws SQLException {
        return getLast("order_entity", "payment_id");
    }

    public static void clearOrderEntityTable() throws SQLException {
        clearTable("order_entity");
    }

    public static void clearCreditRequestEntityTable() throws SQLException {
        clearTable("credit_request_entity");
    }

    public static void clearPaymentEntityTable() throws SQLException {
        clearTable("payment_entity");
    }

    public static void clearAllTables() throws SQLException {
        clearPaymentEntityTable();
        clearCreditRequestEntityTable();
        clearOrderEntityTable();
    }

}
