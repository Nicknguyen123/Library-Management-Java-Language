package service;

import model.Book;
import model.Borrowing;
import model.Member;

import java.time.LocalDate;
import java.util.*;


public class ReportService {
    private MemberService memberService;
    private BookService bookService;
    private BorrowingService borrowingService;

    public ReportService(MemberService memberService, BookService bookService, BorrowingService borrowingService) {
        this.memberService = memberService;
        this.bookService = bookService;
        this.borrowingService = borrowingService;
    }

    public List<Borrowing> getCurrentBorrowing() {

        List<Borrowing> borrowingList = new ArrayList<>();

        for (Borrowing borrowing : borrowingService.getBorrowingList()) {
            if (borrowing.getReturnDate() == null) {
                borrowingList.add(borrowing);
            }
        }

        return borrowingList;
    }

    public List<Borrowing> getOverdueBorrowing() {

        List<Borrowing> borrowingList = new ArrayList<>();

        for (Borrowing borrowing : borrowingService.getBorrowingList()) {
            if (borrowing.getReturnDate() == null && borrowing.getDueDate().isBefore(LocalDate.now())) {
                borrowingList.add(borrowing);
            }
        }

        return borrowingList;
    }

    public List<Book> getMostPopularBook() {
        List<Book> bookList = bookService.getBookList();

        Comparator<Book> bookComparator = (b1, b2) -> b2.getTotalBorrowing() - b1.getTotalBorrowing();
        bookList.sort(bookComparator);
        return bookList;
    }

    public List<Member> getMostBorrowingMember() {
        List<Member> memberList = memberService.getMemberList();

        Comparator<Member> memberComparator = (m1, m2) -> m2.getTotalBorrowing() - m1.getTotalBorrowing();
        memberList.sort(memberComparator);

        return memberList;
    }
}