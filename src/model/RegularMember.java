package model;

public class RegularMember extends Member {
    private final int limitBorrow;
    private final long fineRate;

    public RegularMember(String id, String name, String phone, String email) {
        super(id, name, phone, email);
        this.limitBorrow = 1;
        this.fineRate = 5000;
    }

    @Override
    public void showMemberType() {
        System.out.println("👤 Status: Regular Member");
    }

    @Override
    public void showMemberInfo() {
        showMemberType();
        System.out.println("🪪 Id: " + super.getId());
        System.out.println("👤 Name: " + super.getName());
        System.out.println("📞 Phone: " + super.getPhone());
        System.out.println("📧 Email: " + super.getEmail());
        System.out.println("📚 Current Borrowed Book: " + super.getCurrentBorrowedCount());
        System.out.println("📦 Total Borrowing Records: " + super.getTotalBorrowing());
        System.out.println("🚫 Limit Borrow: " + limitBorrow);
        System.out.println("📈 FineRate: " + fineRate);
    }

    @Override
    public int getLimitBorrow() {
        return limitBorrow;
    }

    @Override
    public long getFineRate() {
        return fineRate;
    }

    @Override
    public long calculateFine(long lateDay) {
        return fineRate * lateDay;
    }
}