package ui;

import model.Book;
import model.Borrowing;
import model.Member;
import model.PremiumMember;
import service.*;
import service.PremiumMemberService;
import storage.BookStorage;
import storage.BorrowingStorage;
import storage.PremiumMemberStorage;
import storage.RegularMemberStorage;
import utils.ConsoleHelper;
import utils.InputHelper;
import utils.Validator;

import java.time.LocalDate;
import java.util.Date;

public class BorrowingMenu {
    private BorrowingService borrowingService;
    private BorrowingStorage borrowingStorage;
    private MemberService memberService;
    private PremiumMemberService premiumMemberService;
    private RegularMemberService regularMemberService;
    private PremiumMemberStorage premiumMemberStorage;
    private RegularMemberStorage regularMemberStorage;
    private BookService bookService;
    private BookStorage bookStorage;
    private ConsoleHelper consoleHelper;
    private InputHelper inputHelper;

    public BorrowingMenu(BorrowingService borrowingService, BorrowingStorage borrowingStorage,
                         MemberService memberService, PremiumMemberService premiumMemberService,
                         RegularMemberService regularMemberService, PremiumMemberStorage premiumMemberStorage,
                         RegularMemberStorage regularMemberStorage, BookService bookService,
                         BookStorage bookStorage, ConsoleHelper consoleHelper, InputHelper inputHelper) {
        this.borrowingService = borrowingService;
        this.borrowingStorage = borrowingStorage;
        this.memberService = memberService;
        this.premiumMemberService = premiumMemberService;
        this.regularMemberService = regularMemberService;
        this.premiumMemberStorage = premiumMemberStorage;
        this.regularMemberStorage = regularMemberStorage;
        this.bookService = bookService;
        this.bookStorage = bookStorage;
        this.consoleHelper = consoleHelper;
        this.inputHelper = inputHelper;
    }

    public void showBorrowingMenu() {
        int choice;
        do {
            consoleHelper.clearScreen();
            System.out.println("⏳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━⏳");
            System.out.println("   📜              BORROWING MANAGEMENT                📜   ");
            System.out.println("⏳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━⏳");
            System.out.println("   1. ✨  Borrow a Book");
            System.out.println("   2. 💫  Return a Book");
            System.out.println("   3.  ⚡  View All Borrowed Books (Currently Out)");
            System.out.println("   4. 🛰️  View Borrowing History for a Specific Member");
            System.out.println("   0. ↩️  Back to Main Menu");
            System.out.println("⏳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━⏳");

            choice = inputHelper.readIntInRange("🔮 Enter your choice: ", 0, 4);

            switch (choice) {
                case 1:
                    borrowBook();
                    break;
                case 2:
                    returnBook();
                    break;
                case 3:
                    viewCurBorrwowedBook();
                    break;
                case 4:
                    viewBorrowingHistory();
                    break;
                case 0:
                    System.out.println("✨ Returning to the Console Menu...");
                    consoleHelper.pause();
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please enter a number between 0 and 4.");
            }
        } while (choice != 0);
    }

    private void borrowBook() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            System.out.println("👜━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👜");
            System.out.println("   ⚙️                  BORROW BOOK PROCESS               ⚙️   ");
            System.out.println("👜━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👜");
            System.out.println("📌 Please enter the following details:");
            System.out.println("──────────────────────────────────────────────────────────");
            System.out.println("ID must start with 'BOR' followed by 3 digits (e.g., BOR001, BOR015).");
            System.out.println("ID must start with 'BK' followed by 3 digits (e.g., BK001, BK015).");
            System.out.println("ID must start with 'PRE' followed by 3 digits (e.g., PRE001, PRE015).");
            System.out.println("ID must start with 'REG' followed by 3 digits (e.g., REG001, REG015).");
            System.out.println("💡 Required format: DD/MM/YYYY (e.g., 08/06/2026)");

