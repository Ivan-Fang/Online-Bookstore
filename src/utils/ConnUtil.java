package utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnUtil {
    static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
    static Properties properties = new Properties();   // for druid connection pool

    static {
        try {
            InputStream inputStream = ConnUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection createConn() {
        try {
            // use druid connection pool instead of basic jdbc connection
            DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            return dataSource.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConn() {
        Connection conn = threadLocal.get();
        if (conn == null) {
            conn = createConn();
            threadLocal.set(conn);
        }
        return conn;
    }

    public static void closeConn() throws SQLException {
        Connection conn = threadLocal.get();
        if (conn == null) {
            return;
        }
        if (!conn.isClosed()) {
            conn.close();
            threadLocal.set(null);
        }
    }
}
