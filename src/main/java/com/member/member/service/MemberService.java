package com.member.member.service;

import com.member.member.dto.MemberDTO;
import com.member.member.entity.MemberEntity;
import com.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity memberEntity : memberEntityList){
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));

        }
        return memberDTOList;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionlMemberEntity = memberRepository.findById(id);
        if (optionlMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionlMemberEntity.get());
        }else{
            return null;
        }

    }

    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity= memberRepository.findByMemberEmail(myEmail);
        if (optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        }else {
            return null;
        }

    }

    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));

    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    public String emailCheck(String memberEmail) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberEmail);
        if(byMemberEmail.isPresent()){
            //조회결과이 있다는건 사용할수 없다
            return null;
        }else{
            return "ok";
        }

    }
}
