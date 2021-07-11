package uz.pdp.appsendemailmessage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appsendemailmessage.entity.product.ProductEntity;

import java.util.UUID;

/**
 * Created by
 * Sahobiddin Abbosaliyev
 * 7/11/2021
 */
@RepositoryRestResource(path = "product")
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
}
