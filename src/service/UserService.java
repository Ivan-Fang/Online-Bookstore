package service;

import dao.UserDAO;
import pojo.User;

public class UserService {
    private UserDAO userDAO = null;

    public User getUser(String name, String password) {
        return userDAO.getUser(name, password);
    }

    public void register(User user) {
        userDAO.addUser(user);
    }

    public User getUser(String name) {
        return userDAO.getUser(name);
    }

}
