package service;

import model.PremiumMember;
import storage.PremiumMemberStorage;
import utils.Validator;

import java.util.ArrayList;
import java.util.List;

public class PremiumMemberService {
    private final List<PremiumMember> premiumMemberList;
    private MemberService memberService;
    private PremiumMemberStorage premiumMemberStorage;

    public PremiumMemberService(MemberService memberService, PremiumMemberStorage premiumMemberStorage) {
        this.premiumMemberList = new ArrayList<>();
        this.memberService = memberService;
        this.premiumMemberStorage = premiumMemberStorage;
    }

    public List<PremiumMember> getPremiumList() {
        return new ArrayList<>(premiumMemberList);
    }

    public void addPremiumMember(PremiumMember premiumMember) {
        memberService.checkMemberNull(premiumMember);

        PremiumMember member = findMemberById(premiumMember.getId());
        if (member != null) {
            throw new IllegalArgumentException("❌ This Premium Member already exists in the system. " +
                    "Duplicate ID: " + premiumMember.getId());
        }

        if (memberService.checkDuplicatePhone(premiumMember.getPhone())) {
            throw new IllegalArgumentException("❌ Phone number already registered: " + premiumMember.getPhone());
        }

        if (memberService.checkDuplicateEmail(premiumMember.getEmail())) {
            throw new IllegalArgumentException("❌ Email already registered: " + premiumMember.getEmail());
        }

        premiumMemberList.add(premiumMember);
        memberService.addMember(premiumMember);
        premiumMemberStorage.saveOnePremiumMember(premiumMember);
    }

    public void addPremiumMemberFromFile(PremiumMember premiumMember) {
        premiumMemberList.add(premiumMember);
        memberService.addMember(premiumMember);
    }

    public void deletePremiumMember(String id) {
        String safeId = Validator.validateBasicString(id);

        for (int i = 0; i < premiumMemberList.size(); i++) {
            if (premiumMemberList.get(i).getId().equals(safeId)) {
                premiumMemberList.remove(i);
                memberService.deleteMember(safeId);
                premiumMemberStorage.saveAllPremiumMember(premiumMemberList);
                return;
            }
        }

        throw new IllegalArgumentException("⚠️ Alert: Unable to remove. No member found with the specified ID: "
                + safeId);
    }

    public void displayPremiumMember() {
        if (premiumMemberList.isEmpty()) {
            System.out.println("⚠️ No premium members found in the system.");
            return;
        }

        for (PremiumMember member : premiumMemberList) {
            member.showMemberInfo();
            System.out.println("👑━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        }
    }

    public void updateName(PremiumMember premiumMember, String newName) {
        memberService.checkMemberNull(premiumMember);

        String safeName = Validator.validateBasicString(newName);

        premiumMember.setName(safeName);
        premiumMemberStorage.saveAllPremiumMember(premiumMemberList);
    }

    public void updatePhone(PremiumMember premiumMember, String newPhone) {
        memberService.checkMemberNull(premiumMember);

        String safePhone = Validator.validateBasicString(newPhone);

        if (memberService.checkDuplicatePhone(newPhone)) {
            throw new IllegalArgumentException("❌ Phone number already registered: " + safePhone);
        }

        premiumMember.setPhone(newPhone);
        premiumMemberStorage.saveAllPremiumMember(premiumMemberList);
    }

    public void updateEmail(PremiumMember premiumMember, String newEmail) {
        memberService.checkMemberNull(premiumMember);

        String safeEmail = Validator.validateBasicEmail(newEmail);

        if (memberService.checkDuplicateEmail(newEmail)) {
            throw new IllegalArgumentException("❌ Email already registered: " + safeEmail);
        }

        premiumMember.setEmail(safeEmail);
        premiumMemberStorage.saveAllPremiumMember(premiumMemberList);
    }

    public PremiumMember findMemberById(String id) {
        String safeId = Validator.validateBasicString(id);

        for (PremiumMember premiumMember : premiumMemberList) {
            if (premiumMember.getId().equals(safeId)) {
                return premiumMember;
            }
        }

        return null;
    }

    public List<PremiumMember> findMemberByName(String name) {
        String safeName = Validator.validateBasicString(name);

        List<PremiumMember> source = new ArrayList<>();

        for (PremiumMember member : premiumMemberList) {
            if (member.getName().contains(safeName)) {
                source.add(member);
            }
        }

        if (source.isEmpty()) {
            return null;
        } else {
            return source;
        }
    }
}