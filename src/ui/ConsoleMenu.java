package ui;

import utils.ConsoleHelper;
import utils.InputHelper;

import java.util.Scanner;

public class ConsoleMenu {
    private Scanner scanner;
    private ConsoleHelper consoleHelper;
    private InputHelper inputHelper;
    private PremiumMemberMenu premiumMemberMenu;
    private RegularMemberMenu regularMemberMenu;
    private BookMenu bookMenu;
    private BorrowingMenu borrowingMenu;
    private ReportMenu reportMenu;

    public ConsoleMenu(Scanner scanner, ConsoleHelper consoleHelper, InputHelper inputHelper, PremiumMemberMenu
            premiumMemberMenu, RegularMemberMenu regularMemberMenu, BookMenu bookMenu,
                       BorrowingMenu borrowingMenu, ReportMenu reportMenu) {
        this.scanner = scanner;
        this.consoleHelper = consoleHelper;
        this.inputHelper = inputHelper;
        this.premiumMemberMenu = premiumMemberMenu;
        this.regularMemberMenu = regularMemberMenu;
        this.bookMenu = bookMenu;
        this.borrowingMenu = borrowingMenu;
        this.reportMenu = reportMenu;
    }

    public void showMainMenu() {
        int choice = 0;
        do {
            consoleHelper.clearScreen();
            System.out.println("✨━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━✨");
            System.out.println("   🌟            LIBRARY MANAGEMENT SYSTEM             🌟        ");
            System.out.println("✨━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━✨");
            System.out.println("   1. 👥 Member Management");
            System.out.println("   2. 📖 Book Management");
            System.out.println("   3. 🔄 Borrowing Management");
            System.out.println("   4. 📊 Report");
            System.out.println("   0. 🚪 Exit Program");
            System.out.println("✨━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━✨");

            choice = inputHelper.readIntInRange("👉 Enter your choice: ", 0, 4);

            switch (choice) {
                case 1:
                    showMemberMenu();
                    break;
                case 2:
                    bookMenu.showBookMenu();
                    break;
                case 3:
                    borrowingMenu.showBorrowingMenu();
                    break;
                case 4:
                    reportMenu.showReportMenu();
                    break;
                case 0:
                    consoleHelper.clearScreen();
                    System.out.println("✨━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━✨");
                    System.out.println("   ✅  System exited successfully.");
                    System.out.println("   👋  Goodbye! See you next time.");
                    System.out.println("✨━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━✨");
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please enter a number between 0 and 4.");
            }
        } while (choice != 0);
    }

    private void showMemberMenu() {
        int choice;
        do {
            consoleHelper.clearScreen();
            System.out.println("⚙️━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━⚙️");
            System.out.println("   🔮               MEMBER MANAGEMENT                  🔮        ");
            System.out.println("⚙️━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━⚙️");
            System.out.println("   1. 👑 Premium Member Management");
            System.out.println("   2. 💎 Regular Member Management");
            System.out.println("   0. ↩️ Back to Main Menu");
            System.out.println("⚙️━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━⚙️");

            choice = inputHelper.readIntInRange("💻 Enter your choice: ", 0, 2);

            switch (choice) {
                case 1:
                    premiumMemberMenu.showPremiumMemberMenu();
                    break;
                case 2:
                    regularMemberMenu.showRegularMemberMenu();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please enter a number between 0 and 4.");
            }
        } while (choice != 0);
    }
}