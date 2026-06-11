package ui;

import model.Book;
import service.BookService;
import storage.BookStorage;
import utils.ConsoleHelper;
import utils.InputHelper;
import utils.StringUtils;

import java.time.Year;
import java.util.List;
import java.util.function.Consumer;

public class BookMenu {
    private BookService bookService;
    private BookStorage bookStorage;
    private ConsoleHelper consoleHelper;
    private InputHelper inputHelper;
    public static final int currentYear = Year.now().getValue();

    public BookMenu(BookService bookService, BookStorage bookStorage, ConsoleHelper consoleHelper,
                    InputHelper inputHelper) {
        this.bookService = bookService;
        this.bookStorage = bookStorage;
        this.consoleHelper = consoleHelper;
        this.inputHelper = inputHelper;
    }

    public void showBookMenu() {
        int choice;
        do {
            consoleHelper.clearScreen();
            System.out.println("📘━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📘");
            System.out.println("   📚                BOOK MANAGEMENT                   📚        ");
            System.out.println("📘━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📘");
            System.out.println("   1. ➕ Add New Book");
            System.out.println("   2. 📝 Update Book Information");
            System.out.println("   3. ❌ Remove a Book");
            System.out.println("   4. 📋 View All Books");
            System.out.println("   5. 🔍 Search Books");
            System.out.println("   0. ↩️ Back to Main Menu");
            System.out.println("📘━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📘");

            choice = inputHelper.readIntInRange("🔮 Enter your choice: ", 0, 5);

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    updateBook();
                    break;
                case 3:
                    deleteBook();
                    break;
                case 4:
                    displayBook();
                    break;
                case 5:
                    searchBook();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please enter a number between 0 and 5.");
            }
        } while (choice != 0);
    }

    private void addBook() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            System.out.println("📖━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📖");
            System.out.println("   📚                  ADD NEW BOOK                    📚        ");
            System.out.println("📖━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📖");
            System.out.println("📌 Please enter the following details:");
            System.out.println("──────────────────────────────────────────────────────────");
            System.out.println("ID must start with 'BK' followed by 3 digits (e.g., BK001, BK015).");

            try {
                String id = inputHelper.readIdBook("📖 Enter Book ID: ");
                String title = inputHelper.readTitle("📝 Enter Book Title: ");
                title = StringUtils.beautify(title);
                String author = inputHelper.readStringWord("✒️ Enter Author Name: ");
                author = StringUtils.beautify(author);
                String genre = inputHelper.readStringWord("🎭 Enter Book Genre: ");
                genre = StringUtils.beautify(genre);
                int publicationYear = inputHelper.readIntInRange("⏳ Enter Publication Year: ",
                        1440, currentYear);
                int quantity = inputHelper.readIntInRange("📦 Enter Quantity: ", 1, 100);

                Book book = new Book(id, title, author, genre, publicationYear, quantity);
                bookService.addBook(book);
                bookStorage.saveOneBook(book);

                System.out.println("\n✨ Successfully added Book!");

                yesNo = inputHelper.readYesNo("🔄 Do you want to add another Book (Y/N): ");
                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

                yesNo = inputHelper.readYesNo("🔄 Do you want to add another Book (Y/N): ");
                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private void deleteBook() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            System.out.println("❌━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━❌");
            System.out.println("   🗑️                  DELETE BOOK                     🗑️        ");
            System.out.println("❌━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━❌");
            System.out.println("ID must start with 'BK' followed by 3 digits (e.g., BK001, BK015).");

            String id = inputHelper.readIdBook("🆔 Enter Book ID to delete: ");

            Book book = bookService.findBookById(id);

            if (book == null) {
                System.out.println("⚠️ Book not found with ID: " + id);
                yesNo = inputHelper.readYesNo("🔄 Do you want to delete another book (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }

            System.out.println("✅ Book found!");
            book.showBookInfo();
            System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");

            yesNo = inputHelper.readYesNo("🔄 Do you want to delete this book (Y/N): ");
            if (yesNo == 'Y') {
                bookService.deleteBook(id);
                bookStorage.saveAllBook(bookService.getBookList());
                System.out.println("🗑️  Book deleted successfully!");

                yesNo = inputHelper.readYesNo("🔄 Do you want to delete another book (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            } else {
                yesNo = inputHelper.readYesNo("🔄 Do you want to delete another book (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private void searchBook() {
        int choice;
        do {
            consoleHelper.clearScreen();
            System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");
            System.out.println("   🔍                  SEARCH BOOK MENU                🔍   ");
            System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");
            System.out.println("   👉  1. Search by Book Title  📖                           ");
            System.out.println("   👉  2. Search by Author Name ✒️                           ");
            System.out.println("   👉  3. Search by Book Genre  🏷️                           ");
            System.out.println("   ❌  0. Back to Main Menu                                ");
            System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");

            choice = inputHelper.readIntInRange("💻 Enter your choice: ", 0, 3);

            switch (choice) {
                case 1:
                    searchByTitle();
                    break;
                case 2:
                    searchByAuthor();
                    break;
                case 3:
                    searchByGenre();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please enter a number between 0 and 3.");
            }
        } while (choice != 0);
    }

    private void searchByTitle() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            System.out.println("📖━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📖");
            System.out.println("   🔍               SEARCH BY BOOK TITLE               🔍   ");
            System.out.println("📖━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📖");

            String title = inputHelper.readTitle("🔍 Enter Book Title to search: ");
            title = StringUtils.beautify(title);

            List<Book> bookList = bookService.findBookByTitle(title);

            if (bookList == null) {
                System.out.println("❌ No book matches the title: " + title);
                yesNo = inputHelper.readYesNo("🔄 Do you want to search another book (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }

            System.out.println("✅ Book found!");
            bookList.forEach(book -> {
                book.showBookInfo();
                System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");
            });

            yesNo = inputHelper.readYesNo("🔄 Do you want to search another book (Y/N): ");
            if (yesNo == 'Y') {
                continue;
            } else {
                break;
            }
        }
    }

    private void searchByAuthor() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            System.out.println("✒️━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━✒️");
            System.out.println("   🔍               SEARCH BY AUTHOR NAME              🔍   ");
            System.out.println("✒️━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━✒️");

            String authorName = inputHelper.readStringWord("🔍 Enter Author Name to search: ");
            authorName = StringUtils.beautify(authorName);

            List<Book> bookList = bookService.findBookByAuthor(authorName);

            if (bookList == null) {
                System.out.println("❌ No book matches the author name: " + authorName);
                yesNo = inputHelper.readYesNo("🔄 Do you want to search another book (Y/N): ");
                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }

            System.out.println("✅ Book found!");
            bookList.forEach(book -> {
                book.showBookInfo();
                System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");
            });

            yesNo = inputHelper.readYesNo("🔄 Do you want to search another book (Y/N): ");
            if (yesNo == 'Y') {
                continue;
            } else {
                break;
            }
        }
    }

    private void searchByGenre() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            System.out.println("🏷️━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━🏷️");
            System.out.println("   🔍               SEARCH BY BOOK GENRE               🔍   ");
            System.out.println("🏷️━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━🏷️");

            String genre = inputHelper.readStringWord("🔍 Enter Genre to search: ");
            genre = StringUtils.beautify(genre);

            List<Book> bookList = bookService.findBookByGenre(genre);
            if (bookList == null) {
                System.out.println("❌ No book matches the genre: " + genre);
                yesNo = inputHelper.readYesNo("🔄 Do you want to search another book (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }

            System.out.println("✅ Book found!");
            bookList.forEach(book -> {
                book.showBookInfo();
                System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");
            });

            yesNo = inputHelper.readYesNo("🔄 Do you want to search another book (Y/N): ");
            if (yesNo == 'Y') {
                continue;
            } else {
                break;
            }
        }
    }

    private void updateBook() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            System.out.println("📝━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📝");
            System.out.println("   ⚙️               UPDATE BOOK INTERFACE               ⚙️   ");
            System.out.println("📝━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📝");
            System.out.println("ID must start with 'BK' followed by 3 digits (e.g., BK001, BK015).");

            String id = inputHelper.readIdBook("👉 Enter Book ID to update: ");

            Book book = bookService.findBookById(id);

            if (book == null) {
                System.out.println("⚠️ Book not found with ID: " + id);
                yesNo = inputHelper.readYesNo("🔄 Do you want to update another book (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }

            System.out.println("✅ Book found!");
            book.showBookInfo();
            System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");
            yesNo = inputHelper.readYesNo("🔄 Do you want to update this book (Y/N): ");

            if (yesNo == 'Y') {
                supportUpdate(book);
                bookStorage.saveAllBook(bookService.getBookList());
                System.out.printf("✨ All Changes For Book Saved Successfully!\n");
                yesNo = inputHelper.readYesNo("🔄 Do you want to update another book (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            } else {
                yesNo = inputHelper.readYesNo("🔄 Do you want to update another book (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private void supportUpdate(Book book) {
        int choice;
        do {
            consoleHelper.clearScreen();
            System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");
            System.out.println("   📝                  UPDATE BOOK INFO                📝   ");
            System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");
            System.out.println("   👉  1. Update Book Title  📖                           ");
            System.out.println("   👉  2. Update Author Name ✒️                           ");
            System.out.println("   👉  3. Update Book Genre  🏷️                           ");
            System.out.println("   👉  4. Update Publication Year 📅                      ");
            System.out.println("   👉  5. Update Total Quantity 🔢 (Optional)             ");
            System.out.println("   ❌  0. Back to Main Menu                                ");
            System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");

            choice = inputHelper.readIntInRange("💻 Enter your choice to update: ", 0, 5);

            switch (choice) {
                case 1:
                    updateTitle(book);
                    break;
                case 2:
                    updateAuthor(book);
                    break;
                case 3:
                    updateGenre(book);
                    break;
                case 4:
                    updateYear(book);
                    break;
                case 5:
                    updateQuantity(book);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please enter a number between 0 and 5.");
            }

        } while (choice != 0);
    }

    private void updateTitle(Book book) {
        consoleHelper.clearScreen();
        System.out.println("📖━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📖");
        System.out.println("   ⚙️                 UPDATE BOOK TITLE                 ⚙️   ");
        System.out.println("📖━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📖");
        String oldTitle = book.getTitle();
        System.out.println("✨ Current Book Title: " + oldTitle);

        String newTitle = inputHelper.readTitle("👉 Enter new title to update: ");
        newTitle = StringUtils.beautify(newTitle);
        bookService.updateTitle(book, newTitle);
        System.out.printf("🎉 Successfully Updated Book: %s ➔ %s\n", oldTitle, newTitle);
        consoleHelper.pause();
    }

    private void updateAuthor(Book book) {
        consoleHelper.clearScreen();
        System.out.println("✒️━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━✒️");
        System.out.println("   ⚙️                 UPDATE AUTHOR NAME                ⚙️   ");
        System.out.println("✒️━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━✒️");
        String oldAuthor = book.getAuthor();
        System.out.println("✨ Current Author Name: " + oldAuthor);

        String newAuthor = inputHelper.readStringWord("👉 Enter new author name to update: ");
        newAuthor = StringUtils.beautify(newAuthor);
        bookService.updateAuthor(book, newAuthor);
        System.out.printf("🎉 Successfully Updated Book: %s ➔ %s\n", oldAuthor, newAuthor);
        consoleHelper.pause();
    }

    private void updateGenre(Book book) {
        consoleHelper.clearScreen();
        System.out.println("🏷️━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━🏷️");
        System.out.println("   ⚙️                 UPDATE BOOK GENRE                 ⚙️   ");
        System.out.println("🏷️━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━🏷️");
        String oldGenre = book.getGenre();
        System.out.println("✨ Current Book Genre: " + oldGenre);

        String newGenre = inputHelper.readStringWord("👉 Enter new genre to update: ");
        newGenre = StringUtils.beautify(newGenre);
        bookService.updateGenre(book, newGenre);
        System.out.printf("🎉 Successfully Updated Book: %s ➔ %s\n", oldGenre, newGenre);
        consoleHelper.clearScreen();
    }

    private void updateYear(Book book) {
        consoleHelper.clearScreen();
        System.out.println("📅━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📅");
        System.out.println("   ⚙️             UPDATE PUBLICATION YEAR               ⚙️   ");
        System.out.println("📅━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📅");
        int oldYear = book.getPublicationYear();
        System.out.println("✨ Current Publication Year: " + oldYear);

        int newYear = inputHelper.readIntInRange("👉 Enter new publication year to update: ",
                1440, currentYear);
        bookService.updateYear(book, newYear);
        System.out.printf("🎉 Successfully Updated Book: %s ➔ %s\n", oldYear, newYear);
        consoleHelper.pause();
    }

    private void updateQuantity(Book book) {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            System.out.println("🔢━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━🔢");
            System.out.println("   ⚙️                 UPDATE BOOK QUANTITY              ⚙️   ");
            System.out.println("🔢━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━🔢");
            int oldQuantity = book.getQuantity();
            System.out.println("📊 [CURRENT STOCK STATUS]");
            System.out.printf("   🔢 Current Total Quantity : %d\n", book.getQuantity());
            System.out.printf("   🔄 Currently Borrowed     : %d\n", book.getBorrowCount());
            System.out.println("   [ERROR] Cannot reduce total quantity below the number   ");
            System.out.println("           of currently borrowed books!                    ");
            System.out.println("──────────────────────────────────────────────────────────");

            try {
                int newQuantity = inputHelper.readIntInRange("👉 Enter new quantity to update: ",
                        book.getBorrowCount(), 100);
                bookService.updateQuantity(book, newQuantity);
                System.out.printf("🎉 Successfully Updated Book: %s ➔ %s\n", oldQuantity, newQuantity);
                consoleHelper.pause();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

                yesNo = inputHelper.readYesNo("🔄 Do you want to update another quantity (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private void displayBook() {
        consoleHelper.clearScreen();
        System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");
        System.out.println("   📋               DISPLAY ALL BOOKS                  📋   ");
        System.out.println("📚━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━📚");

        bookService.displayBook();
        consoleHelper.pause();
    }
}