package model;

import utils.Validator;

public class Book {
    private final String bookId;
    private String title;
    private String author;
    private String genre;
    private int publicationYear;
    private int quantity;
    private int borrowCount;
    private int totalBorrowing;

    public Book(String bookId, String title, String author, String genre, int publicationYear, int quantity) {
        this.bookId = Validator.validateBasicString(bookId);
        setTitle(title);
        setAuthor(author);
        setGenre(genre);
        setPublicationYear(publicationYear);
        setQuantity(quantity);
        this.borrowCount = 0;
        this.totalBorrowing = 0;
    }

    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = Validator.validateBasicString(title);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = Validator.validateBasicString(author);
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = Validator.validateBasicString(genre);
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = Validator.validateNumber(publicationYear);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = Validator.validateNumber(quantity);
    }

    public int getBorrowCount() {
        return borrowCount;
    }

    public void setBorrowCount(int borrowCount) {
        this.borrowCount = Validator.validateNumber(borrowCount);
    }

    public int getTotalBorrowing() {
        return totalBorrowing;
    }

    public void setTotalBorrowing(int totalBorrowing) {
        this.totalBorrowing = Validator.validateNumber(totalBorrowing);
    }

    public String getStatus() {
        int currentQuantity = quantity - borrowCount;

        // Định nghĩa mã màu ANSI
        String RESET = "\u001B[0m";
        String GREEN = "\u001B[32m"; // Màu xanh lá
        String RED = "\u001B[31m";   // Màu đỏ

        // Trả về chuỗi kèm mã màu trực tiếp
        return (currentQuantity != 0)
                ? GREEN + "● AVAILABLE" + RESET
                : RED + "● UNAVAILABLE" + RESET;
    }

    public void showBookInfo() {
        System.out.println("🔖 BookingId: " + bookId);
        System.out.println("📖 Title: " + title);
        System.out.println("✍️ Author: " + author);
        System.out.println("🎭 Genre: " + genre);
        System.out.println("📅 Publication Year: " + publicationYear);
        System.out.println("🔢 Quantity: " + quantity);
        System.out.println("📊 BorrowCount: " + borrowCount);
        System.out.println("📦 Total Borrowing Records: " + totalBorrowing);
        System.out.println("💡 Status: " + getStatus());
    }
}