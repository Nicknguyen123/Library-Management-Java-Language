package app;

import service.*;
import storage.BookStorage;
import storage.BorrowingStorage;
import storage.PremiumMemberStorage;
import storage.RegularMemberStorage;
import ui.*;
import utils.ConsoleHelper;
import utils.InputHelper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsoleHelper consoleHelper = new ConsoleHelper(scanner);
        InputHelper inputHelper = new InputHelper(scanner);

        MemberService memberService = new MemberService();
        PremiumMemberService premiumMemberService = new PremiumMemberService(memberService);
        RegularMemberService regularMemberService = new RegularMemberService(memberService);
        BookService bookService = new BookService();
        BorrowingService borrowingService = new BorrowingService(bookService, memberService);
        ReportService reportService = new ReportService(memberService, bookService, borrowingService);

        PremiumMemberStorage premiumMemberStorage = new PremiumMemberStorage();
        RegularMemberStorage regularMemberStorage = new RegularMemberStorage();
        BookStorage bookStorage = new BookStorage();
        BorrowingStorage borrowingStorage = new BorrowingStorage(bookService, memberService);

        PremiumMemberMenu premiumMemberMenu = new PremiumMemberMenu(memberService, premiumMemberService,
                premiumMemberStorage, consoleHelper, inputHelper);
        RegularMemberMenu regularMemberMenu = new RegularMemberMenu(memberService, regularMemberService,
                regularMemberStorage, consoleHelper, inputHelper);
        BookMenu bookMenu = new BookMenu(bookService, bookStorage,consoleHelper, inputHelper);
        BorrowingMenu borrowingMenu = new BorrowingMenu(borrowingService, borrowingStorage, memberService,
                premiumMemberService, regularMemberService, premiumMemberStorage, regularMemberStorage,
                bookService, bookStorage, consoleHelper, inputHelper);
        ReportMenu reportMenu = new ReportMenu(reportService, consoleHelper, inputHelper);

        premiumMemberStorage.loadData(premiumMemberService, memberService);
        regularMemberStorage.loadData(regularMemberService, memberService);
        bookStorage.loadData(bookService);
        borrowingStorage.loadData(borrowingService);

        ConsoleMenu consoleMenu = new ConsoleMenu(scanner, consoleHelper, inputHelper, premiumMemberMenu,
                regularMemberMenu, bookMenu, borrowingMenu, reportMenu);
        consoleMenu.showMainMenu();
    }
}