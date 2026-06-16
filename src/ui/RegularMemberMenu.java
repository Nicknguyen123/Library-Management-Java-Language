package ui;

import model.RegularMember;
import service.MemberService;
import service.RegularMemberService;
import storage.RegularMemberStorage;
import utils.ConsoleHelper;
import utils.InputHelper;
import utils.StringUtils;

import java.util.List;
import java.util.function.Consumer;

public class RegularMemberMenu {
    private RegularMemberService regularMemberService;
    private ConsoleHelper consoleHelper;
    private InputHelper inputHelper;

    public RegularMemberMenu(RegularMemberService regularMemberService, ConsoleHelper consoleHelper,
                             InputHelper inputHelper) {
        this.regularMemberService = regularMemberService;
        this.consoleHelper = consoleHelper;
        this.inputHelper = inputHelper;
    }

    public void showRegularMemberMenu() {
        int choice;
        do {
            consoleHelper.clearScreen();
            System.out.println("⚡━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━⚡");
            System.out.println("   🌌          REGULAR MEMBER MANAGEMENT           🌌   ");
            System.out.println("⚡━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━⚡");
            System.out.println("   1. 🚀 Add Regular Member");
            System.out.println("   2. 💥 Delete Regular Member");
            System.out.println("   3. 🛠️ Update Regular Member");
            System.out.println("   4. 📋 Display All Members");
            System.out.println("   5. 🎯 Search Regular Member");
            System.out.println("   0. ↩️ Back to Member Menu");
            System.out.println("⚡━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━⚡");

            choice = inputHelper.readIntInRange("🔮 Enter your choice: ", 0, 5);

            switch (choice) {
                case 1:
                    addRegularMember();
                    break;
                case 2:
                    deleteRegularMember();
                    break;
                case 3:
                    updateRegularMember();
                    break;
                case 4:
                    displayRegularMember();
                    break;
                case 5:
                    searchRegularMember();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please enter a number between 0 and 4.");
            }
        } while (choice != 0);
    }

    private void addRegularMember() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            System.out.println("👤━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👤");
            System.out.println("   🪪                ADD REGULAR MEMBER                 🪪        ");
            System.out.println("👤━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👤");
            System.out.println("📌 Please enter the following details:");
            System.out.println("──────────────────────────────────────────────────────────");
            System.out.println("ID must start with 'REG' followed by 3 digits (e.g., REG001, REG015).");

