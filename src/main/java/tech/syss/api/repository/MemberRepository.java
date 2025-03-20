package tech.syss.api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.syss.api.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByCode(String code);
}
