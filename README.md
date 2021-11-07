# simParty
## 21년도에도 참여하였지만 작년과는 예약하는 방법이 다양해지고 예약 수의 규모가 커져서 이대로 사용하기에는 무리라고 판단하여 다시 제작 예정
## Thymeleaf + Spring Boot + JPA + MariaDB + AWS Service(RDS, EC2, S3)
### Project Description
> 20년도 핼러윈 때 의상 대여를 위해 일손을 도와주었습니다. <br>
> 전화 문의와 당일 방문하여 대기하는 손님들의 응대를 하기 위해 응대팀과 대여팀이 종이를 들고 계단을 오르내리며 불편함을 겪었던 경험이 있습니다. <br>
> 그때 느꼈던 불편한 상황을 해결하기 위해 손님들도 기다리며 예약 상황을 실시간으로 알 수 있는 웹 페이지를 만드는 프로젝트입니다. <br>
> 목적: 예약 상황 실시간 공유 <br>

### 기능 요약
> 관리자는 글을 등록, 수정, 삭제할 수 있다. <br>
> ( 의상 페이지: 이미지 포함, 공지사항 페이지: 이미지 미포함 ) <br>
> 테마별로 의상을 볼 수 있다. <br>
> 검색을 통해 원하는 의상을 찾을 수 있다. <br>
> 의상 상세페이지는 예약되어 있는 시간을 확인할 수 있다. <br>

### 개발 상황
-
- 관리자용 예약 테이블 만들기 ---? 진행중. <br>
- 메인 페이지에서 날짜+시간별 예약 가능 상품 조회 ---> 완료. 21.10.03<br>
- 조회 페이지 화면 구성 ---> 완료. 21.10.02 <br>
- 로컬 저장소에서 S3로 저장소 변경 ---> 완료. <br>
- EC2를 이용한 배포 ---> 완료. <br> 
- 로컬 MariaDB에서 RDS로 변경 ---> 완료. <br>
- 관리자와 게스트의 구별을 위한 로그인 기능 ---> 완료. <br>
- 예약을 위한 게시글에서의 LocalDateTime CRUD ---> 완료. <br>
- 첨부파일(이미지) 등록, 조회, 수정, 삭제 ---> 완료. <br>
- 게시글 등록, 조회, 수정, 삭제 ---> 완료. <br>

### Screenshots 과 세부 내용 - 관리자 로그인 후 기능
> 메인화면의 Register 버튼 <br>
<div>
  <img src=" " width="500"> 
</div>
<br>


### 마무리
> 
> Author: 정천우 [CodeHousePig] <br>
> 2021.08.31 ~ ING <br>
> 개인 개발 블로그: https://blog.naver.com/codehouse9
