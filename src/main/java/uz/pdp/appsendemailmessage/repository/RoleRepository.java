package uz.pdp.appsendemailmessage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appsendemailmessage.entity.RoleEntity;
import uz.pdp.appsendemailmessage.entity.roles.RoleName;

/**
 * Created by
 * Sahobiddin Abbosaliyev
 * 7/10/2021
 */
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    RoleEntity findByRoleName(RoleName roleName);

}
