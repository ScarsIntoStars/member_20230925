package com.icia.member.service;

import com.icia.member.dto.MemberDTO;
import com.icia.member.entity.MemberEntity;
import com.icia.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long save(MemberDTO memberDTO){
        MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
        Long saveId = memberRepository.save(memberEntity).getId();
        return saveId;
    }

    public boolean login(MemberDTO memberDTO) {
        /*
            DB에서 로그인하는 사용자의 이메일을 조회한 결과를 가져와서
            비밀번호가 일치하는지 비교한 뒤 로그인 성공 여부를 판단

            findByMemberEmail()


            select * from member_table where member_email = ?
            // jpa에서 where절 이하는 findBy...로 함

            findById()
            => select * from member_table where id = ?
         */

        // 1번 방법
//        memberRepository.findByMemberEmail(memberDTO.getMemberEmail()).orElseThrow(() -> new NoSuchElementException();

        // 2번 방법
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmailAndMemberPassword(memberDTO.getMemberEmail(), memberDTO.getMemberPassword());
        if (optionalMemberEntity.isPresent()){

            return true;
        } else {
            return false;
        }
    }
}
