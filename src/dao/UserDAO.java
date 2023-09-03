package dao;

import basedao.BaseDAO;
import pojo.User;

public class UserDAO extends BaseDAO<User> {

    public User getUser(String name, String password) {
        String sql = "select * from t_user where name like ? and password like ?";
        return load(sql, name, password);
    }

    public void addUser(User user) {
        String sql = "insert into t_user values(0, ?, ?, ?, 0)";
        executeUpdate(sql, user.getName(), user.getPassword(), user.getEmail());
    }

    public User getUser(String name) {
        String sql = "select * from t_user where name=?";
        return load(sql, name);
    }

}
