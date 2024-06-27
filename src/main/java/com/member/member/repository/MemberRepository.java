package com.member.member.repository;

import com.member.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
    //이메일로 회원 정보 조회
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
}
