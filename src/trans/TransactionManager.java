package trans;

import utils.ConnUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    // begin the transaction
    public static void begin() throws SQLException {
        ConnUtil.getConn().setAutoCommit(false);
        System.out.println(">>> TransactionManager begins...");
    }

    // commit and close the transaction
    public static void commit() throws SQLException {
        Connection conn = ConnUtil.getConn();
        conn.commit();
        ConnUtil.closeConn();   // close conn and set threadLocal to null
        System.out.println(">>> TransactionManager commit...");
    }

    // rollback and close the transaction
    public static void rollback() throws SQLException {
        Connection conn = ConnUtil.getConn();
        conn.rollback();
        ConnUtil.closeConn();   // close conn and set threadLocal to null
        System.out.println(">>> TransactionManager rollback...");
    }
}
