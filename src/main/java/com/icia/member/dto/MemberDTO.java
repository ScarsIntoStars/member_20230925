package com.icia.member.dto;

import com.icia.member.entity.MemberEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Member;


@Data
public class MemberDTO {
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberBirth;
    private String memberMobile;

    // Entity -> DTO
    public static MemberDTO toSaveDTO(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberBirth(memberEntity.getMemberBirth());
        memberDTO.setMemberMobile(memberEntity.getMemberMobile());
        return memberDTO;
    }

}
