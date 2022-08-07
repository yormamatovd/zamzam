package systemuser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import systemuser.entity.SystemUser;

@Repository
public interface SystemUserRepo extends JpaRepository<SystemUser, Long> {
}
