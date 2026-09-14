import entity.Expense;
import org.w3c.dom.ls.LSOutput;
import repository.ExpenseRepository;
import service.ExpenseService;

import java.time.LocalDate;
import java.util.List;

public class Main {
    static void main(String[] args) {
        System.out.println("==지출 관리 시스템 검증 시작==");

        // 1. 의존성 주입 - Repository를 먼저 생성하여 Service에 주입
        ExpenseRepository repository = new ExpenseRepository();
        ExpenseService service = new ExpenseService(repository);

        // 2. 지출 등록 test (addExpense)
        System.out.println("\n[1] 지출 등록 test");
        service.addExpense(LocalDate.now(), "식비", 9000L, "점심 백반");
        service.addExpense(LocalDate.now(), "교통", 1500L, "지하철");
        service.addExpense(LocalDate.now(), "카페", 4500L, "아이스 아메리카노");
        System.out.println("-> 3건의 지출 등록 완료");

        // 3. 전체 지출 목록 조회 테스트 (getAllExpenses)
        System.out.println("\n[2] 전체 목록 조회");
        List<Expense> expenses = service.getAllExpenses();
        for(Expense expense : expenses) {
            System.out.println(expense);
        }

        // 4. 총 지출액 합계 계산 test (calculateTotalAmount)
        System.out.println("\n[3] 총 지출액 계산");
        long totalAmount = service.calculateTotalAmount();
        System.out.printf("총 지출 금액: %, d원\n", totalAmount);

        // 5. 단건 조회 test (getExpenseById)
        System.out.println("\n [4] 1번 지출 단건 조회");
        Expense foundExpense = service.getExpenseById(1L);
        System.out.println("조회 결과: " + foundExpense);

        // 6. 삭제 test (removeExpense)
        System.out.println("\n [5] 2번 지출 삭제 test");
        service.removeExpense(2L);
        System.out.println("-> 2번 지출 삭제 완료");

        System.out.println("\n [6] 삭제 후 전체 목록 재확인");
        for(Expense expense : service.getAllExpenses()) {
            System.out.println(expense);
        }

        // 7. 예외 처리 test (존재하지 않는 99번 삭제 시도)
        System.out.println("\n[7] 예외 처리 검증 (없는 ID 삭제 시도)");
        try {
            service.removeExpense(99L);
        } catch (IllegalArgumentException e) {
            System.out.println("예외 발생 정상 감지: " + e.getMessage());
        }
        System.out.println("\n== 모든 기능 검증 완료 ==");
    }
}
