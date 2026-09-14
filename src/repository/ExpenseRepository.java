package repository;

import entity.Expense;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 지출(Expense) 데이터의 저장, 조회, 삭제를 담당하는 In-Memory 저장소 클래스
 */
public class ExpenseRepository {

    // 저장 파일 경로
    private static final String FILE_PATH = "expenses.csv";

    // 1. 데이터 저장소 (In-Memory 리스트)
    private final List<Expense> store = new ArrayList<>();

    // 2. ID 자동 증가(Auto-Increment)용 시퀀스 번호
    private Long sequence = 0L;

    public ExpenseRepository() {
        loadFromFile();
    }

    /**
     * 지출 내역 저장 (Create)
     * - 새로운 지출일 경우 고유 ID를 1씩 증가시켜 부여한 뒤 저장합니다.
     */
    public Expense save(Expense expense) {
        if (expense.getId() == null) {
            expense.setId(++sequence);
        }
        store.add(expense);
        saveToFile();
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
        boolean isDeleted = store.removeIf(expense -> expense.getId().equals(id));
        if(isDeleted) {
            saveToFile();
        }
        return isDeleted;
    }

    /**
     * 저장소 비우기 (테스트 및 초기화용)
     */
    public void clear() {
        store.clear();
        sequence = 0L;
        saveToFile();
    }

    /**
     * 현재 저장된 데이터 개수 반환
     */
    public int count() {
        return store.size();
    }

    // ==========================================
    // 📁 파일 I/O (CSV 읽기 / 쓰기) 내부 메서드
    // ==========================================

    /**
     * 파일에서 데이터 불러오기 (Load)
     */

    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if(!file.exists()) {
            return; // 파일이 아직 없으면 그냥 리턴
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))){
            String line;
            boolean isHeader = true;

            while((line = reader.readLine()) != null) {
                // 첫 번째 줄(id, date, category...)은 skip
                if(isHeader) {
                    isHeader = false;
                    continue;
                }

                if(line.trim().isEmpty()) {
                    continue;
                }

                // 2. 쉼표(,)를 기준으로 데이터 분리
                String[] parts = line.split(",", -1);
                if(parts.length >= 5) {
                    Long id = Long.parseLong(parts[0].trim());
                    LocalDate date = LocalDate.parse(parts[1].trim());
                    String category = parts[2].trim();
                    long amount = Long.parseLong(parts[3].trim());
                    String memo = parts[4].trim();

                    store.add(new Expense(id, date, category, amount, memo));

                    // 3. ID 시퀀스 동기화: 불러온 데이터 중 가장 큰 ID로 맞춤
                    if(id > sequence) {
                        sequence = id;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("!!파일 읽기 중 오류 발생: " + e.getMessage());
        }
    }

    /**
     * 현재 메모리 데이터를 CSV 파일로 저장하기 (Save)
     */
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, StandardCharsets.UTF_8))) {
            // 1. 헤더 작성
            writer.write("id,date,category,amount,memo");
            writer.newLine();

            // 2. 데이터 행 작성
            for(Expense expense : store) {
                String line = String.format("%d,%s,%s,%d,%s",
                        expense.getId(),
                        expense.getDate(),
                        expense.getCategory(),
                        expense.getAmount(),
                        expense.getMemo());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("!!파일 저장 중 오류 발생: " + e.getMessage());
        }
    }



}
