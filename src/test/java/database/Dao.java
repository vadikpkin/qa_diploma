package database;

import java.sql.*;
import java.util.ArrayList;

public class Dao {
    public static String getLastStatusNotCredit() throws SQLException {
        try (Connection cn = ConnectionFactory.getConnection();
             Statement st = cn.createStatement()) {
            try (ResultSet rs = st.executeQuery("select status from payment_entity order by created;")) {
                ArrayList<String> codes = new ArrayList<>();
                while (rs.next()) {
                    codes.add(rs.getString("status"));
                }

                if (codes.isEmpty()) return "table is empty";

                return codes.get(codes.size() - 1);
            }
        }
    }

    public static String getLastStatusCredit() throws SQLException {
        try (Connection cn = ConnectionFactory.getConnection();
             Statement st = cn.createStatement()) {
            try (ResultSet rs = st.executeQuery("select status from credit_request_entity order by created;")) {
                ArrayList<String> codes = new ArrayList<>();
                while (rs.next()) {
                    codes.add(rs.getString("status"));
                }

                if (codes.isEmpty()) return "table is empty";

                return codes.get(codes.size() - 1);
            }
        }
    }

    public static String getLastTransactionId() throws SQLException {
        try (Connection cn = ConnectionFactory.getConnection();
             Statement st = cn.createStatement()) {
            try (ResultSet rs = st.executeQuery("select transaction_id from payment_entity order by created;")) {
                ArrayList<String> codes = new ArrayList<>();
                while (rs.next()) {
                    codes.add(rs.getString("transaction_id"));
                }

                if (codes.isEmpty()) return "table is empty";

                return codes.get(codes.size() - 1);
            }
        }
    }

    public static String getLastBankId() throws SQLException {
        try (Connection cn = ConnectionFactory.getConnection();
             Statement st = cn.createStatement()) {
            try (ResultSet rs = st.executeQuery("select bank_id from credit_request_entity order by created;")) {
                ArrayList<String> codes = new ArrayList<>();
                while (rs.next()) {
                    codes.add(rs.getString("bank_id"));
                }

                if (codes.isEmpty()) return "table is empty";

                return codes.get(codes.size() - 1);
            }
        }
    }

    public static String getLastOrderId() throws SQLException {
        try (Connection cn = ConnectionFactory.getConnection();
             Statement st = cn.createStatement()) {
            try (ResultSet rs = st.executeQuery("select payment_id from order_entity order by created;")) {
                ArrayList<String> codes = new ArrayList<>();
                while (rs.next()) {
                    codes.add(rs.getString("payment_id"));
                }

                if (codes.isEmpty()) return "table is empty";

                return codes.get(codes.size() - 1);
            }
        }
    }

    public static void clearOrderEntityTable() throws SQLException {
        try (Connection cn = ConnectionFactory.getConnection();
             Statement st = cn.createStatement()) {
            st.executeUpdate("delete from order_entity;");
        }
    }

    public static void clearCreditRequestEntityTable() throws SQLException {
        try (Connection cn = ConnectionFactory.getConnection();
             Statement st = cn.createStatement()) {
            st.executeUpdate("delete from credit_request_entity;");
        }
    }

    public static void clearPaymentEntityTable() throws SQLException {
        try (Connection cn = ConnectionFactory.getConnection();
             Statement st = cn.createStatement()) {
            st.executeUpdate("delete from payment_entity;");
        }
    }

    public static void clearAllTables() throws SQLException {
        clearPaymentEntityTable();
        clearCreditRequestEntityTable();
        clearOrderEntityTable();
    }

}
