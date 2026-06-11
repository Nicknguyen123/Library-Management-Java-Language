package model;

import utils.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Borrowing {
    private final String transactionId;
    private Book book;
    private Member member;
    private final LocalDate borrowDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;
    private long fine;

    public Borrowing(String transactionId, Book book, Member member, LocalDate borrowDate) {
        this.transactionId = transactionId;
        this.book = validateBook(book);
        this.member = validateMember(member);
        this.borrowDate = Validator.validateDate(borrowDate);
        this.dueDate = calculateDueDate();
        this.returnDate = null;
        this.fine = 0;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        if (returnDate == null) {
            return;
        }
        this.returnDate = returnDate;
        long newFine = calculateTotalFine();
        this.fine = newFine;
    }

    public long getFine() {
        return fine;
    }

    private Book validateBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("❌ Book cannot be null");
        }

        return book;
    }

    private Member validateMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("❌ Member cannot be null");
        }

        return member;
    }

    private LocalDate calculateDueDate() {
        if (member instanceof PremiumMember) {
            return borrowDate.plusDays(21);
        } else {
            return borrowDate.plusDays(14);
        }
    }

    private long calculateTotalFine() {
        if (returnDate != null) {
            long lateDay = ChronoUnit.DAYS.between(dueDate, returnDate);
            if (lateDay <= 0) {
                return 0;
            } else {
                return member.calculateFine(lateDay);
            }
        } else {
            return 0;
        }
    }

    public void showBorrowingInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("🆔 TransactionId: " + transactionId);
        System.out.println("📘 BookId: " + book.getBookId());
        System.out.println("🪪 MemberId: " + member.getId());
        System.out.println("🛫 Borrow Date: " + borrowDate.format(formatter));
        System.out.println("⏳ Due Date: " + dueDate.format(formatter));
        if (returnDate == null) {
            System.out.println("🛬 Return Date: " + returnDate);
        } else {
            System.out.println("🛬 Return Date: " + returnDate.format(formatter));
        }

        System.out.println("💰 Fine: " + fine);
    }
}