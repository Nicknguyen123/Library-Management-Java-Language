package storage;

import model.RegularMember;
import service.MemberService;
import service.RegularMemberService;

import java.io.*;
import java.util.List;
import java.util.function.Consumer;

public class RegularMemberStorage {
    private static final String FOLDER = "data";
    private static final String FILE_PATH = "data/regularMember.txt";

    public void saveOneRegularMember(RegularMember regularMember) {
        File folder = new File(FOLDER);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        try (
                FileWriter fileWriter = new FileWriter(FILE_PATH, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                ) {
            String dataLine = convertObjectToString(regularMember);
            bufferedWriter.write(dataLine);
            bufferedWriter.newLine();

        } catch (IOException e) {
            System.out.println("❌ Failed to save Regular Member!");
        }
    }

    public void saveAllRegularMember(List<RegularMember> regularMemberList) {
        File folder = new File(FOLDER);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        try (
                FileWriter fileWriter = new FileWriter(FILE_PATH, false);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                ) {

            for (RegularMember regularMember : regularMemberList) {
                String dataLine = convertObjectToString(regularMember);
                bufferedWriter.write(dataLine);
                bufferedWriter.newLine();
            }

        } catch (IOException e) {
            System.out.println("❌ Failed to save Regular Member!");
        }
    }

    public void loadData(RegularMemberService regularMemberService, MemberService memberService) {
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
                RegularMember regularMember = convertStringToObject(dataLine);
                regularMemberService.addRegularMemberFromFile(regularMember);
            }

        } catch (IOException e) {
            System.out.println("⚠️ Warning: Failed to load Regular Member data!");
        }
    }

    private String convertObjectToString(RegularMember regularMember) {
        String dataLine = regularMember.getId() + '|' + regularMember.getName() + '|' +
                regularMember.getPhone() + '|' + regularMember.getEmail() + '|' +
                regularMember.getCurrentBorrowedCount() + '|' + regularMember.getTotalBorrowing();

        return dataLine;
    }

    private RegularMember convertStringToObject(String string) {
        String[] source = string.split("\\|");

        String id = source[0];
        String name = source[1];
        String phone = source[2];
        String email = source[3];
        int curBorrowedCount = Integer.parseInt(source[4]);
        int totalBorrowing = Integer.parseInt(source[5]);

        RegularMember regularMember = new RegularMember(id, name, phone, email);
        regularMember.setCurrentBorrowedCount(curBorrowedCount);
        regularMember.setTotalBorrowing(totalBorrowing);

        return regularMember;
    }
}