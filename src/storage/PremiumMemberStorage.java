package storage;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import model.PremiumMember;
import service.MemberService;
import service.PremiumMemberService;

import java.io.*;
import java.util.List;

public class PremiumMemberStorage {
    private static final String FOLDER = "data";
    private static final String FILE_PATH = "data/premiumMember.txt";

    public void saveOnePremiumMember(PremiumMember premiumMember) {
        File folder = new File(FOLDER);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        try (
                FileWriter fileWriter = new FileWriter(FILE_PATH, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                ) {

            String dataLine = convertObjectToString(premiumMember);
            bufferedWriter.write(dataLine);
            bufferedWriter.newLine();

        } catch (IOException e) {
            System.out.println("❌ Failed to save Premium Member!");
        }
    }

    public void saveAllPremiumMember(List<PremiumMember> premiumMemberList) {
        File folder = new File(FOLDER);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        try (
                FileWriter fileWriter = new FileWriter(FILE_PATH, false);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                ) {

            for (PremiumMember premiumMember : premiumMemberList) {
                String dataLine = convertObjectToString(premiumMember);
                bufferedWriter.write(dataLine);
                bufferedWriter.newLine();
            }

        } catch (IOException e) {
            System.out.println("❌ Failed to save Premium Member!");
        }
    }

    public void loadData(PremiumMemberService premiumMemberService, MemberService memberService) {
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
                PremiumMember premiumMember = convertStringOfObject(dataLine);
                premiumMemberService.addPremiumMemberFromFile(premiumMember);
            }

        } catch (IOException e) {
            System.out.println("⚠️ Warning: Failed to load Premium Member data!");
        }
    }

    private String convertObjectToString(PremiumMember premiumMember) {
        String dataLine = premiumMember.getId() + '|' + premiumMember.getName() + '|' +
                premiumMember.getPhone() + '|' + premiumMember.getEmail() + '|' +
                premiumMember.getCurrentBorrowedCount() + '|' + premiumMember.getTotalBorrowing();

        return dataLine;
    }

    private PremiumMember convertStringOfObject(String dataLine) {
        String[] source = dataLine.split("\\|");

        String id = source[0];
        String name = source[1];
        String phone = source[2];
        String email = source[3];
        int curBorrowedCount = Integer.parseInt(source[4]);
        int totalBorrowing = Integer.parseInt(source[5]);

        PremiumMember premiumMember = new PremiumMember(id, name, phone, email);
        premiumMember.setCurrentBorrowedCount(curBorrowedCount);
        premiumMember.setTotalBorrowing(totalBorrowing);
        return premiumMember;
    }
}