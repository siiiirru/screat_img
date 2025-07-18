# 비밀 이미지 저장소
< 네트워크 보안 수업의 텀프로젝트입니다. >
- 중요하거나 보안이 필요한 이미지를 암호화하여 서버에 저장해두고 사용자만 필요할 때 조회할 수 있게 하는 저장소
- 서버 관리자도 데이터를 확인하지 못하게 하여 민감한 데이터를 보관한다. 
- 서비스를 통해 사용자는 이미지를 개인 저장장치가 아닌 서버에 보관하고 클라이언트단에서 사용자가 입력한 이미지 비밀번호로 데이터를 암,복호화 한다. 
- 서버는 로그인에 필요한 정보와 암호화된 이미지만 관리하며, 이미지 비밀번호에 대한 정보를 전달받지 않기에 자료의 기밀성, 무결성, 가용성을 보장할 수 있다.

## 개발 환경
Springboot(java17), MariaDB, Docker, OpenSSL

![image](https://github.com/user-attachments/assets/4ad6a4b3-6d01-44a6-ba3c-4490d5893cc9)

- 스프링부트 앱과 마리아db를 각각의 이미지로 생성해 컨테이너로 패키징하여 실행
- 스프링부트에서 스프링시큐리티로 폼로그인과 인증을 구현하였고 타임리프로 프론트엔드 뷰렌더링을 구현
- 톰캣 내장서버로 로컬 환경에서 실행
- OpenSSL에서 발급받은 키로 인증서를 적용하여 https 통신이 가능하게끔 구현


## 로그인 및 회원가입
<img width="722" height="712" alt="image" src="https://github.com/user-attachments/assets/4b356c73-4ed7-4cc1-ad9b-26c9e0201ed7" />


- 첫화면은 로그인페이지로 리다이렉트 하였고 스프링시큐리티의 form로그인 기능 사용
- 회원가입을 하면 아이디는 그대로, 비밀번호는 해쉬함수를 거친 뒤 해쉬값으로 데이터베이스에 저장


## 이미지 업로드 섹션
<img width="993" height="586" alt="image" src="https://github.com/user-attachments/assets/df3daa85-3948-426d-b9eb-0b6a1b38694f" />


 암호화할 이미지 파일을 선택하고 해당 이미지의 이름, 이미지를 암호화할 비밀번호를 입력하여 업로드 버튼을 누르면 아래와 같이 수행
1) 이미지파일을 base64로 인코딩
2) 인코딩된 이미지 스트링을 자바스크립트의 CryptoJS 라이브러리를 사용해 AES 알고리즘으로 입력받은 패스워드를 키로 사용하여 암호화
3) 암호화된 이미지 스트링과 이미지 이름은 백엔드를 거쳐 DB에 저장

## 이미지 열람하기 섹션
<img width="989" height="518" alt="image" src="https://github.com/user-attachments/assets/50335b40-f981-4a6d-85ed-0f6a28789c43" />


사용자의 세션에서 userid로 이미지테이블을 조회한 결과를 목록으로 띄움
목록에서 이미지 이름을 클릭하면 이미지가 선택되고 이미지를 암호화할 때 사용했던 비밀번호를 
입력하고 버튼을 누르면 아래와 같이 수행
1) 사용자가 선택한 이미지의 id를 서버로 보내 암호화된 이미지 스트링을 받음
2) 받은 이미지 스트링을 CrytoJS 라이브러리의 복호화 함수를 통해 입력받은 비밀번호를 
키로 하여 복호화
3) 복호화 된 이미지를 화면에 출력한다. 비밀번호가 틀리면 이미지는 출력되지 않고 복호화에 실패했다는 경고창이 뜸
