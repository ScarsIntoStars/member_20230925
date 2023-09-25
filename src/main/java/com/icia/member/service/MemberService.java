package com.icia.member.service;

import com.icia.member.dto.MemberDTO;
import com.icia.member.entity.MemberEntity;
import com.icia.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long save(MemberDTO memberDTO){
        System.out.println("Entity 되기 직전" + memberDTO);
        MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
        Long saveId = memberRepository.save(memberEntity).getId();
        System.out.println("saveId : " +  saveId);
        return saveId;
    }
}
