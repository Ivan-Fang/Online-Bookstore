package controller;

import pojo.Cart;
import pojo.User;
import service.CartItemService;
import service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class UserController {
    private UserService userService = null;
    private CartItemService cartItemService = null;

    public String login(String name, String password, HttpSession session) {
        User user = userService.getUser(name, password);
        if (user != null) {
            // set the cart of the user
            Cart cart = cartItemService.getCart(user.getId());
            user.setCart(cart);

            session.setAttribute("currUser", user);
            return "redirect:book.do?operate=index";
        }
        return "user/login";
    }

    public String register(String verificationCode, String name, String password, String email, HttpSession session, HttpServletResponse resp) throws IOException {
        // verify
        String verificationKey = (String) session.getAttribute("KAPTCHA_SESSION_KEY");
        if (verificationKey != null && verificationCode.equals(verificationKey)) {
            User user = new User(name, password, email);
            userService.register(user);
            return "user/login";
        } else {
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html");
            PrintWriter writer = resp.getWriter();
            writer.println("<script language='JavaScript'>alert('驗證碼輸入錯誤')</script>");

            return "user/regist";
        }
    }

    public String checkDuplicateName(String name) {
        User user = userService.getUser(name);
        if (user != null) { // user name duplicate
            return "json:{'isDuplicateName':true}";
        } else {            // user name not duplicate
            return "json:{'isDuplicateName':false}";
        }
    }
}
