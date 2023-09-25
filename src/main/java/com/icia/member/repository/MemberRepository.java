package com.icia.member.repository;

import com.icia.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // jpa를 상속받으면 자동으로 springbean에 등록이 되기 때문에 @repositoryt 안해줘도 됨

    // interface는 추상메서드(abstract method)만 사용가능
    // 실행문구가 없는 것이 특징


    // select * from member_table where member_email = ? 이런 쿼리문이 만들어짐
    Optional<MemberEntity> findByMemberEmail(String memberEmail);


    // 아래처럼 email과 password를 같이 찾는 것도 가능함
    // select * from member_table where member_email = ? and member_password = ?
    Optional<MemberEntity> findByMemberEmailAndMemberPassword(String memberEmail, String memberPassword);

}
