package sugimomoto.springsecurity2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sugimomoto.springsecurity2.model.*;

public interface  SiteUserRepository extends JpaRepository<SiteUser,Long> {
    SiteUser findByUsername(String username);
    boolean existsByUsername(String username);
}
