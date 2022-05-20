# springbootBoard

# 개발환경
개발 툴: eclipse 4.15.0, visual code studio 1.66.2, HeidiSQL 11.3.0.6295

DBMS: MariaDB 10.6

언어: JAVA 11, HTML5, CSS3, JAVAScript

프레임 워크: SpringBoot 2.6.7, Bootstrap

서버: tomcat-embed 9.0.62

# 개발 목적
스프링 부트를 통해 스프링 시큐리티와 JPA을 연습하기 위한 게시판 만들기

# 기능 구현
1. 게시글(작성(파일 업로드), 조회(검색), 수정, 삭제)
2. 회원(가입, 수정)
3. 시큐리티를 이용한 암호화

# SpringBoot란?
자바를 기반으로 스프링 어플리케이션을 쉽게 만들 수 있도록 하는 프레임워크

스프링부트의 장점
1. 독립형 Spring 애플리케이션 생성
2. Tomcat, Jetty 또는 Undertow를 직접 포함(WAR 파일을 배포할 필요 없음)
3. 빌드 구성을 단순화하기 위해 독자적인 '스타터' 종속성을 제공합니다.
4. 가능할 때마다 Spring 및 타사 라이브러리를 자동으로 구성
5. 메트릭, 상태 확인 및 외부 구성과 같은 프로덕션 준비 기능을 제공합니다.
6. 코드 생성 및 XML 구성 요구 사항 없음

스프링 부트의 maven과 gradle를 빌드툴로 제공한다.

graddle란? 
  groovy언어 기반의 빌드 도구, maven과 ant의 장점을 모아놓았음 
  •	Ant처럼 유연한 범용 빌드 도구 (A very flexible general purpose build tool like Ant.)
  •	Maven을 사용할 수 있는 변환 가능 컨벤션 프레임 워크 (Switchable, build-by-convention frameworks a la Maven. But we never lock you in!)
  •	멀티 프로젝트에 사용하기 좋음 (Very powerful support for multi-project builds.)
  •	Apache Ivy에 기반한 강력한 의존성 관리 (Very powerful dependency management (based on Apache Ivy))
  •	Maven과 Ivy 레파지토리 완전 지원 (Full support for your existing Maven or Ivy repository infrastructure.)
  •	원격 저장소나, pom, ivy 파일 없이 연결되는 의존성 관리 지원
    (Support for transitive dependency management without the need for remote repositories or pom.xml and ivy.xml files.)
  •	그루비 문법 사용 (Groovy build scripts.)
  •	빌드를 설명하는 풍부한 도메인 모델 (A rich domain model for describing your build.)
  
  maven과의 비교
   •  Build라는 동적인 요소를 XML로 정의하기에는 어려운 부분이 많다.
   •	설정 내용이 길어지고 가독성 떨어짐
   •	의존관계가 복잡한 프로젝트 설정하기에 부적절
   •	상속구조를 이용한 멀티 모듈 구현
   •	특정 설정을 소수의 모듈에서 공유하기 위해서는 부모 프로젝트를 생성하여 상속하게 해야 함 (상속의 단점 생김)
   •  Gradle은 Groovy를 사용하기 때문에, 동적인 빌드는 Groovy 스크립트로 플러그인을 호출하거나 직접 코드를 짜면 된다.
   •	Configuration Injection 방식을 사용해서 공통 모듈을 상속해서 사용하는 단점을 커버했다.
   •	설정 주입 시 프로젝트의 조건을 체크할 수 있어서 프로젝트별로 주입되는 설정을 다르게 할 수 있다

참고:https://spring.io/projects/spring-boot#overview

스프링부트는 스프링 이니셜 라이저를 통해 쉽개 구성할 수 있다.
참고: https://start.spring.io/

스프링 부트, jar, war
Jar: 자바 어플리케이션이 동작할 수 있도록 해주는 리소스 속성파일, 라이브러리 등이 포함된 압축파일/ JDK 와 JRE만 있어도 실행 가능
(JDK: 자바 개발 키트, JRE: 자바실행환경)

War: 서블릿, jsp 컨테이너에 배치하는 압축 포맷 파일, 웹 서버 또는 웹 컨테이너 필요
외부 서버를 이용하는 웹 프로젝트는 war파일로 패키징 해야 함

