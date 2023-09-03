package dao;

import basedao.BaseDAO;
import pojo.Book;

import java.util.List;

public class BookDAO extends BaseDAO<Book> {

    public List<Book> getBookList() {
        String sql = "select * from t_book where status = 0";
        return executeQuery(sql);
    }

    public List<Book> getBookList(Double minPrice, Double maxPrice) {
        String sql = "select * from t_book where ? <= price and price <= ?";
        return executeQuery(sql, minPrice, maxPrice);
    }

    public Book getBook(Integer id) {
        String sql = "select * from t_book where id = ?";
        return load(sql, id);
    }

}
