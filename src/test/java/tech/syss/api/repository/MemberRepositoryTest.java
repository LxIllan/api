package tech.syss.api.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import tech.syss.api.model.Member;
import tech.syss.api.model.Membership;

@DataJpaTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MembershipRepository membershipRepository;

    @Test
    void testFindByCode() {
        Membership membership = new Membership("Monthly", 1, 0, 350.0, null, null);
        membershipRepository.save(membership);
        Member member = new Member("0405", "Fernando", "Illan", "Fernando.Illan@syss.tech", "3861063066", "",
                membership, LocalDate.now().plusMonths(1));
        memberRepository.save(member);

        Optional<Member> result = memberRepository.findByCode("0405");
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals("Fernando", result.get().getName());
        assertInstanceOf(LocalDate.class, result.get().getEndMembership());
    }

    @Test
    void testFailFindByCode() {
        Membership membership = new Membership("Monthly", 1, 0, 350.0, null, null);
        membershipRepository.save(membership);
        Member member = new Member("0405", "Fernando", "Illan", "Fernando.Illan@syss.tech", "3861063066", "",
                membership, LocalDate.now().plusMonths(1));
        memberRepository.save(member);

        Optional<Member> result = memberRepository.findByCode("5851");
        assertFalse(result.isPresent());
        assertThrows(NoSuchElementException.class, () -> result.get());
    }
}