하지만 스프링 부트는 내장 톰캣으로 돌려서 jar파일로 패키징 가능

spring boot devtools란?
- Property Defaults : Thymeleaf는 기본적으로 성능을 향상시키기 위해서 캐싱 기능을 사용한다. 하지만 개발하는 과정에서 캐싱 기능을 사용한다면 수정한 소스가 제대로 반영되지 않을 수 있기 때문에 cache의 기본값을 false로 설정할 수 있다.
- Automatic Restart:  claapath에 있는 파일이 변경될 때마다 애플리케이션을 자동으로 재시작해준다. 개발자가 소스 수정 후 애플리케이션을 재실행하는 과정을 줄일 수 있으므로 생산성을 향상시킬 수 있다.
- Live Reload : 정적 자원(html, css, js) 수정 시 새로고침 없이 바로 적용할 수 있다.
쉽게 말하면 브라우저로 전송되는 내용들에 대한 코드가 변경되면, 자동으로 어플리케이션을 재시작하여 브라우저에도 업데이트를 해주는 역할을 한다.
관련 디펜던시: 
developmentOnly 'org.springframework.boot:spring-boot-devtools'

# 나의 스프링 부트 설정(build.gradle, application.properties)
![화면 캡처 2022-05-20 160712](https://user-images.githubusercontent.com/98066327/169472889-65727a03-89ac-4d9a-bf7b-e973d5d41c3d.png)
![화면 캡처 2022-05-20 160738](https://user-images.githubusercontent.com/98066327/169473061-5913f5cf-78ab-4e03-b852-f4f1fe391ff2.png)

# 마리아 DB 연동
연동을 위한 스프링 부트에서의 설정

-build.gradle
implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.2'
runtimeOnly 'org.mariadb.jdbc:mariadb-java-client'

-application.properties
  -mariadb \uC124\uC815
  spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
  spring.datasource.url=jdbc:mariadb://localhost:3306/test
  spring.datasource.username=root
  spring.datasource.password=root

  -mapper.xml mapper location setting
  mybatis.mapper-locations:classpath:mappers/**/*.xml
  -model camelcase setting
  mybatis.configuration.map-underscore-to-camel-case=true
  -alias setting
  mybatis.type-aliases-package=vo패키지
  -mapper logging level setting
  logging.level.com.prototype.domain.repository=TRACE

  -server setting
  server.port=8900
  참고: https://www.devkcj.com/gatsby-springMVC-2/
  
# 이번에 사용한 어노테이션
@Entity: 데이터 베이스 연동을 위한 모델 클래스라는 것을 알려주는 어노테이션
*PK를 나타내기 위해 @Id 어노테이션을 사용하며, 생성 방법을 정의하기 위해 @GeneratedValue 를 사용한다
@Id: 기본키라는 것을 알려주는 어노테이션
@GeneratedValue(strategy = identity, auto. sequence(DB에 따로 시퀀스 생성해야). Table
	전략에 따른 차이점 찾아보기
프로젝트에 연결된 DB의 넘버링 전략을 따라간다.(오라클-시퀀스, 마리아-오토인크레먼트…)
@Mapping : 매퍼 인터페이스임을 명시(Repository, 매퍼는 직접 DB에 접촉) 매퍼.xml파일 namespace에 매퍼 파일의 경로명
@Column
@CreatedDate
@LastModifiedDate
@EnableJpaAuditing : Application 메인 클래스에 넣어 audit 활성화 해주는 어노테이션
*auditing란? 도메인에서 공통적으로 쓰이는 필드나 컬럼들에 대해 자동으로 값을 넣어주는 기능, 대표적으로 생성일, 수정일, 생성자, 수정자가 있다.
@EntityListeners(AuiditingEntityListener.class): Entity에 임포트
참고: https://webcoding-start.tistory.com/53  baseTImeEntity클래스 이용 audit(사용할 Entity 여러 개일 때)
참고: https://compunication.tistory.com/27 vo 컬럼에 직접 넣음(게시판 하나일 때 사용 가능)
@DynamicInsert : 디폴트 설정된 컬럼에 null인 값이 들어가게 되면 디폴트가 작용되는 것이 아니라 null이 들어가 null오류가 날 수 있는데, null인 값의 인서트를 막아줌으로써 디폴트 값이 적용될 수 있도록 한다.
@Data 는 안 쓴다. Setter 를 해주지 말아야할 필드도 있기 때문에
@Builder 필요한 컬럼들을 자유롭게 쓸 수 있도록, 
@PageableDefault(size = 10, sort="boardNo", direction=Sort.Direction.DESC) Pageable pageable
@JsonIgnoreProperties: 무함참조 방지->다대일 또는 다대다 매핑을 하다보면 무한참조가 일어난다.
Ex) 보드안에 첨부파일 컬럼이 있고 첨부파일에는 보드 객체 형태로 primary키를 조인해오는 컬럼이 있는데 이때 서로 참조하면서 무한참조가 일어난다.
그때 조인의 주체가 아닌 테이블(주키를 주는 테이블, 여기서는 보드 테이블) 의 해당 컬럼(보드테이블의 첨부파일 컬럼)에 @JsonIgnoreProperties({“board”})을 걸어서 Json 파싱을 방지하게 되면 Json 안에 Json 형태로 무한 참조하는 것을 방지할 수 있다.(이 컬럼에 조인된 테이블의 해당 컬럼(Attachment 테이블의 ‘board’컬럼은 Json으로 파싱하지 않는다.))

# 스프링 시큐리티 - 더 공부 필요(아직 이해하지 못 함)

-참고:https://spring.io/guides/gs/securing-web/

-시큐리티 관련 디펜던시 추가
  implementation 'org.springframework.boot:spring-boot-starter-security'

-스프링 시큐리티 관련 가이드: https://spring.io/guides

-권한체크
  implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity5'
  <html xmlns:th=”http://www.thymeleaf.org” xmlns:sec=”http://www.thymeleaf.org/extras/spring-security”> => 타임리프에서 스프링 시큐리티 사용할 수 있게 하는 네임스페이스
  sec:authorize-sepr=”isAuthenticated()” 권한이 있어? 

-로그인 폼의 아이디랑 비밀번호 name 값이 username, password이어야 값이 넘어감


# 예외처리(구현하지 못 함 더 공부 필요)
  예외처리 방식은 크게 2가지
-@ControlerAdvice를 통한 모든 controller에서 발생할 수 있는 예외 처리(@RestControllerAdvice)
-@ExceptionHandler를 통한 특정  Controller의 예외 처리: 어노테이션 뒤에 괄호를 붙여 어떤 exceptionclass 를 처리할 지 설정할 수 있음(@ExceptionHandler(00Exception.class)) 이때 세부적인 예외처리를 해준 메소드가 더 우선순위가 높다., @ControllerAdvice에서도 사용하지만 일반 Controller 안에서도 사용 가능(이때 컨트롤러에 있는 예외처리가 우선순위가 높다.)
  
  
# Entity vs DTO vs Vo
  Entity(DB와 직접연결 setter 지양) 
  vs DTO(게터 세터있고 비즈니스 로직 없는 데이터만 왔다 갔다 하는 클래스)
  vs VO(게터 세터 있고 비즈니스 로직 있는):
Entity는 DB와 직접연결 하는 클래스로 값 변환하는 것을 지양하고 DTO나 VO로 변환하여 쓴다. 
일반 사용시에는 필드를 맞춰서 beanutils.copyproperties(source,target)등의 기능을 통해 복사하여서 쓰고, save 시에는 Entity에서 @Builder 하여서 빌드된 필드대로 DTO나 VO 클래스에 Entity객체를 받아서 toEntity 메소드를 만들어 빌드된 필드만으로 Entity객체를 만들어 사용 
  
# 파일 업로드/ 파일 다운로드
파일 업로드: 
파일 서버로 보내는 방법
1. form 태그의 enctype 를 multipart/form-data로 설정/ 한 개만 올릴 것이 아니라면 multiple 설정
2. 그 안에 type이 file 인 input 태그를 name을 설정하여 만들어준다.
3. MultipartFile 형태를 받을 수 있는 파일 전용 dto를 만든다.
4. form이 보내주는 메소드에서 @ModelAttribute를 통해 해당 dto로 받아준다. (받아주는 객체 이름과 name값이 일치해야 한다.)– dto 없이@RequesParam 으로도 받을 수 있다.(name값 일치)
5. 받아온 MultipartFile 객체를 첨부파일 서비스단으로 보내준다.
6. 서버의 저장될 경로를 String 형태로 만들어준다.
   String path = System.getProperty(“user.dir”) + \\src\\main\\resources\\static\\지정파일;
7. DB에 저장될 용도의 첨부파일용 dto 새 객체 생성(파일 여러 개일 경우 반복문 안에서)
8. 파일 이름 중복이나 잘못 다운로드 받을 경우를 대비하기 위해 파일을 리네임 해준다.
1)UUID uuid = UUID.randonUUID(); 
String changeName = uuid+”_”+uploadVo.getUpfile().get(i).getOriginalFileName();
       2)각자 원하는 대로 rename를 해줄 클래스를 만들어서 rename해준다.
