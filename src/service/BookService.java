package service;

import storage.BookStorage;
import utils.Validator;
import model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookService {
    private final List<Book> bookList;
    private BookStorage bookStorage;

    public BookService(BookStorage bookStorage) {
        this.bookList = new ArrayList<>();
        this.bookStorage = bookStorage;
    }

    public List<Book> getBookList() {
        return new ArrayList<>(bookList);
    }

    public void addBook(Book book) {
        checkBookNull(book);

        Book temp = findBookById(book.getBookId());
        if (temp != null) {
            throw new IllegalArgumentException("❌ This Book already exists in the system. " +
                    "Duplicate ID: " + book.getBookId());
        }

        bookList.add(book);
        bookStorage.saveOneBook(book);
    }

    public void addBookFromFile(Book book) {
        bookList.add(book);
    }

    public void deleteBook(String id) {
        String safeId = Validator.validateBasicString(id);

        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getBookId().equals(safeId)) {
                bookList.remove(i);
                bookStorage.saveAllBook(bookList);
                return;
            }
        }

        throw new IllegalArgumentException("⚠️ Alert: Unable to remove. No book found with the specified ID: "
                + safeId);
    }

    public void displayBook() {
        if (bookList.isEmpty()) {
            System.out.println("⚠️ No book found in the system.");
            return;
        }

        for (Book book : bookList) {
            book.showBookInfo();
            System.out.println("📖━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        }
    }

    public void updateTitle(Book book, String newTitle) {
        checkBookNull(book);

        String safeTitle = Validator.validateBasicString(newTitle);

        book.setTitle(safeTitle);
        bookStorage.saveAllBook(bookList);
    }

    public void updateAuthor(Book book, String newAuthor) {
        checkBookNull(book);

        String safeAuthor = Validator.validateBasicString(newAuthor);

        book.setTitle(safeAuthor);
        bookStorage.saveAllBook(bookList);
    }

    public void updateGenre(Book book, String newGenre) {
        checkBookNull(book);

        String safeGenre = Validator.validateBasicString(newGenre);

        book.setGenre(safeGenre);
        bookStorage.saveAllBook(bookList);
    }

    public void updateYear(Book book, int newYear) {
        checkBookNull(book);

        int safeYear = Validator.validateNumber(newYear);

        book.setPublicationYear(safeYear);
        bookStorage.saveAllBook(bookList);
    }

    public void updateQuantity(Book book, int newQuantity) {
        checkBookNull(book);

        int safeQuantity = Validator.validateNumber(newQuantity);
        if (safeQuantity < book.getBorrowCount()) {
            throw new IllegalArgumentException("❌ Cannot reduce total quantity below the number of " +
                    "currently borrowed books!");
        }

        book.setQuantity(safeQuantity);
        bookStorage.saveAllBook(bookList);
    }

    public Book findBookById(String id) {
        String safeId = Validator.validateBasicString(id);

        for (Book book : bookList) {
            if (book.getBookId().equals(safeId)) {
                return book;
            }
        }

        return null;
    }

    public List<Book> findBookByTitle(String title) {
        String safeTitle = Validator.validateBasicString(title);

        List<Book> source = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getTitle().contains(safeTitle)) {
                source.add(book);
            }
        }

        if (source.isEmpty()) {
            return null;
        } else {
            return source;
        }
    }

    public List<Book> findBookByAuthor(String author) {
        String safeAuthor = Validator.validateBasicString(author);

        List<Book> source = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getAuthor().contains(safeAuthor)) {
                source.add(book);
            }
        }

        if (source.isEmpty()) {
            return null;
        } else {
            return source;
        }
    }

    public List<Book> findBookByGenre(String genre) {
        String safeGenre = Validator.validateBasicString(genre);

        List<Book> source = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getGenre().contains(safeGenre)) {
                source.add(book);
            }
        }

        if (source.isEmpty()) {
            return null;
        } else {
            return source;
        }
    }

    private void checkBookNull(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("❌ Book cannot be null");
        }
    }
}