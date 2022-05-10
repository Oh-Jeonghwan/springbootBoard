package com.nmplus.springbootBoard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nmplus.springbootBoard.vo.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	//Optional: 널이 들어올 수 있는 값을 감싸는 Wrapper클래스로 널포인트익셉션을 방지해준
	Optional<Member> findByMemberIdAndStatus(String memberId, String status);

	Member findByMemberId(String id);

	Member findByEmail(String email);
}
