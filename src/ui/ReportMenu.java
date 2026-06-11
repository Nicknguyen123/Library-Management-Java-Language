package ui;

import model.Book;
import model.Borrowing;
import model.Member;
import service.ReportService;
import utils.ConsoleHelper;
import utils.InputHelper;

import java.util.List;

public class ReportMenu {
    private ReportService reportService;
    private ConsoleHelper consoleHelper;
    private InputHelper inputHelper;
    public static final int topRank = 5;

    public ReportMenu(ReportService reportService, ConsoleHelper consoleHelper, InputHelper inputHelper) {
        this.reportService = reportService;
        this.consoleHelper = consoleHelper;
        this.inputHelper = inputHelper;
    }

    public void showReportMenu() {
        int choice;
        do {
            consoleHelper.clearScreen();
            System.out.println("📊━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📊");
            System.out.println("   📈              REPORTING MANAGEMENT                📈        ");
            System.out.println("📊━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📊");
            System.out.println("   1. 📋 View All Currently Borrowed Books");
            System.out.println("   2. 🚨 Track Overdue Books & Deadlines");
            System.out.println("   3. 🔥 Check Most Popular Books (Trending)");
            System.out.println("   4. 👥 View Top Members With Most Borrowings");
            System.out.println("   0. ↩️ Back to Main Menu");
            System.out.println("📊━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📊");

            choice = inputHelper.readIntInRange("🔮 Enter your choice: ", 0, 4);

            switch (choice) {
                case 1:
                    viewCurBorrwowedBook();
                    break;
                case 2:
                    viewOverdueBook();
                    break;
                case 3:
                    viewPopularBook();
                    break;
                case 4:
                    viewPopularMember();
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

    private void viewCurBorrwowedBook() {
        consoleHelper.clearScreen();
        System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");
        System.out.println("   🔍      VIEW ALL CURRENTLY BORROWED BOOKS           🔍   ");
        System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");

        List<Borrowing> temp = reportService.getCurrentBorrowing();

        if (temp.isEmpty()) {
            System.out.println("✨ No books are currently being borrowed.");
            consoleHelper.pause();
            return;
        }

        for (Borrowing borrowing : temp) {
            borrowing.showBorrowingInfo();
            System.out.println("💳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        }
        consoleHelper.pause();
    }

    private void viewOverdueBook() {
        consoleHelper.clearScreen();
        System.out.println("⏰━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━⏰");
        System.out.println("📕                    VIEW ALL OVERDUE BOOKS                    📕");
        System.out.println("⏰━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━⏰");

        List<Borrowing> temp = reportService.getOverdueBorrowing();

        if (temp.isEmpty()) {
            System.out.println("✨ No overdue books found.");
            consoleHelper.pause();
            return;
        }

        for (Borrowing borrowing : temp) {
            borrowing.showBorrowingInfo();
            System.out.println("💳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        }
        consoleHelper.pause();
    }

    private void viewPopularBook() {
        consoleHelper.clearScreen();
        System.out.println("🔥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━🔥");
        System.out.println("📚                VIEW MOST POPULAR BOOKS (TRENDING)                📚");
        System.out.println("🔥━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━🔥");

        List<Book> temp = reportService.getMostPopularBook();

        if (temp.isEmpty()) {
            System.out.println("📭 No books have been borrowed yet.");
            consoleHelper.pause();
            return;
        }

        if (temp.size() >= topRank) {
            for (int i = 0; i < topRank; i++) {
                temp.get(i).showBookInfo();
                System.out.println("📖━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            }
        } else {
            for (Book book : temp) {
                book.showBookInfo();
                System.out.println("📖━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            }
        }

        consoleHelper.pause();
    }

    private void viewPopularMember() {
        consoleHelper.clearScreen();
        System.out.println("🏆━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━🏆");
        System.out.println("👥           VIEW TOP MEMBERS WITH MOST BORROWINGS           👥");
        System.out.println("🏆━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━🏆");

        List<Member> temp = reportService.getMostBorrowingMember();

        if (temp.isEmpty()) {
            System.out.println("📭 No member have borrowed yet.");
            consoleHelper.pause();
            return;
        }

        if (temp.size() >= topRank) {
            for (int i = 0; i < topRank; i++) {
                temp.get(i).showMemberInfo();
                System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            }
        } else {
            for (Member member : temp) {
                member.showMemberInfo();
                System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            }
        }
        consoleHelper.pause();
    }
}