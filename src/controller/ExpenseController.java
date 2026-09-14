package controller;

import entity.Expense;
import service.ExpenseService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * 콘솔 화면 입출력 및 사용자 요청을 처리하는 컨트롤러 클래스
 */
public class ExpenseController {
   private final ExpenseService service;
   private final Scanner scanner;

    public ExpenseController(ExpenseService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    /**
     * 콘솔 프로그램 실행 루프 (0 입력 시까지 무한 반복)
     */
    public void run() {
        while (true) {
            printMenu();
            System.out.print("선택 >> ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    registerExpense();
                    break;
                case "2":
                    viewAllExpenses();
                    break;
                case "3":
                    viewExpenseById();
                    break;
                case "4":
                    deleteExpense();
                    break;
                case "5":
                    viewTotalAmount();
                    break;
                case "0":
                    System.out.println("\n프로그램을 종료합니다. 감사합니다!");
                    return;
                default:
                    System.out.println("❌ 잘못된 입력입니다. 메뉴 번호(0~5)를 다시 입력해 주세요.\n");
            }
        }
    }

    // 메뉴판 출력
    private void printMenu() {
        System.out.println("\n========================================");
        System.out.println("   ☕ 스마트 자산/지출 관리 프로그램");
        System.out.println("========================================");
        System.out.println(" 1. 지출 내역 등록");
        System.out.println(" 2. 전체 지출 목록 조회");
        System.out.println(" 3. 지출 단건 조회 (ID 검색)");
        System.out.println(" 4. 지출 내역 삭제");
        System.out.println(" 5. 총 지출액 합계 조회");
        System.out.println(" 0. 프로그램 종료");
        System.out.println("========================================");
    }

    // [1] 지출 등록
    private void registerExpense() {
        System.out.println("\n[1] 지출 내역 등록");
        try {
            System.out.println("날짜 (YYYY-MM-DD, 엔터 치면 오늘 날짜) : ");
            String dateInput = scanner.nextLine().trim();
            LocalDate date = dateInput.isEmpty() ? LocalDate.now() : LocalDate.parse(dateInput);

            System.out.print("카테고리 (예: 식비, 교통, 카페 등) : ");
            String category = scanner.nextLine().trim();

            System.out.print("금액 (원) : ");
            long amount = Long.parseLong(scanner.nextLine().trim());

            System.out.print("메모 : ");
            String memo = scanner.nextLine().trim();

            Expense saved = service.addExpense(date, category, amount, memo);
            System.out.println("지출 등록 완료! [발급 ID: " + saved.getId() + "]");
        } catch (DateTimeParseException e) {
            System.out.println("날짜 형식이 올바르지 않습니다. (예: 2026-09-14)");
        } catch (NumberFormatException e) {
            System.out.println("금액은 숫자만 입력해 주세요.");
        } catch (IllegalArgumentException e) {
            System.out.println("등록 실패: " + e.getMessage());
        }
    }

    // [2] 전체 목록 조회
    private void viewAllExpenses() {
        System.out.println("\n[2] 전체 지출 목록 조회");
        List<Expense> expenses = service.getAllExpenses();
        if(expenses.isEmpty()) {
            System.out.println("등록된 지출 내역이 없습니다.");
            return;
        }
        System.out.println("--------------");
        for(Expense expense : expenses) {
            System.out.println(expense);
        }
        System.out.println("--------------");
    }

    // [3] 지출 단건 조회
    private void viewExpenseById() {
        System.out.println("\n [3] 지출 단건 조회");
        try {
            System.out.print("조회할 지출 ID 입력: ");
            long id = Long.parseLong(scanner.nextLine().trim());
            Expense expense = service.getExpenseById(id);
            System.out.println("조회 결과: " + expense);
        } catch (NumberFormatException e) {
            System.out.println("ID는 숫자만 입력해 주세요.");
        } catch (IllegalArgumentException e) {
            System.out.println("X" + e.getMessage());
        }
    }

    // [4] 삭제
    private void deleteExpense() {
        try {
            System.out.print("삭제할 지출 ID 입력 : ");
            long id = Long.parseLong(scanner.nextLine().trim());
            service.removeExpense(id);
            System.out.println(id + "번 지출이 성공적으로 삭제되었습니다.");   ;
        } catch (NumberFormatException e) {
            System.out.println("ID는 숫자만 입력해 주세요.");
        } catch (IllegalArgumentException e) {
            System.out.println("X" + e.getMessage());
        }
    }

    // [5] 총합 조회
    private void viewTotalAmount() {
        System.out.println("\n[5] 총 지출액 합계 조회");
        long total = service.calculateTotalAmount();
        System.out.printf("💰 현재까지 총 지출 금액: %,d원\n", total);
    }
}
