package service;

import model.*;
import storage.BookStorage;
import storage.BorrowingStorage;
import storage.PremiumMemberStorage;
import storage.RegularMemberStorage;
import utils.Validator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowingService {
    private List<Borrowing> borrowingList;
    private BookService bookService;
    private MemberService memberService;
    private PremiumMemberService premiumMemberService;
    private RegularMemberService regularMemberService;
    private BorrowingStorage borrowingStorage;
    private BookStorage bookStorage;
    private PremiumMemberStorage premiumMemberStorage;
    private RegularMemberStorage regularMemberStorage;

    public BorrowingService(BookService bookService, MemberService memberService,
                            PremiumMemberService premiumMemberService, RegularMemberService regularMemberService,
                            BorrowingStorage borrowingStorage, BookStorage bookStorage,
                            PremiumMemberStorage premiumMemberStorage, RegularMemberStorage regularMemberStorage) {
        this.borrowingList = new ArrayList<>();
        this.bookService = bookService;
        this.memberService = memberService;
        this.premiumMemberService = premiumMemberService;
        this.regularMemberService = regularMemberService;
        this.borrowingStorage = borrowingStorage;
        this.bookStorage = bookStorage;
        this.premiumMemberStorage = premiumMemberStorage;
        this.regularMemberStorage = regularMemberStorage;
    }

    public List<Borrowing> getBorrowingList() {
        return new ArrayList<>(borrowingList);
    }

    public void borrowBook(Borrowing borrowing) {
        checkBorrowNull(borrowing);

        if (findBorrowingById(borrowing.getTransactionId()) != null) {
            throw new IllegalArgumentException("❌ This Borrowing already exists in the system. " +
                    "Duplicate ID: " + borrowing.getTransactionId());
        }

        Book book = bookService.findBookById(borrowing.getBook().getBookId());
        if (book == null) {
            throw new IllegalArgumentException("❌ Book not found in the system.");
        }

        String status = book.getStatus();
        if (status.charAt(2) == 'U') {
            throw new IllegalArgumentException("❌ This book is currently out of stock.");
        }

        Member member = memberService.findMemberById(borrowing.getMember().getId());
        if (member == null) {
            throw new IllegalArgumentException("❌ Member not found in the system.");
        }

        if (member.getCurrentBorrowedCount() >= member.getLimitBorrow()) {
            throw new IllegalArgumentException("❌ Borrowing limit reached: This Member has already " +
                    "borrowed " + member.getLimitBorrow() + " books and cannot borrow more.");
        }

        if (borrowing.getBorrowDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("🚫 Invalid Date: The borrow date must be today or earlier." +
                    " Future dates are not allowed.");
        }

        boolean isDuplicate = checkDuplicateBorrowing(book.getBookId(), member.getId());
        if (isDuplicate) {
            throw new IllegalArgumentException("🚫 Duplicate Book Error: You cannot borrow the same book twice!");
        }

        borrowingList.add(borrowing);
        book.setBorrowCount(book.getBorrowCount() + 1);
        book.setTotalBorrowing(book.getTotalBorrowing() + 1);
        member.setCurrentBorrowedCount(member.getCurrentBorrowedCount() + 1);
        member.setTotalBorrowing(member.getTotalBorrowing() + 1);
        borrowingStorage.saveOneBorrowing(borrowing);
        bookStorage.saveAllBook(bookService.getBookList());

        if (member instanceof PremiumMember) {
            premiumMemberStorage.saveAllPremiumMember(premiumMemberService.getPremiumList());
        } else {
            regularMemberStorage.saveAllRegularMember(regularMemberService.getRegularMemberList());
        }
    }

    public void addBorrowingFromFile(Borrowing borrowing) {
        borrowingList.add(borrowing);
    }

    public void returnBook(Borrowing borrowing, LocalDate returnDate) {
        checkBorrowNull(borrowing);

        returnDate = Validator.validateDate(returnDate);

        if (returnDate.isBefore(borrowing.getBorrowDate())) {
            throw new IllegalArgumentException("❌ Return Date must be after Borrow Date!");
        }

        borrowing.setReturnDate(returnDate);

        Book book = borrowing.getBook();
        book.setBorrowCount(book.getBorrowCount() - 1);

        Member member = borrowing.getMember();
        member.setCurrentBorrowedCount(member.getCurrentBorrowedCount() - 1);

        borrowingStorage.saveAllBorrowing(borrowingList);
        bookStorage.saveAllBook(bookService.getBookList());

        if (member instanceof PremiumMember) {
            premiumMemberStorage.saveAllPremiumMember(premiumMemberService.getPremiumList());
        } else {
            regularMemberStorage.saveAllRegularMember(regularMemberService.getRegularMemberList());
        }
    }

    public void displayCurrentBorrowing() {

        if (borrowingList.isEmpty()) {
            System.out.println("⚠️ No borrowing found in the system.");
            return;
        }

        boolean found = false;
        for (Borrowing borrowing : borrowingList) {
            if (borrowing.getReturnDate() == null) {
                borrowing.showBorrowingInfo();
                System.out.println("💳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                found = true;
            }
        }

        if (!found) {
            System.out.println("⚠️ There are no active (unreturned) borrowings at the moment.");
        }
    }

    public void displayHistoryBorrowing(Member member) {
        memberService.checkMemberNull(member);

        if (borrowingList.isEmpty()) {
            System.out.println("⚠️ No borrowing found in the system.");
            return;
        }

        boolean found = false;
        String id = member.getId();
        for (Borrowing borrowing : borrowingList) {
            String idList = borrowing.getMember().getId();
            if (idList.equals(id)) {
                borrowing.showBorrowingInfo();
                System.out.println("💳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                found = true;
            }
        }

        if (!found) {
            System.out.printf("⚠️ No borrowing history found for Member ID: %s (%s)\n",
                    member.getId(), member.getName());
        }
    }

    public Borrowing findBorrowingById(String id) {
        String safeId = Validator.validateBasicString(id);

        for (Borrowing borrowing : borrowingList) {
            if (borrowing.getTransactionId().equals(safeId)) {
                return borrowing;
            }
        }

        return null;
    }

    public Borrowing findBorrowingByReturnDate(String id) {
        String safeId = Validator.validateBasicString(id);

        for (Borrowing borrowing : borrowingList) {
            if (borrowing.getReturnDate() == null) {
                if (borrowing.getTransactionId().equals(safeId)) {
                    return borrowing;
                }
            }
        }

        return null;
    }

    private void checkBorrowNull(Borrowing borrowing) {
        if (borrowing == null) {
            throw new IllegalArgumentException("❌ Borrowing cannot be null");
        }
    }

    private boolean checkDuplicateBorrowing(String bookId, String memberId) {
        for (Borrowing borrowing : borrowingList) {
            Book book = borrowing.getBook();
            Member member = borrowing.getMember();
            if (book.getBookId().equals(bookId) && member.getId().equals(memberId)) {
                return true;
            }
        }

        return false;
    }
}