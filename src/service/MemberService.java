package service;

import model.Member;
import utils.Validator;

import java.util.ArrayList;
import java.util.List;

public class MemberService {
    private List<Member> memberList;

    public MemberService() {
        this.memberList = new ArrayList<>();
    }

    public List<Member> getMemberList() {
        return new ArrayList<>(memberList);
    }

    public void addMember(Member member) {
        memberList.add(member);
    }

    public void deleteMember(String id) {
        for (int i = 0; i < memberList.size(); i++) {
            if (memberList.get(i).getId().equals(id)) {
                memberList.remove(i);
                return;
            }
        }

        throw new IllegalArgumentException("⚠️ Alert: Unable to remove. No member found with the specified ID: "
                + id);
    }

    public Member findMemberById(String id) {
        String safeId = Validator.validateBasicString(id);

        for (Member member : memberList) {
            if (member.getId().equals(safeId)) {
                return member;
            }
        }

        return null;
    }

    public void checkMemberNull(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("❌ Member cannot be null");
        }
    }

    public boolean checkDuplicatePhone(String phone) {
        String safePhone = Validator.validateBasicString(phone);

        for (Member member : memberList) {
            if (member.getPhone().equals(safePhone)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkDuplicateEmail(String email) {
        String safeEmail = Validator.validateBasicString(email);

        for (Member member : memberList) {
            if (member.getEmail().equals(safeEmail)) {
                return true;
            }
        }

        return false;
    }
}