package basedao;

import exception.BaseDAOException;
import utils.ConnUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDAO<T> {

    protected Connection conn;
    protected PreparedStatement psmt;
    protected ResultSet rs;

    // T 的 Class 物件
    private Class entityClass;

    public BaseDAO() {
        // getClass() 獲取 Class 物件，當前我們執行的是 new FruitDAOImpl()，創建的是FruitDAOImpl 的實例
        // 那麼子類構造方法內部首先會調用父類（BaseDAO）的無參構造方法
        // 因此此處的 getClass() 會被執行，但是 getClass 獲取的是 FruitDAOImpl 的 Class
        // 所以 getGenericSuperclass() 獲取到的是 BaseDAO 的 Class
        Type genericType = getClass().getGenericSuperclass();
        // ParameterizedType 參數化類型
        Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
        // 獲取到的 <T> 中的 T 的真實的類型
        Type actualType = actualTypeArguments[0];
        try {
            entityClass = Class.forName(actualType.getTypeName());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseDAOException(">>> BaseDAO() has some errors...");
        }
    }

    // 給預處理命令對象設置參數
    private void setParams(PreparedStatement psmt, Object... params) {
        try {
            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    psmt.setObject(i + 1, params[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseDAOException(">>> BaseDAO.setParams() has some errors...");
        }
    }

    // 執行更新，返回影響行數
    protected int executeUpdate(String sql, Object... params) {
        boolean insertFlag = false;
        insertFlag = sql.trim().toUpperCase().startsWith("INSERT");
        try {
            conn = ConnUtil.getConn();
            if (insertFlag) {
                psmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            } else {
                psmt = conn.prepareStatement(sql);
            }
            setParams(psmt, params);
            int count = psmt.executeUpdate();

            // if "insert", generate new key
            // if "update", do not generate new key
            if (insertFlag) {
                rs = psmt.getGeneratedKeys();
                if (rs.next()) {
                    return ((Long) rs.getLong(1)).intValue();
                }
            }

            return count;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseDAOException(">>> BaseDAO.executeUpdate() has some errors...");
        }
    }

    // 通過反射技術給 obj 物件的 property 屬性賦 propertyValue 值
    private void setValue(Object obj, String property, Object propertyValue) {
        try {
            Class clazz = obj.getClass();
            // 獲取property這個字串對應的屬性名 ， 比如 "fid"  去找 obj物件中的 fid 屬性
            Field field = clazz.getDeclaredField(property);

            if (field != null) {

                // if the field is self defined type, use a constructor that has only one parameter to create an instance
                String typeName = field.getType().getTypeName();
                if (isSelfDefinedType(typeName)) {
                    Constructor constructor = Class.forName(typeName).getDeclaredConstructor(Integer.class);
                    propertyValue = constructor.newInstance(propertyValue);
                }

                field.setAccessible(true);
                field.set(obj, propertyValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseDAOException(">>> BaseDAO.setValue() has some errors...");
        }
    }

    private boolean isSelfDefinedType(String typeName) {
        return !isNotSelfDefinedType(typeName);
    }

    private boolean isNotSelfDefinedType(String typeName) {
        // since we are not using other types defined by others
        // we don't write it all here
        return typeName.startsWith("java.lang") ||
               typeName.startsWith("java.util") ||
               typeName.startsWith("java.sql");
    }

    // 執行複雜查詢，返回例如統計結果
    protected Object[] executeComplexQuery(String sql, Object... params) {
        try {
            conn = ConnUtil.getConn();
            psmt = conn.prepareStatement(sql);
            setParams(psmt, params);
            rs = psmt.executeQuery();

            // 通過rs可以獲取結果集的中繼資料
            // 中繼資料：描述結果集資料的資料 , 簡單講，就是這個結果集有哪些列，什麼類型等等

            ResultSetMetaData rsmd = rs.getMetaData();
            // 獲取結果集的列數
            int columnCount = rsmd.getColumnCount();
            Object[] columnValueArr = new Object[columnCount];
            // 6.解析rs
            if (rs.next()) {
                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = rs.getObject(i + 1);     // 33    蘋果      5
                    columnValueArr[i] = columnValue;
                }
                return columnValueArr;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseDAOException(">>> BaseDAO.executeComplexQuery() has some errors...");
        }
    }

    // 執行查詢，返回單個實體物件
    protected T load(String sql, Object... params) {
        try {
            conn = ConnUtil.getConn();
            psmt = conn.prepareStatement(sql);
            setParams(psmt, params);
            rs = psmt.executeQuery();

            // 通過rs可以獲取結果集的中繼資料
            // 中繼資料：描述結果集資料的資料 , 簡單講，就是這個結果集有哪些列，什麼類型等等

            ResultSetMetaData rsmd = rs.getMetaData();
            // 獲取結果集的列數
            int columnCount = rsmd.getColumnCount();
            // 6.解析rs
            if (rs.next()) {
                T entity = (T) entityClass.newInstance();

                for (int i = 0; i < columnCount; i++) {
                    String columnName = rsmd.getColumnLabel(i + 1);  // fid   fname   price
                    Object columnValue = rs.getObject(i + 1);       // 33    蘋果     5
                    setValue(entity, columnName, columnValue);
                }
                return entity;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseDAOException(">>> BaseDAO.load() has some errors...");
        }
    }

    // 執行查詢，返回List
    protected List<T> executeQuery(String sql, Object... params) {
        try {
            List<T> list = new ArrayList<>();
            conn = ConnUtil.getConn();
            psmt = conn.prepareStatement(sql);
            setParams(psmt, params);
            rs = psmt.executeQuery();

            // 通過rs可以獲取結果集的中繼資料
            // 中繼資料：描述結果集資料的資料 , 簡單講，就是這個結果集有哪些列，什麼類型等等

            ResultSetMetaData rsmd = rs.getMetaData();
            // 獲取結果集的列數
            int columnCount = rsmd.getColumnCount();
            // 6.解析rs
            while (rs.next()) {
                T entity = (T) entityClass.newInstance();

                for (int i = 0; i < columnCount; i++) {
                    /*
                        getColumnLabel(): return nickname (label)
                        getColumnName(): return real name
                     */
                    String columnName = rsmd.getColumnLabel(i + 1);         // fid   fname   price
                    Object columnValue = rs.getObject(i + 1);    // 33    蘋果    5
                    setValue(entity, columnName, columnValue);
                }
                list.add(entity);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseDAOException(">>> BaseDAO.executeQuery() has some errors...");
        }
    }
}