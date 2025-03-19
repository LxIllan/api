package tech.syss.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.syss.api.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