9. 위에서 만들어둔 경로와 rename한 이름을 통해 파일화 시켜준다.
File target1 = new File(path, changeName);
10.  transferTo() 메소드를 통해 받아온 파일들을 서버에 저장
     uploadVo.getUpfile().get(i).transeferTo(target1);
11. 파일에 대한 정보를 DB에 넣어준다.(파일의 원래 이름, 변한 이름, 조인된 게시글 등)
 => DB에 저장될 정보: 원래 파일 이름, rename한 파일 이름, 이름 전까지의 파일 경로 
 
다운로드(내가 이해한 것: 구현은 안 됨 추후 변경)
1. HttpServletResponse response, HttpServletRequest request, @RequestParam Long attNo 를 매개변수로 첨부파일 용 서비스 단으로 넘김
2. DB에서 해당 파일의 정보를 불러온다. 
3. System.getProperty(“user.dir”) 과 DB에서 불러온 경로 변한 이름 등을 통해 파일의 풀 경로를 만들어준다.
4. 풀경로를 통해 파일화를  시켜준다. File file = new File(풀경로);
5. 만둘어준 파일을 바이트화 하여 inputStream에 넣어줄 FileInputStream 객체와 바이너리 객체를 밖으로 내보내줄 아웃 스트림을 제공하는 ServletOutputStream 객체를 null로 초기화 하여 만들어준다.
6. 원래 파일명을 UTF-8로 인코딩해준다.(헤더에 넣어줄 것임)
   downName = new String(attachment.getOriginFilename().getBytes("UTF-8"), "ISO-8859-1");
