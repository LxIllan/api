package tech.syss.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.syss.api.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByCode(String code);
}