            try {
                int currentQuantity = borrowingService.getBorrowingList().size();
                String id = String.format("BOR%04d", currentQuantity + 1);
                System.out.printf("🪪 Generated Borrowing ID: [%s]\n", id);

                String bookId = inputHelper.readIdBook("📖 Enter Book ID: ");
                Book book = bookService.findBookById(bookId);
                String memberId = inputHelper.readIdMember("👉 Enter Member ID: ");
                Member member = memberService.findMemberById(memberId);
                LocalDate borrowDate = inputHelper.readDate("📅 Enter the borrow date: ");

                Borrowing borrowing = new Borrowing(id, book, member, borrowDate);
                borrowingService.borrowBook(borrowing);
                borrowingStorage.saveOneBorrowing(borrowing);
                bookStorage.saveAllBook(bookService.getBookList());
                if (member instanceof PremiumMember) {
                    premiumMemberStorage.saveAllPremiumMember(premiumMemberService.getPremiumList());
                } else {
                    regularMemberStorage.saveAllRegularMember(regularMemberService.getRegularMemberList());
                }

                System.out.println("\n✨ Successfully added Borrowing!");
                yesNo = inputHelper.readYesNo("🔄 Do you want to add another Borrowing (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

                yesNo = inputHelper.readYesNo("🔄 Do you want to add another Borrowing (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private void returnBook() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            System.out.println("📥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📥");
            System.out.println("   ⚙️                  RETURN BOOK PROCESS               ⚙️   ");
            System.out.println("📥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📥");
            System.out.println("ID must start with 'BOR' followed by 4 digits (e.g., BOR0001, BOR0015).");
            System.out.println("💡 Required format: DD/MM/YYYY (e.g., 08/06/2026)");

            String id = inputHelper.readIdBorrowing("👉 Enter Borrowing ID: ");

            Borrowing borrowing = borrowingService.findBorrowingByReturnDate(id);

            if (borrowing == null) {
                System.out.println("❌ No active (unreturned) borrowing transaction found with this ID!");
                yesNo = inputHelper.readYesNo("🔄 Do you want to return another Borrowing (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }

            System.out.println("✅ Borrowing found!");
            borrowing.showBorrowingInfo();
            System.out.println("💳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            yesNo = inputHelper.readYesNo("🔄 Do you want to return this Borrowing (Y/N): ");
            if (yesNo == 'Y') {
                supportReturnBook(borrowing);

                yesNo = inputHelper.readYesNo("🔄 Do you want to return another Borrowing (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            } else {
                yesNo = inputHelper.readYesNo("🔄 Do you want to return another Borrowing (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private void supportReturnBook(Borrowing borrowing) {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            System.out.println("📊 CURRENT BORROWING DETAILS");
            borrowing.showBorrowingInfo();
            System.out.println("👉 Note: The return date must be AFTER or EQUAL to the borrowed date!");
            System.out.println("──────────────────────────────────────────────────────────");

            try {
                LocalDate returnDate = inputHelper.readDate("📅 Enter the return date: ");
                borrowingService.returnBook(borrowing, returnDate);
                borrowingStorage.saveAllBorrowing(borrowingService.getBorrowingList());
                bookStorage.saveAllBook(bookService.getBookList());
                if (borrowing.getMember() instanceof PremiumMember) {
                    premiumMemberStorage.saveAllPremiumMember(premiumMemberService.getPremiumList());
                } else {
                    regularMemberStorage.saveAllRegularMember(regularMemberService.getRegularMemberList());
                }

                System.out.println("\n✨ Successfully returned Borrowing!");
                consoleHelper.pause();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

                yesNo = inputHelper.readYesNo("🔄 Do you want to return this Borrowing (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private void viewCurBorrwowedBook() {
        consoleHelper.clearScreen();
        System.out.println("🔍━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━🔍");
        System.out.println("⚙️             VIEW ALL BORROWED BOOKS (CURRENTLY OUT)          ⚙️   ");
        System.out.println("🔍━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━🔍");

        borrowingService.displayCurrentBorrowing();
        consoleHelper.pause();
    }

    private void viewBorrowingHistory() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            System.out.println("📜━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📜");
            System.out.println("📚                  VIEW BORROWING HISTORY                  📚");
            System.out.println("📜━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📜");
            System.out.println("ID must start with 'PRE' followed by 3 digits (e.g., PRE001, PRE015).");
            System.out.println("ID must start with 'REG' followed by 3 digits (e.g., REG001, REG015).");

            String id = inputHelper.readIdMember("🔎 Enter Member ID to Search: ");

            Member member = memberService.findMemberById(id);

            if (member == null) {
                System.out.println("⚠️ Member not found with ID: " + id);
                yesNo = inputHelper.readYesNo("🔄 Do you want to search another member (Y/N): ");
                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }

            System.out.println("✅ Member found!");
            member.showMemberInfo();
            System.out.println("──────────────────────────────────────────────────────────");
            yesNo = inputHelper.readYesNo("🔄 Do you want to view the histoty of this member (Y/N): ");

            if (yesNo == 'Y') {
                consoleHelper.clearScreen();
                borrowingService.displayHistoryBorrowing(member);

                yesNo = inputHelper.readYesNo("🔄 Do you want to view another member (Y/N): ");
                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            } else {
                yesNo = inputHelper.readYesNo("🔄 Do you want to view another member (Y/N): ");
                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }
}