package uz.pdp.appsendemailmessage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appsendemailmessage.entity.UserEntity;

import javax.validation.constraints.Email;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by
 * Sahobiddin Abbosaliyev
 * 7/10/2021
 */
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByEmailCodeAndEmail(String emailCode, @Email String email);

    Optional<UserEntity> findByEmail(@Email String email);

}
