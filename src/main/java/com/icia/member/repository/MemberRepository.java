package com.icia.member.repository;

import com.icia.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
//jpa를 상속받으면 자동으로 springbean에 등록이 되기 때문에 @repositoryt 안해줘도 됨
}
