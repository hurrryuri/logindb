package com.example.logindb;

import com.example.logindb.Constant.RoleType;
import com.example.logindb.Entity.LoginEntity;
import com.example.logindb.Repository.LoginRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

//Repository, Service를 주로 확인할 때
//스프링부트 버전과 상관없이 옛날 방식, 클래스 주입은 Autowired로 적용
//각 메소드에 Test를 선언해서 동작
@SpringBootTest
public class LoginRepositoryTest {
    @Autowired
    private LoginRepository loginRepository; //데이터베이스 처리(회원등록)
    @Autowired
    private PasswordEncoder passwordEncoder; // 암호를 암호화 처리

    @Test
    public void loginInsert() { //데이터베이스에 회원가입이 정상적으로 구동되는지 테스트하는 메소드
        //sample1~4번까지 4명을 저장, 비밀번호 1234, 1,3=User, 2,4=ADMIN
        for (int i = 1; i < 5; i++) {//1,2,3,4,5전까지 반복
            //builder는 각변수에 값 entity값으로 만들어 준다.
            LoginEntity loginEntity = LoginEntity.builder()
                    .loginid("sample" + i) //sample1, sample2, sample3, sample4
                    .password(passwordEncoder.encode("1234")) //비밀번호는 암호화
                    .username("홍길동" + i) //홍길동1, 홍길동2, 홍길동3, 홍길동4
                    .build();
            //권한부여
            if (i == 2 || i == 4) { //i가 2 또는 4이면 ADMIN을 추가
                loginEntity.setRoleType(RoleType.ADMIN);
            } else { //그렇지 않으면 (1,3) USER을 추가
                loginEntity.setRoleType(RoleType.USER);
            }
            loginRepository.save(loginEntity); //데이터베이스 저장
        }
    }
    //실행은 메소드별로 실행한다.
}