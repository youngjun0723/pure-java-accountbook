package service;

import entity.Expense;
import repository.ExpenseRepository;

import java.time.LocalDate;
import java.util.List;

public class ExpenseService {
    private final ExpenseRepository repository; // 주입받기 위해 final로 선언

    public ExpenseService(ExpenseRepository repository) { // 생성자를 통해 외부에서 저장소를 건네받음
        this.repository = repository;
    }

    /*
    * 로직1: 지출 내역 등록
    * 카테고리 유효성 검사 후 저장소에 저장
    * 카테고리:(지출한 날짜, 카테고리, 쓴 돈, 메모)
    */
    public Expense addExpense(LocalDate date, String category, long amount, String memo) {
        if(category==null || category.trim().isEmpty()){
            throw new IllegalArgumentException("카테고리는 필수 입력 항목입니다.");
        }

        Expense expense = new Expense(null, date, category, amount, memo);
        return repository.save(expense);
    }

    /*
    * 로직2: 전체 지출 내역 조회
    * */
    public List<Expense> getAllExpenses() {
        return repository.findAll();
    }

    /*
    * 로직3: ID로 특정 지출 단건 조회
    * Day 4에서 배운 Optional의 orElseThrow를 적용하여 데이터가 없으면 예외 발생
    * */
    public Expense getExpenseById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 ID(" + id + ")의 지출 내역을 찾을 수 없습니다."));
    }

    /*
    * 로직4: ID로 특정 지출 삭제
    * 삭제 실패 시(해당 ID가 없을 시) 예외 발생
    * */
    public void removeExpense(Long id) {
        boolean isDeleted = repository.deleteById(id);
        if(!isDeleted) {
            throw new IllegalArgumentException("삭제 실패: ID " + id + "번 지출 내역이 존재하지 않습니다.");
        }
    }

    /*
    * 로직5: 총 지출액 합계 계산
    * 저장된 모든 지출의 금액을 합산하여 반환
    * */
    public long calculateTotalAmount() {
        long total = 0;
        for(Expense expense : repository.findAll()){
            total += expense.getAmount();
        }
        return total;
    }
}