7. 매개변수로 받아온 HttpServletResponse 객체 response에 들어온 파일을 내보내줄 정보를 담아 주는 헤더 설정을 해준다.
   .setHeade(“Content-Disposition”: 들어온 파일이 어떤 형태인지 웹페이지인지 페이지 일부인지, 다운로드 되어 로컬에 저장될 용도인지=>”attachment;filename=\””+인코딩된 파일네임+”\””
   .setContentType(“application/octet-stream”); 콘텐트 타입은 Tika 클래스를 통해 받을 수 있다.
   .setHeader(“Content-Transfer-Encoding”, ”binary”); 콘텐트를 바이너리 형태로 인코딩해 내보내 줌

8. byte화 시킨 파일을 담아줄 byte 배열 만들기 : byte b [] = new byte[1024]; int data = 0;
9. inputStream에의 정보를 읽어와 outputStream에 써준다.
while((data=(fileInputStream.read(b, 0, b.length))) != -1){ 
	            servletOutputStream.write(b, 0, data);
	        }
10. 써준 outputStream을 출력 해준다.  servletOutputStream.flush();
11. 자원들을 닫아준다.(stream들이 제대로 release 되기 위해서는 닫혀야 한다고 설명되어 있다.)

  
# JPA(JAVA Persistent API)란? 
API(Application Programming Interface)란? 
컴퓨터랑 프로그램을 연결해주는 인터페이스 (cf. UI: 사용자랑 연결해주는 인터페이스)

DB에 저장 되어있는 값들은 영속성 컨텍스트의 관리를 받지않는 준영속성 상태의 엔티티(1차 캐시 안에 있는 것이 아니라는 뜻) 따라서 자동적으로 변경 감지 되는 것이 아니라 직접적으로 더티체킹(변경감지) 또는 머지 해주어야 한다

ORM(Object Relational Mapping)-> JAVA Object -> 테이블로 매핑해주는 기술.
자바의 ORM 기술에 대한 표준 명세를 의미하는 JPA
-JPA 세이브(인서트, 업데이트)
ID(primarykey)가 없다면 인서트, 있으면 업데이트

-JPA find(select)할 때 조건 추가 하는 방법
findBy~And(Or)~(자료형 변수명, 자료형 변수명); where ‘~’ and  ‘~’ 와 같다.
문자열 포함 셀렉트: findByBoardTitleContaining(String keyword); 
=> where boardTitle like ‘%keyowrd%’ 와 같다.

-JPA 정렬 하는 방법
1.정렬하고자 하는 방식대로 메소드 작성(Service단)(org.springframework.data.domain.Sort 임포트)
private Sort sortByReplyNo() {
		return Sort.by(Sort.Direction.DESC,"정렬컬럼");}
2. 조건들과 소트 객체를 같이 지정해준다.
Sort sort = sortByReplyNo();
List<BoardReply> list=boardReplyRepository.findByBoardAndStatus(board, status, sort); 
*페이징 시에 정렬하는 방법
@PageableDefault(size = 10, sort="boardNo", direction=Sort.Direction.DESC) Pageable pageable

# JPA로 페이징하는 방법
Controller 단
![화면 캡처 2022-05-20 095413](https://user-images.githubusercontent.com/98066327/169475135-bd3ebef8-ea4d-4c49-9f94-bdcac67c4da7.png)

1.	@PageableDefault 를 통해 한페이지의 보여줄 게시물의 개수, 정렬 기준컬럼, 정렬 방식등을 지정하고 Pegeable 객체를 만든다.
2.	pageable객체와 검색 조건을 Page<Board> 게시물을 제네릭한 Page 객체를 반환 값으로 하여 서비스단으로 보내준다.
3.	startPage는 첫페이지 1과 총페이지에서 9를 뺀(pageable 할 시 첫 페이지가 0부터 반환 되기에 -10이 아닌 -9를 해주었다.) 수 중 더 큰 것을 반환하게 된다. 따라서 현재페이지가 10을 넘기기 전까지는 1을 반환, 11부터 2반환, 12부터 3반환
4.	endPage는 총페이지와 현재페이지+10 중 터 작은 것 반환(전체 페이지가 11이상이고 현재 페이지가 2일 경우 11반환, 현재 페이지가 3, 전체 페이지가 15일 경우 15반환)
5.	이렇게 지정한 startPage, endPage, 현재페이지, 총 페이지 수, Page 형으로 조회해온 보드 객체를 해당 페이지로 보내준다.(검색결과가 없을 시 endPage와 totalPage가 0을 반환하여 무조건1반환하도록 해줬다.)

Service 단
 ![화면 캡처 2022-05-20 101417](https://user-images.githubusercontent.com/98066327/169475283-937b31bb-20eb-4d0f-a93d-6d702b61e458.png)
  
  서비스단 에서는 검색 controller단 2번 에서 보내준 pageable 객체와 검색조건을 통해 조회해온다

view 단
 ![화면 캡처 2022-05-20 101635](https://user-images.githubusercontent.com/98066327/169475444-33c9aa05-7833-47dc-bd9f-c8335c02aa59.png)

이전 버튼은 현재페이지가 1이라면 diabled, 페이지 버튼 들은 반복문 돌리고 현재페이지와 일치한다면 disabled, 다음 버튼은 현재페이지가 토탈페이지와 같다면 disabled
(현재 페이지는 0부터 출력되기에 page+1로 해주었고 링크 연결 시에는 다시 i-1로 해주었다.)


# 느낀점
공부할 것이 겁나 많이 늘었다.
-스프링 부트
-JPA
-자바스크립트(바닐라JS)
-자바
-웹(HTTP 등)
-정처기
당장 생각나는 것만 해도 이 정도...아...좋다...
