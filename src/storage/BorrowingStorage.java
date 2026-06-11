package storage;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import model.Book;
import model.Borrowing;
import model.Member;
import service.BookService;
import service.BorrowingService;
import service.MemberService;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BorrowingStorage {
    public static final String FOLDER = "data";
    public static final String FILE_PATH = "data/borrowing.txt";
    private BookService bookService;
    private MemberService memberService;

    public BorrowingStorage(BookService bookService, MemberService memberService) {
        this.bookService = bookService;
        this.memberService = memberService;
    }

    public void saveOneBorrowing(Borrowing borrowing) {
        File folder = new File(FOLDER);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        try (
                FileWriter fileWriter = new FileWriter(FILE_PATH, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        ) {

            String dataLine = convertObjectToString(borrowing);
            bufferedWriter.write(dataLine);
            bufferedWriter.newLine();

        } catch (IOException e) {
            System.out.println("❌ Failed to save Borrowing!");
        }
    }

    public void saveAllBorrowing(List<Borrowing> borrowingList) {
        File folder = new File(FOLDER);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        try (
                FileWriter fileWriter = new FileWriter(FILE_PATH, false);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        ) {
            for (Borrowing borrowing : borrowingList) {
                String dataLine = convertObjectToString(borrowing);
                bufferedWriter.write(dataLine);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Failed to save Borrowing!");

        }
    }

    public void loadData(BorrowingService borrowingService) {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return;
        }

        try (
                FileReader fileReader = new FileReader(FILE_PATH);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            String dataLine;
            while ((dataLine = bufferedReader.readLine()) != null) {
                Borrowing borrowing = convertStringToObject(dataLine);
                borrowingService.addBorrowingToList(borrowing);
            }

        } catch (IOException e) {
            System.out.println("⚠️ Warning: Failed to load Borrowing data!");
        }
    }

    private String convertObjectToString(Borrowing borrowing) {
        String bookId = borrowing.getBook().getBookId();
        String memberId = borrowing.getMember().getId();
        String borrowDate = borrowing.getBorrowDate().toString();
        String dueDate = borrowing.getDueDate().toString();
        String returnDate = (borrowing.getReturnDate() != null) ? borrowing.getReturnDate().toString() : "";


        String result = borrowing.getTransactionId() + '|' + bookId + '|' + memberId + '|' + borrowDate +
                '|' + dueDate + '|' + returnDate + '|' + borrowing.getFine();

        return result;
    }

    private Borrowing convertStringToObject(String data) {
        String[] source = data.split("\\|");

        String id = source[0];
        String bookId = source[1];
        Book book = bookService.findBookById(bookId);
        String memberId = source[2];
        Member member = memberService.findMemberById(memberId);
        LocalDate borrowDate = LocalDate.parse(source[3]);
        LocalDate returnDate = null;
        if (!source[5].isEmpty()) {
           returnDate = LocalDate.parse(source[5]);
        }

        Borrowing borrowing = new Borrowing(id, book, member, borrowDate);
        borrowing.setReturnDate(returnDate);

        return borrowing;
    }
}