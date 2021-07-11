package uz.pdp.appsendemailmessage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import uz.pdp.appsendemailmessage.entity.roles.RoleName;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

/**
 * Created by
 * Sahobiddin Abbosaliyev
 * 7/10/2021
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class RoleEntity implements GrantedAuthority {
    @Id
    private Integer id;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @Override
    public String getAuthority() {
        return roleName.name();
    }
}
