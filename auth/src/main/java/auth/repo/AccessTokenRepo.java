package auth.repo;

import auth.entity.AccessCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessTokenRepo extends JpaRepository<AccessCode,Long> {
}