            try {
                String id = inputHelper.readIdRegularMember("🪪 Enter Regular Member ID: ");
                String name = inputHelper.readStringWord("📝 Enter Regular Member Name: ");
                name = StringUtils.beautify(name);
                String phone = inputHelper.readPhoneNumber("📞 Enter Phone Number: ");
                String email = inputHelper.readEmail("📧 Enter Email: ");

                RegularMember regularMember = new RegularMember(id, name, phone, email);
                regularMemberService.addRegularMember(regularMember);

                System.out.println("\n✨ Successfully added Regular Member!");

                yesNo = inputHelper.readYesNo("🔄 Do you want to add another Regular Member (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

                yesNo = inputHelper.readYesNo("🔄 Do you want to add another Regular Member (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private void deleteRegularMember() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            System.out.println("🚫━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━🚫");
            System.out.println("   🗑️             DELETE REGULAR MEMBER                🗑️   ");
            System.out.println("🚫━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━🚫");
            System.out.println("ID must start with 'REG' followed by 3 digits (e.g., REG001, REG015).");

            String id = inputHelper.readIdRegularMember("🆔 Enter Member ID to delete: ");

            RegularMember regularMember = regularMemberService.findMemberById(id);

            if (regularMember == null) {
                System.out.println("⚠️ Regular Member not found with ID: " + id);
                yesNo = inputHelper.readYesNo("🔄 Do you want to delete another member (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }

            System.out.println("✅ Member found!");
            regularMember.showMemberInfo();
            System.out.println("👑━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            yesNo = inputHelper.readYesNo("🔄 Do you want to delete this member (Y/N): ");

            if (yesNo == 'Y') {
                regularMemberService.deleteRegularMember(id);
                System.out.println("🗑️  Member deleted successfully!");

                yesNo = inputHelper.readYesNo("🔄 Do you want to delete another member (Y/N): ");
                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            } else {
                yesNo = inputHelper.readYesNo("🔄 Do you want to delete another member (Y/N): ");
                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private void searchRegularMember() {
        int choice;
        do {
            consoleHelper.clearScreen();
            System.out.println("👤━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👤");
            System.out.println("   🔍             SEARCH REGULAR MEMBER                 🔍        ");
            System.out.println("👤━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👤");
            System.out.println("   1. 🪪 Search by ID");
            System.out.println("   2. 📛 Search by Name");
            System.out.println("   0. 🚪 Back to Regular Menu");
            System.out.println("👤━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👤");

            choice = inputHelper.readIntInRange("👉 Enter your choice: ", 0, 2);

            switch (choice) {
                case 1:
                    searchById();
                    break;
                case 2:
                    searchByName();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please enter a number between 0 and 2.");
            }

        } while (choice != 0);
    }

    private void searchById() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            System.out.println("👤━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👤");
            System.out.println("   🔍               SEARCH BY MEMBER ID                 🔍        ");
            System.out.println("👤━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👤");
            System.out.println("ID must start with 'REG' followed by 3 digits (e.g., REG001, REG015).");

            String id = inputHelper.readIdRegularMember("👉 Enter Regular Member ID to search: ");

            RegularMember regularMember = regularMemberService.findMemberById(id);

            if (regularMember == null) {
                System.out.println("⚠️ Regular Member not found with ID: " + id);

                yesNo = inputHelper.readYesNo("🔄 Do you want to search another member (Y/N): ");
                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }

            System.out.println("✅ Member found!");
            regularMember.showMemberInfo();
            System.out.println("👤━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            yesNo = inputHelper.readYesNo("🔄 Do you want to search another member (Y/N): ");

            if (yesNo == 'Y') {
                continue;
            } else {
                break;
            }
        }
    }

    private void searchByName() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            System.out.println("👤━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👤");
            System.out.println("   📛              SEARCH BY MEMBER NAME                📛        ");
            System.out.println("👤━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👤");

            String name = inputHelper.readStringWord("👉 Enter Premium Member Name to search: ");
            name = StringUtils.beautify(name);

            List<RegularMember> regularMemberList = regularMemberService.findMemberByName(name);

            if (regularMemberList == null) {
                System.out.println("❌ No premium member matches the name: " + name);

                yesNo = inputHelper.readYesNo("🔄 Do you want to search another member (Y/N): ");
                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }

            System.out.println("✅ Member found!");
            regularMemberList.forEach(regularMember -> {
                regularMember.showMemberInfo();
                System.out.println("👤━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            });

            yesNo = inputHelper.readYesNo("🔄 Do you want to search another member (Y/N): ");
            if (yesNo == 'Y') {
                continue;
            } else {
                break;
            }
        }
    }

    private void updateRegularMember() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            System.out.println("👤━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👤");
            System.out.println("   📝              UPDATE REGULAR MEMBER               📝        ");
            System.out.println("👤━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👤");
            System.out.println("ID must start with 'REG' followed by 3 digits (e.g., REG001, REG015).");
            String id = inputHelper.readIdRegularMember("👉 Enter Regular Member ID to update: ");

            RegularMember regularMember = regularMemberService.findMemberById(id);
            if (regularMember == null) {
                System.out.println("⚠️ Regular Member not found with ID: " + id);
                yesNo = inputHelper.readYesNo("🔄 Do you want to update another member (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }

            System.out.println("✅ Member found!");
            regularMember.showMemberInfo();
            System.out.println("👤━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            yesNo = inputHelper.readYesNo("🔄 Do you want to update this member (Y/N): ");

            if (yesNo == 'Y') {
                supportUpdate(regularMember);
                System.out.printf("✨ All Changes For Regular Member Saved Successfully!\n");

                yesNo = inputHelper.readYesNo("🔄 Do you want to update another member (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            } else {
                yesNo = inputHelper.readYesNo("🔄 Do you want to update another member (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private void supportUpdate(RegularMember regularMember) {
        int choice;
        do {
            consoleHelper.clearScreen();
            System.out.println("👤━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👤");
            System.out.println("   📝              UPDATE REGULAR MEMBER               📝        ");
            System.out.println("👤━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👤");
            System.out.println("   1. 👤 Update Member Name                              ");
            System.out.println("   2. 📱 Update Phone Number                             ");
            System.out.println("   3. 📧 Update Email Address                            ");
            System.out.println("   0. ↩️ Back to Regular Menu                            ");
            System.out.println("👤━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👤");

            choice = inputHelper.readIntInRange("👉 Enter your choice: ", 0, 3);

            switch (choice) {
                case 1:
                    updateName(regularMember);
                    break;
                case 2:
                    updatePhone(regularMember);
                    break;
                case 3:
                    updateEmail(regularMember);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please enter a number between 0 and 3.");
            }
        } while (choice != 0);
    }

    private void updateName(RegularMember regularMember) {
        consoleHelper.clearScreen();
        System.out.println("👤━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👤");
        System.out.println("   📝                UPDATE MEMBER NAME                📝        ");
        System.out.println("👤━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👤");
        String oldName = regularMember.getName();
        System.out.println("✨ Current Regular Member Name: " + oldName);

        String newName = inputHelper.readStringWord("👉 Enter new name to update: ");
        newName = StringUtils.beautify(newName);
        regularMemberService.updateName(regularMember, newName);
        System.out.printf("🎉 Successfully Updated Regular Member: %s ➔ %s\n", oldName, newName);
        consoleHelper.pause();
    }

    private void updatePhone(RegularMember regularMember) {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            System.out.println("👤━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👤");
            System.out.println("   📱               UPDATE PHONE NUMBER                📱        ");
            System.out.println("👤━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👤");
            String oldPhone = regularMember.getPhone();
            System.out.println("✨ Current Phone Number: " + oldPhone);

            try {
                String newPhone = inputHelper.readPhoneNumber("👉 Enter new phone number to update: ");
                regularMemberService.updatePhone(regularMember, newPhone);
                System.out.printf("🎉 Successfully Updated Regular Member: %s ➔ %s\n", oldPhone, newPhone);
                consoleHelper.pause();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

                yesNo = inputHelper.readYesNo("🔄 Do you want to update another phone number (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private void updateEmail(RegularMember regularMember) {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            System.out.println("👤━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👤");
            System.out.println("   📧                  UPDATE MEMBER EMAIL                 📧        ");
            System.out.println("👤━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👤");
            String oldEmail = regularMember.getEmail();
            System.out.println("✨ Current Regular Member Email: " + oldEmail);

            try {
                String newEmail = inputHelper.readEmail("📝 Enter New Email Address to update: ");
                regularMemberService.updateEmail(regularMember, newEmail);
                System.out.printf("🎉 Successfully Updated Regular Member: %s ➔ %s\n", oldEmail, newEmail);
                consoleHelper.pause();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

                yesNo = inputHelper.readYesNo("🔄 Do you want to update another email (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private void displayRegularMember() {
        consoleHelper.clearScreen();
        System.out.println("👤━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👤");
        System.out.println("   📋            DISPLAY REGULAR MEMBERS               📋   ");
        System.out.println("👤━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👤");

        regularMemberService.displayRegularMember();
        consoleHelper.pause();
    }
}