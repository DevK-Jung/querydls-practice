package com.example.querydlspractice.member.repository;

import com.example.querydlspractice.member.entity.Member;
import com.example.querydlspractice.member.repository.custom.MemberRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

/**
 * Spring Data JPA, Custom Repository, QuerydslPredicateExecutor 활용
 */
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom, QuerydslPredicateExecutor<Member> {
    List<Member> findByUsername(String username);
}
