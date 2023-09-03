package pojo;

import java.math.BigDecimal;
import filter.CharacterEncodingFilter;

public class CartItem {
    private Integer id;
    private Book book;
    private Integer count;
    private User user;

    private Double subTotalPrice;

    public CartItem() {}

    public CartItem(Integer id) {
        this.id = id;
    }

    public CartItem(Book book, Integer count, User user) {
        this.book = book;
        this.count = count;
        this.user = user;
    }

    public CartItem(Integer id, Integer count) {
        this.id = id;
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getSubTotalPrice() {
        BigDecimal bigDecimalBookPrice = new BigDecimal("" + book.getPrice());
        BigDecimal bigDecimalCount = new BigDecimal("" + count);
        this.subTotalPrice = (bigDecimalBookPrice.multiply(bigDecimalCount)).doubleValue();

        System.out.println("subTotalPrice = " + subTotalPrice);

        return subTotalPrice;
    }
}
