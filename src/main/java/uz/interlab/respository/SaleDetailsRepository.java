package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.interlab.entity.sale.SaleDetails;

public interface SaleDetailsRepository extends JpaRepository<SaleDetails, Long>
{
    boolean existsBySlug(String slug);

    @Query(value = "SELECT * FROM sale_details WHERE slug = :slug", nativeQuery = true)
    SaleDetails findBySlug(String slug);
}
