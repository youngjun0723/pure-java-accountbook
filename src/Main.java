import controller.ExpenseController;
import repository.ExpenseRepository;
import service.ExpenseService;

public class Main {
    public static void main(String[] args) {
        // 1. 데이터 저장소(Repository) 생성
        ExpenseRepository repository = new ExpenseRepository();

        // 2. 서비스(Service)에 저장소 주입 (DI)
        ExpenseService service = new ExpenseService(repository);

        // 3. 컨트롤러(Controller)에 서비스 주입 (DI)
        ExpenseController controller = new ExpenseController(service);

        // 4. 대화형 콘솔 프로그램 시작!
        controller.run();
    }
}