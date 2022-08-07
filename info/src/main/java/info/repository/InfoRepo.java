package info.repository;

import info.entity.Info;
import info.enums.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InfoRepo extends JpaRepository<Info, Long> {
    boolean existsByEmail(String email);


    Optional<Info> findByEmailOrPhoneAndActiveTrue(String email, String phone);


    Page<Info> findAllByTypeAndActiveTrue(UserType type, Pageable pageable);
}
