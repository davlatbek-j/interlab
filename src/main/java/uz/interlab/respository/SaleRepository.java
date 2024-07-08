package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.interlab.entity.Sale;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long>
{
    List<Sale> findByActive(boolean active);

    List<Sale> findByMain(boolean main);

    List<Sale> findByMainAndActive(boolean main, boolean active);

    @Query(value = "SELECT photo_url FROM sale WHERE id =:id", nativeQuery = true)
    String findPhotoUrlById(Long id);
}
