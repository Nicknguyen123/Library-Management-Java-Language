package service;

import model.PremiumMember;
import model.RegularMember;
import utils.Validator;

import java.util.ArrayList;
import java.util.List;

public class RegularMemberService {
    private List<RegularMember> regularMemberList;
    private MemberService memberService;

    public RegularMemberService(MemberService memberService) {
        this.regularMemberList = new ArrayList<>();
        this.memberService = memberService;
    }

    public List<RegularMember> getRegularMemberList() {
        return new ArrayList<>(regularMemberList);
    }

    public void addRegularMember(RegularMember regularMember) {
        memberService.checkMemberNull(regularMember);

        RegularMember member = findMemberById(regularMember.getId());
        if (member != null) {
            throw new IllegalArgumentException("❌ This Regular Member already exists in the system. " +
                    "Duplicate ID: " + regularMember.getId());
        }

        if (memberService.checkDuplicatePhone(regularMember.getPhone())) {
            throw new IllegalArgumentException("❌ Phone number already registered: " + regularMember.getPhone());
        }

        if (memberService.checkDuplicateEmail(regularMember.getEmail())) {
            throw new IllegalArgumentException("❌ Email already registered: " + regularMember.getEmail());
        }

        regularMemberList.add(regularMember);
    }

    public void deleteRegularMember(String id) {
        String safeId = Validator.validateBasicString(id);

        for (int i = 0; i < regularMemberList.size(); i++) {
            if (regularMemberList.get(i).getId().equals(safeId)) {
                regularMemberList.remove(i);
                return;
            }
        }

        throw new IllegalArgumentException("⚠️ Alert: Unable to remove. No member found with the specified ID: "
                + safeId);
    }

    public void displayRegularMember() {
        if (regularMemberList.isEmpty()) {
            System.out.println("⚠️ No regular members found in the system.");
            return;
        }

        for (RegularMember member : regularMemberList) {
            member.showMemberInfo();
            System.out.println("👤━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        }
    }

    public void updateName(RegularMember regularMember, String newName) {
        memberService.checkMemberNull(regularMember);

        String safeName = Validator.validateBasicString(newName);

        regularMember.setName(safeName);
    }

    public void updatePhone(RegularMember regularMember, String newPhone) {
        memberService.checkMemberNull(regularMember);

        String safePhone = Validator.validateBasicString(newPhone);

        if (memberService.checkDuplicatePhone(newPhone)) {
            throw new IllegalArgumentException("❌ Phone number already registered: " + regularMember.getPhone());
        }

        regularMember.setPhone(newPhone);
    }

    public void updateEmail(RegularMember regularMember, String newEmail) {
        memberService.checkMemberNull(regularMember);

        String safeEmail = Validator.validateBasicEmail(newEmail);

        if (memberService.checkDuplicateEmail(newEmail)) {
            throw new IllegalArgumentException("❌ Email already registered: " + regularMember.getEmail());
        }

        regularMember.setEmail(safeEmail);
    }

    public RegularMember findMemberById(String id) {
        String safeId = Validator.validateBasicString(id);

        for (RegularMember regularMember : regularMemberList) {
            if (regularMember.getId().equals(safeId)) {
                return regularMember;
            }
        }

        return null;
    }

    public List<RegularMember> findMemberByName(String name) {
        String safeName = Validator.validateBasicString(name);

        List<RegularMember> source = new ArrayList<>();

        for (RegularMember member : regularMemberList) {
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