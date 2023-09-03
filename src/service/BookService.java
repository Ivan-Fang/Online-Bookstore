package service;

import dao.BookDAO;
import pojo.Book;

import java.util.List;

public class BookService {
    private BookDAO bookDAO = null;

    public List<Book> getBookList() {
        return bookDAO.getBookList();
    }

    public List<Book> getBookList(Double minPrice, Double maxPrice) {
        return bookDAO.getBookList(minPrice, maxPrice);
    }

    public Book getBook(Integer id) {
        return bookDAO.getBook(id);
    }
}
