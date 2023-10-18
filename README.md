# 원티드 프리온보딩 백엔드 인턴십 선발과제

## 요구사항 분석
#### 1. 채용공고 등록
[POST] /recruit
- 로그인한 회사id로 채용 정보를 등록(해당 과제에서는 인증 절차가 생략되어 json으로 받음)
- 아래와 같은 request를 받는다
```json
{
  "companyId": "wanted",
  "jobPosition":  "백엔드 주니어 개발자",
  "signingBonus": 1000000,
  "jobDescription": "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..",
  "requiredSkills": "Python"
}
```
#### 2. 채용공고 수정
[PUT] /recruit
- 채용공고의 회사id와 동일한 회사인지 체크(해당 과제에서는 인증 절차가 생략되어 생략)
- 등록된 채용공고 조회 후 정보 수정
- 아래와 같은 request를 받는다
```json
{
  "jobPosition":"백엔드 주니어 개발자",
  "signingBonus":1000000,
  "jobDescription":"원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..",
  "requiredSkills":"Django"
}
```
#### 3. 채용공고 삭제
[DELETE] /recruit/{id}
- 채용공고의 회사id와 동일한 회사인지 체크(해당 과제에서는 인증 절차가 생략되어 생략)
- 논리적 삭제가 아닌 DB 삭제

#### 4. 채용공고 목록 조회 및 검색
[GET] /recruit
- search 파라미터가 없는 경우 전체 목록 조회
- 채용공고를 최신순으로 목록 조회
- page 설정은 프론트와 논의하여 결정
- 아래와 같은 response를 보낸다
```json
[
	{
      "recruitId": "611fb4dc-2cc1-48a4-bc5b-18625a3c73ff",
	  "companyName":"원티드랩",
	  "country":"한국",
	  "city":"서울",
	  "jobPosition":"백엔드 주니어 개발자",
	  "signingBonus":1500000,
	  "requiredSkills":"Python"
	},
	{
      "recruitId": "921c3652-d359-4642-9d61-f2fff63fa334",
	  "companyName":"네이버",
	  "country":"한국",
	  "city":"판교",
	  "jobPosition":"Django 백엔드 개발자",
	  "signingBonus":1000000,
	  "requiredSkills":"Django"
	}
]
```
[GET] /recruit?search=원티드
- search 파라미터 검색어가 포함된 채용공고를 최신순으로 목록 조회
- 대소문자 및 띄어쓰기 구분
- 아래와 같은 response를 보낸다
```json
[
	{
      "recruitId": "611fb4dc-2cc1-48a4-bc5b-18625a3c73ff",
	  "companyName":"원티드랩",
	  "country":"한국",
	  "city":"서울",
	  "jobPosition":"백엔드 주니어 개발자",
	  "signingBonus":1500000,
	  "requiredSkills":"Python"
	},
	{
      "recruitId": "78c2d79f-ea4f-4373-b966-040c705a3492",
	  "companyName":"원티드코리아",
	  "country":"한국",
	  "city":"부산",
	  "jobPosition":"프론트엔드 개발자",
	  "signingBonus":500000,
	  "requiredSkills":"javascript"
	}
]
```
#### 5. 채용 상세 페이지 조회
[GET] recruit/{id}
- 아래와 같은 response를 보낸다
```json
{
    "recruitId": "611fb4dc-2cc1-48a4-bc5b-18625a3c73ff",
    "companyName": "원티드",
    "country": "한국",
    "city": "서울",
    "jobPosition": "3백엔드 주니어 개발자",
    "signingBonus": 1000000,
    "requiredSkills": "Django",
    "jobDescription": "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..",
    "moreRecruit": [
        "2c070b89-dcdb-4008-aae7-4996ffcc92dc",
        "78c2d79f-ea4f-4373-b966-040c705a3492",
        "921c3652-d359-4642-9d61-f2fff63fa334"
    ]
}
```

#### 6. 채용공고에 지원 등록
[POST] recruit/apply
- 로그인한 사용자id로 지원 등록(해당 과제에서는 인증 절차가 생략되어 json으로 받음)
- 해당 사용자 지원 여부 체크
- 채용공고 존재 여부 체크
- 아래와 같은 request를 받는다
```json
{
  "recruitId": "921c3652-d359-4642-9d61-f2fff63fa334",
  "userId": "user01"
}
```

## 구현방법
#### 0. 프로젝트 설정
- postgresql, java 17, springboot 3, JPA 사용
- 개발 profile (dev)에 db 설정 및 sql 로깅 설정(해당 설정 파일은 git에는 생략)
- 로컬로 스케마를 생성

#### 1. 레포지토리 테스트 및 도메인 모델 구현
- 테스트를 만들어서 소스코드를 한 번에 더 간결하게 만들 수 있다
- 레포지토리 테스트를 작성하면서 도메인, 레포지토리, DTO을 수정
- 도메인 모델에서 비즈니스 로직 처리

#### 2. 검증 및 예외 처리
- DTO에 입력되는 데이터에 대한 검증(검증 파라미터는 임의로 정함)
- 예외처리는 @ControllerAdvice로 로깅 및 오류 메시지 반환

#### 3. 서비스 및 컨트롤러 단 구현
- 서비스 단은 트랜잭션을 보장하는 역할로 @Transactional 사용
- 서비스 중 조회는 readOnly = true
- 컨트롤러에서 

#### 4. 웹 (컨트롤러단) 통합 테스트 작성
- 예외처리를 검증하기 위해 통합테스트는 TestRestTemplate 사용하여 작성
- 정상 작동할 경우와 입력 될 수 있는 오류 테스트

#### 5. 리팩토링
- 전반적인 코드 검토 및 코드 리뷰 후 리팩토링 대상 및 우선도 선정
- 리팩토링 및 테스트 진행