package com.health.Health.repository;


import com.health.Health.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // 이 클래스는 데이터베이스와 상호작용하는 역할을 한다.
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByPhoneNumber(String phoneNumber); // 전화번호로 회원 검색
}
