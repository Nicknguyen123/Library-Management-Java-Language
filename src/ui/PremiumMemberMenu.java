package ui;

import model.Member;
import model.PremiumMember;
import service.MemberService;
import service.PremiumMemberService;
import storage.PremiumMemberStorage;
import utils.ConsoleHelper;
import utils.InputHelper;
import utils.StringUtils;

import java.util.List;

public class PremiumMemberMenu {
    private PremiumMemberService premiumMemberService;
    private ConsoleHelper consoleHelper;
    private InputHelper inputHelper;

    public PremiumMemberMenu(PremiumMemberService premiumMemberService,
                             ConsoleHelper consoleHelper, InputHelper inputHelper) {
        this.premiumMemberService = premiumMemberService;
        this.consoleHelper = consoleHelper;
        this.inputHelper = inputHelper;
    }

    public void showPremiumMemberMenu() {
        int choice;
        do {
            consoleHelper.clearScreen();
            System.out.println("👑━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👑");
            System.out.println("   🔥           PREMIUM MEMBER MANAGEMENT              🔥        ");
            System.out.println("👑━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👑");
            System.out.println("   1. ➕ Add Premium Member");
            System.out.println("   2. ❌ Delete Premium Member");
            System.out.println("   3. 📝 Update Premium Member");
            System.out.println("   4. 📋 Display All Members");
            System.out.println("   5. 🔍 Search Premium Member");
            System.out.println("   0. 🔙 Back to Member Menu");
            System.out.println("👑━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👑");

            choice = inputHelper.readIntInRange("💎 Enter your choice: ", 0, 5);

            switch (choice) {
                case 1:
                    addPremiumMember();
                    break;
                case 2:
                    deletePremiumMember();
                    break;
                case 3:
                    updatePremiumMember();
                    break;
                case 4:
                    displayPremiumMember();
                    break;
                case 5:
                    searchPremiumMember();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please enter a number between 0 and 4.");
            }
        } while (choice != 0);
    }

    private void addPremiumMember() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            System.out.println("💎━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━💎");
            System.out.println("   ✨               ADD PREMIUM MEMBER                 ✨   ");
            System.out.println("💎━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━💎");
            System.out.println("📌 Please enter the following details:");
            System.out.println("──────────────────────────────────────────────────────────");
            System.out.println("ID must start with 'PRE' followed by 3 digits (e.g., PRE001, PRE015).");

