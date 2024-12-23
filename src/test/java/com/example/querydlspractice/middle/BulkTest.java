package com.example.querydlspractice.middle;

import com.example.querydlspractice.member.entity.Member;
import com.example.querydlspractice.utils.TestDataUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.querydlspractice.member.entity.QMember.member;

/**
 * bulk 쿼리
 * 벌크 연산후 꼭 영속성 컨텍스트를 지워 줘야함
 */
@Transactional
@SpringBootTest
public class BulkTest {
    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() { // 테스트 케이스 실행전 테스트 데이터 세팅
        queryFactory = new JPAQueryFactory(em);
        TestDataUtil.setupTestData(em);
    }

    @Test
    @Commit
    @DisplayName("bulk update")
    public void bulkUpdate() {
        long count = queryFactory
                .update(member)
                .set(member.username, "비회원")
                .where(member.age.lt(28))
                .execute();

        // 벌크 연산후 꼭 영속성 컨텍스트를 지워주자
        em.flush();
        em.clear();

        List<Member> result = queryFactory
                .selectFrom(member)
                .fetch();

        for (Member member : result) {
            System.out.println("member = " + member);
        }

    }

    @Test
    @Commit
    @DisplayName("bulk update - add age")
    public void bulkAdd() {
        long count = queryFactory
                .update(member)
                .set(member.age, member.age.add(1))
                .execute();
    }

    @Test
    @Commit
    @DisplayName("bulk delete")
    public void bulkDelete() {
        long count = queryFactory
                .delete(member)
                .where(member.age.gt(18))
                .execute();
    }
}
