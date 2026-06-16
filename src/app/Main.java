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

        PremiumMemberStorage premiumMemberStorage = new PremiumMemberStorage();
        RegularMemberStorage regularMemberStorage = new RegularMemberStorage();
        BookStorage bookStorage = new BookStorage();

        MemberService memberService = new MemberService();
        PremiumMemberService premiumMemberService = new PremiumMemberService(memberService, premiumMemberStorage);
        RegularMemberService regularMemberService = new RegularMemberService(memberService, regularMemberStorage);
        BookService bookService = new BookService(bookStorage);

        BorrowingStorage borrowingStorage = new BorrowingStorage(bookService, memberService);
        BorrowingService borrowingService = new BorrowingService(bookService, memberService, premiumMemberService,
                regularMemberService, borrowingStorage, bookStorage, premiumMemberStorage, regularMemberStorage);
        ReportService reportService = new ReportService(memberService, bookService, borrowingService);

        PremiumMemberMenu premiumMemberMenu = new PremiumMemberMenu(premiumMemberService, consoleHelper,
                inputHelper);
        RegularMemberMenu regularMemberMenu = new RegularMemberMenu(regularMemberService, consoleHelper,
                inputHelper);
        BookMenu bookMenu = new BookMenu(bookService, consoleHelper, inputHelper);
        BorrowingMenu borrowingMenu = new BorrowingMenu(borrowingService, memberService, bookService,
                consoleHelper, inputHelper);
        ReportMenu reportMenu = new ReportMenu(reportService, consoleHelper, inputHelper);

        premiumMemberStorage.loadData(premiumMemberService, memberService);
        regularMemberStorage.loadData(regularMemberService, memberService);
        bookStorage.loadData(bookService);
        borrowingStorage.loadData(borrowingService);

        ConsoleMenu consoleMenu = new ConsoleMenu(consoleHelper, inputHelper, premiumMemberMenu,
                regularMemberMenu, bookMenu, borrowingMenu, reportMenu);
        consoleMenu.showMainMenu();
    }
}