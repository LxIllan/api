package tech.syss.api.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
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
        Member member = new Member(1L, "0405", "Fernando", "Illan", "Fernando.Illan@syss.tech", "3861063066", "",
                membership, LocalDate.now().plusMonths(1));
        memberRepository.save(member);

        Optional<Member> result = memberRepository.findByCode("0405");
        assertTrue(result != null);
        assertEquals("Fernando", result.get().getName());
    }
}
