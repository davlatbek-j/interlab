package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.interlab.entity.service.Service;

public interface ServiceRepository extends JpaRepository<Service, Long>
{

    @Transactional
    @Modifying
    @Query(value = "UPDATE service SET details_id = :detailsId WHERE id = :id", nativeQuery = true)
    void updateDetailsId(@Param("id") Long serviceId, @Param("detailsId") Long detailsId);

    @Query(value = "SELECT details_id FROM service WHERE id= :serviceId", nativeQuery = true)
    Long findDetailsIdByServiceId(@Param("serviceId") Long serviceId);

    @Query(value = "SELECT details_id FROM service WHERE slug= :slug0", nativeQuery = true)
    Long findDetailsIdByServiceSlug(@Param("slug0") String slug);

    @Transactional
    @Modifying
    @Query(value = "UPDATE service SET slug = :slug WHERE id = :id", nativeQuery = true)
    void updateSlug(@Param("slug") String slug, @Param("id") Long serviceId);

    @Query(value = "SELECT * FROM service WHERE slug= :slug", nativeQuery = true)
    Service findBySlug(@Param("slug") String serviceSlug);

    boolean existsBySlug(String slug);

    @Query(value = "SELECT slug FROM service WHERE id = :id", nativeQuery = true)
    String findSlugById(@Param("id") Long serviceId);
}
