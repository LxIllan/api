package tech.syss.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.syss.api.model.Membership;

public interface MembershipRepository extends JpaRepository<Membership, Long> {}
