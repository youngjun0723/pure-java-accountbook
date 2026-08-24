package repository;

import entity.Expense;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 지출(Expense) 데이터의 저장, 조회, 삭제를 담당하는 In-Memory 저장소 클래스
 */
public class ExpenseRepository {

    // 1. 데이터 저장소 (In-Memory 리스트)
    private final List<Expense> store = new ArrayList<>();

    // 2. ID 자동 증가(Auto-Increment)용 시퀀스 번호
    private Long sequence = 0L;

    /**
     * 지출 내역 저장 (Create)
     * - 새로운 지출일 경우 고유 ID를 1씩 증가시켜 부여한 뒤 저장합니다.
     */
    public Expense save(Expense expense) {
        if (expense.getId() == null) {
            expense.setId(++sequence);
        }
        store.add(expense);
        return expense;
    }

    /**
     * 전체 지출 목록 조회 (Read All)
     * - 외부에서 원본 store 리스트를 직접 수정하지 못하도록 새 ArrayList로 감싸서(방어적 복사) 반환합니다.
     */
    public List<Expense> findAll() {
        return new ArrayList<>(store);
    }

    /**
     * ID로 특정 지출 단건 조회 (Read One)
     * - 찾고자 하는 ID가 없을 수도 있으므로 null 안전성을 위해 Optional을 사용합니다.
     */
    public Optional<Expense> findById(Long id) {
        for (Expense expense : store) {
            if (expense.getId().equals(id)) {
                return Optional.of(expense);
            }
        }
        return Optional.empty();
    }

    /**
     * ID로 특정 지출 삭제 (Delete)
     * - ID가 일치하는 항목을 삭제하고, 삭제 성공 여부(true/false)를 반환합니다.
     */
    public boolean deleteById(Long id) {
        return store.removeIf(expense -> expense.getId().equals(id));
    }

    /**
     * 저장소 비우기 (테스트 및 초기화용)
     */
    public void clear() {
        store.clear();
        sequence = 0L;
    }

    /**
     * 현재 저장된 데이터 개수 반환
     */
    public int count() {
        return store.size();
    }
}
