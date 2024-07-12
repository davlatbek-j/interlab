package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.interlab.entity.sale.Sale;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long>
{
    List<Sale> findByActive(boolean active);

    List<Sale> findByMain(boolean main);

    List<Sale> findByMainAndActive(boolean main, boolean active);


    boolean existsBySlug(String slug);

    @Query(value = "SELECT * FROM sale WHERE slug = :slug", nativeQuery = true)
    Sale findBySlug(String slug);
}