            try {
                String id = inputHelper.readIdPremiumMember("💳 Enter Premium Member ID: ");
                String name = inputHelper.readStringWord("👑 Enter Premium Member Name: ");
                name = StringUtils.beautify(name);
                String phone = inputHelper.readPhoneNumber("📞 Enter Phone Number: ");
                String email = inputHelper.readEmail("📧 Enter Email: ");

                PremiumMember premiumMember = new PremiumMember(id, name, phone, email);
                premiumMemberService.addPremiumMember(premiumMember);

                System.out.println("\n✨ Successfully added Premium Member!");

                yesNo = inputHelper.readYesNo("🔄 Do you want to add another Premium Member (Y/N): ");
                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

                yesNo = inputHelper.readYesNo("🔄 Do you want to add another Premium Member (Y/N): ");
                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }
        }
    }

    private void deletePremiumMember() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            System.out.println("❌━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━❌");
            System.out.println("   🗑️             DELETE PREMIUM MEMBER                🗑️   ");
            System.out.println("❌━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━❌");
            System.out.println("ID must start with 'PRE' followed by 3 digits (e.g., PRE001, PRE015).");

            String id = inputHelper.readIdPremiumMember("🆔 Enter Member ID to delete: ");

            PremiumMember premiumMember = premiumMemberService.findMemberById(id);

            if (premiumMember == null) {
                System.out.println("⚠️ Premium Member not found with ID: " + id);
                yesNo = inputHelper.readYesNo("🔄 Do you want to delete another member (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }

            System.out.println("✅ Member found!");
            premiumMember.showMemberInfo();
            System.out.println("👑━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            yesNo = inputHelper.readYesNo("🔄 Do you want to delete this member (Y/N): ");

            if (yesNo == 'Y') {
                premiumMemberService.deletePremiumMember(id);
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

    private void searchPremiumMember() {
        int choice;
        do {
            consoleHelper.clearScreen();
            System.out.println("👑━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👑");
            System.out.println("   ⚡             SEARCH PREMIUM MEMBER                 ⚡        ");
            System.out.println("👑━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👑");
            System.out.println("   1. 🔑 Search by ID");
            System.out.println("   2. 🏷️ Search by Name");
            System.out.println("   0. ↩️ Back to Premium Menu");
            System.out.println("👑━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👑");

            choice = inputHelper.readIntInRange("💎 Enter your choice: ", 0, 2);

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
            System.out.println("👑━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👑");
            System.out.println("   🔑               SEARCH BY MEMBER ID                 🔑        ");
            System.out.println("👑━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👑");
            System.out.println("ID must start with 'PRE' followed by 3 digits (e.g., PRE001, PRE015).");

            String id = inputHelper.readIdPremiumMember("👉 Enter Premium Member ID to search: ");

            PremiumMember premiumMember = premiumMemberService.findMemberById(id);

            if (premiumMember == null) {
                System.out.println("⚠️ Premium Member not found with ID: " + id);
                yesNo = inputHelper.readYesNo("🔄 Do you want to search another member (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }

            System.out.println("✅ Member found!");
            premiumMember.showMemberInfo();
            System.out.println("👑━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
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
            System.out.println("👑━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👑");
            System.out.println("   🏷️              SEARCH BY MEMBER NAME                🏷️        ");
            System.out.println("👑━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━👑");

            String name = inputHelper.readStringWord("👉 Enter Premium Member Name to search: ");
            name = StringUtils.beautify(name);

            List<PremiumMember> premiumMemberList = premiumMemberService.findMemberByName(name);
            if (premiumMemberList == null) {
                System.out.println("❌ No premium member matches the name: " + name);
                yesNo = inputHelper.readYesNo("🔄 Do you want to search another member (Y/N): ");
                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }

            System.out.println("✅ Member found!");
            for (PremiumMember premiumMember : premiumMemberList) {
                premiumMember.showMemberInfo();
                System.out.println("👑━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            }

            yesNo = inputHelper.readYesNo("🔄 Do you want to search another member (Y/N): ");

            if (yesNo == 'Y') {
                continue;
            } else {
                break;
            }
        }
    }

    private void updatePremiumMember() {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            System.out.println("💎━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━💎");
            System.out.println("   ⚡              UPDATE PREMIUM MEMBER               ⚡        ");
            System.out.println("💎━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━💎");
            System.out.println("ID must start with 'PRE' followed by 3 digits (e.g., PRE001, PRE015).");
            String id = inputHelper.readIdPremiumMember("👉 Enter Premium Member ID to update: ");

            PremiumMember premiumMember = premiumMemberService.findMemberById(id);

            if (premiumMember == null) {
                System.out.println("⚠️ Premium Member not found with ID: " + id);
                yesNo = inputHelper.readYesNo("🔄 Do you want to update another member (Y/N): ");

                if (yesNo == 'Y') {
                    continue;
                } else {
                    break;
                }
            }

            System.out.println("✅ Member found!");
            premiumMember.showMemberInfo();
            System.out.println("👑━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            yesNo = inputHelper.readYesNo("🔄 Do you want to update this member (Y/N): ");

            if (yesNo == 'Y') {
                supportUpdate(premiumMember);
                System.out.printf("✨ All Changes For Premium Member Saved Successfully!\n");

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

    private void supportUpdate(PremiumMember premiumMember) {
        int choice;
        do {
            consoleHelper.clearScreen();
            System.out.println("💎━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━💎");
            System.out.println("   ⚡              UPDATE PREMIUM MEMBER               ⚡        ");
            System.out.println("💎━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━💎");
            System.out.println("   1. 👤 Update Member Name                              ");
            System.out.println("   2. 📱 Update Phone Number                             ");
            System.out.println("   3. 📧 Update Email Address                            ");
            System.out.println("   0. 🚪 Back to Premium Menu                               ");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            choice = inputHelper.readIntInRange("👉 Enter your choice: ", 0, 3);

            switch (choice) {
                case 1:
                    updateName(premiumMember);
                    break;
                case 2:
                    updatePhoneNumber(premiumMember);
                    break;
                case 3:
                    updateEmail(premiumMember);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please enter a number between 0 and 3.");
            }
        } while (choice != 0);
    }

    private void updateName(PremiumMember premiumMember) {
            consoleHelper.clearScreen();
            System.out.println("💎━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━💎");
            System.out.println("   👤                UPDATE MEMBER NAME                👤        ");
            System.out.println("💎━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━💎");
            String oldName = premiumMember.getName();
            System.out.println("✨ Current Premium Member Name: " + oldName);

            String newName = inputHelper.readStringWord("👉 Enter new name to update: ");
            newName = StringUtils.beautify(newName);
            premiumMemberService.updateName(premiumMember, newName);
            System.out.printf("🎉 Successfully Updated Premium Member: %s ➔ %s\n", oldName, newName);
            consoleHelper.pause();
    }

    private void updatePhoneNumber(PremiumMember premiumMember) {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            System.out.println("💎━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━💎");
            System.out.println("   📱               UPDATE PHONE NUMBER                📱        ");
            System.out.println("💎━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━💎");
            String oldPhone = premiumMember.getPhone();
            System.out.println("✨ Current Phone Number: " + oldPhone);

            try {
                String newPhone = inputHelper.readPhoneNumber("👉 Enter new phone number to update: ");
                premiumMemberService.updatePhone(premiumMember, newPhone);
                System.out.printf("🎉 Successfully Updated Premium Member: %s ➔ %s\n", oldPhone, newPhone);
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

    private void updateEmail(PremiumMember premiumMember) {
        char yesNo;
        while (true) {
            consoleHelper.clearScreen();
            System.out.println("💎━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━💎");
            System.out.println("   📧                  UPDATE MEMBER EMAIL                 📧        ");
            System.out.println("💎━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━💎");
            String oldEmail = premiumMember.getEmail();
            System.out.println("✨ Current Premium Member Email: " + oldEmail);

            try {
                String newEmail = inputHelper.readEmail("📝 Enter New Email Address to update: ");
                premiumMemberService.updateEmail(premiumMember, newEmail);
                System.out.printf("🎉 Successfully Updated Premium Member: %s ➔ %s\n", oldEmail, newEmail);
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

    private void displayPremiumMember() {
        consoleHelper.clearScreen();
        System.out.println("💎━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━💎");
        System.out.println("   📋            DISPLAY PREMIUM MEMBERS               📋   ");
        System.out.println("💎━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━💎");

        premiumMemberService.displayPremiumMember();
        consoleHelper.pause();
    }
}