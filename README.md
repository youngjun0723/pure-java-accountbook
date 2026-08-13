# 💰 CLI 자산 & 지출 관리 시스템 (Asset & Expense Tracker)

순수 자바(Pure Java) 환경에서 구현하는 콘솔 기반 자산 및 지출 관리 프로그램입니다.  
스프링(Spring)이나 외부 라이브러리 없이, 자바 기본기를 다지기 위해 표준 라이브러리만을 사용하여 차근차근 개발을 진행하고 있습니다.

---

## 📌 프로젝트 소개

개인의 자산 현황과 일별/월별 지출 및 수입 내역을 콘솔 환경에서 기록하고 관리하는 프로젝트입니다.  
프로그램을 만들며 객체지향 설계, 예외 처리, 데이터 영속성(File I/O) 등의 Java 핵심 개념을 직접 적용하고 복습하는 것을 목표로 합니다.

---

## 🛠️ 활용 예정 및 적용 중인 Java 개념

- **Object-Oriented Programming**
    - 모델(Model), 저장소(Repository), 서비스(Service) 역할을 분리하는 객체지향 설계

- **Collection Framework**
    - 거래 내역 데이터를 다루기 위한 `List`, `ArrayList`
    - 카테고리별 집계 및 요약을 위한 `Map`, `HashMap`

- **File I/O**
    - 데이터를 파일에 저장하고 앱 시작 시 불러오기 위한 `BufferedReader`, `BufferedWriter` 활용

- **Exception Handling**
    - 잘못된 콘솔 입력값 처리 및 예외 상황 대응

- **Java 8+ (Time API & Stream)**
    - 날짜 처리를 위한 `java.time` 패키지 (`LocalDate`)
    - 조건별 데이터 필터링 및 집계를 위한 `Stream API`

---

## 💡 주요 기능 개발 계획

- [ ] **기본 데이터 모델링 (`Expense`, `Account` 등)**
- [ ] **콘솔 입출력 메뉴(UI) 구현**
- [ ] **수입 / 지출 내역 추가 및 조회**
- [ ] **월별 / 카테고리별 필터링 기능**
- [ ] **파일 저장 및 로딩 (데이터 영속성 구현)**

---

## 🚀 실행 방법 (개발 진행 중)

### 요구 사항
- **JDK 11** 이상

# 📝 커밋 규칙 (Git Commit Convention)

하루에 한 기능이나 한 클래스를 만들 때마다 소규모 단위로 커밋을 진행합니다.

---

## 📌 Commit Message Format

```text
<type>: <description>

🏷️ Type
feat : 새로운 기능 추가 / 클래스 작성

fix : 버그 수정

refactor : 코드 구조 개선

docs : README 등 문서 수정

💡 커밋 예시
feat: Expense 모델 클래스 정의

feat: 사용자 입출력 콘솔 메뉴 구현

docs: README.md 초기 작성
