package com.member.member.service;

import com.member.member.dto.MemberDTO;
import com.member.member.entity.MemberEntity;
import com.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO) {
        //dto -> entity 객체로 변환
        //repository의 save메서드 호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
        //repositoryd의 save메소드호출 (entity 객체를 넘겨줘야한다.)


    }

    public MemberDTO login(MemberDTO memberDTO) {
        //1.회원이 입력한 이메일로 DB에서  조회를함
        //2.DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는진 판단
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if (byMemberEmail.isPresent()) {
            //조회결과가 있다(해당 이메일을 가진 회원정보가 있다)
            MemberEntity memberEntity = byMemberEmail.get();
            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                       //비번일치
                      //entity -> dto 변화후 리턴
                   MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                   return dto;
            }else {
                //비번불일치
                return null;
            }

        } else {
            //조회결과가 없다(해당 이메일을 가진 회원이 없다.)
            return null;
        }

    }
}
