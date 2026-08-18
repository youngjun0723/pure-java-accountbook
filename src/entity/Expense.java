package entity;

import java.time.LocalDate;

// Alt + Insert: 생성자 || Getter & Setter 등 자동생성

public class Expense {

    // 데이터 필드(private로 외부 접근 차단)
    private Long id; // 지출을 구별할 고유 번호
    private LocalDate date; // 지출한 날짜
    private String category;
    private long amount; // 쓴 돈
    private String memo; // 메모

    // 기본 생성자
    public Expense() {
    }

    // 모든 데이터를 받아서 지출 객체를 생성하는 생성자
    public Expense(Long id, LocalDate date, String category, long amount, String memo) {
        this.id = id;
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.memo = memo;
    }

    // Getter (private 필드의 값을 읽어서 외부로 꺼내주는 창구)
    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public long getAmount() {
        return amount;
    }

    public String getMemo() {
        return memo;
    }

    // Setter (설정(set) 역할)
    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmount(long amount) {
        if(amount < 0) {
            throw new IllegalArgumentException("지출 금액은 0원 이상이어야 합니다.");
        }

        this.amount = amount;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    // Console에 지출 내역을 깔끔하게 출력하기 위한 toString() 메서드
    @Override
    public String toString() {
        return String.format("[%d] %s | %-6s | %,d원 | %s", id, date, category, amount, memo);
    }
}
