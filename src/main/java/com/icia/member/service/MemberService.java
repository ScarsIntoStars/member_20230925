package com.icia.member.service;

import com.icia.member.dto.MemberDTO;
import com.icia.member.entity.MemberEntity;
import com.icia.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long save(MemberDTO memberDTO) {
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
        if (optionalMemberEntity.isPresent()) {

            return true;
        } else {
            return false;
        }
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity memberEntity : memberEntityList) {
            MemberDTO memberDTO = MemberDTO.toSaveDTO(memberEntity);
            memberDTOList.add(memberDTO);

            // 한줄로
//            memberDTOList.add(MemberDTO.toSaveDTO(memberEntity));
        }
        return memberDTOList;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
            // 있는 경우
            MemberEntity memberEntity = optionalMemberEntity.get();
            return MemberDTO.toSaveDTO(memberEntity);
        } else {
            // 없는 경우
            return null;
        }

        // 한 줄로 가능
        // MemberEntity memberEntity = memberRepository.findById(id).orElseThrow(() -> new NosuchElemetException());
        // return Member.toSaveDTO(memberEntity);
    }

    public Boolean emailCheck(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if (optionalMemberEntity.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public MemberDTO findByMemberEmail(String memberEmail) {
        MemberDTO memberDTO = new MemberDTO();
        MemberEntity memberEntity = memberRepository.findByMemberEmail(memberEmail).orElseThrow(() -> new NoSuchElementException());
        return memberDTO.toSaveDTO(memberEntity);
    }

    public void update(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toUpdateEntity(memberDTO);
        memberRepository.save(memberEntity);

    }

    public void delete(Long id) {
        memberRepository.deleteById(id);
    }
}
