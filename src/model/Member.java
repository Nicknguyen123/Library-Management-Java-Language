package model;

import utils.Validator;

import java.util.List;

public abstract class Member {
    private final String id;
    private String name;
    private String phone;
    private String email;
    private int currentBorrowedCount;
    private int totalBorrowing;

    public Member(String id, String name, String phone, String email) {
        this.id = Validator.validateBasicString(id);
        setName(name);
        setPhone(phone);
        setEmail(email);
        this.currentBorrowedCount = 0;
        this.totalBorrowing = 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = Validator.validateBasicString(name);
    }

    public String getPhone() {
        return phone;
    }

    public final void setPhone(String phone) {
        this.phone = Validator.validateBasicString(phone);
    }

    public String getEmail() {
        return email;
    }

    public final void setEmail(String email) {
        this.email = Validator.validateBasicEmail(email);
    }

    public int getCurrentBorrowedCount() {
        return currentBorrowedCount;
    }

    public final void setCurrentBorrowedCount(int currentBorrowedCount) {
        this.currentBorrowedCount = Validator.validateNumber(currentBorrowedCount);
    }

    public int getTotalBorrowing() {
        return totalBorrowing;
    }

    public void setTotalBorrowing(int totalBorrowing) {
        this.totalBorrowing = Validator.validateNumber(totalBorrowing);
    }

    public abstract void showMemberType();

    public abstract void showMemberInfo();

    public abstract int getLimitBorrow();
    public abstract long getFineRate();

    public abstract long calculateFine(long lateDay);

}