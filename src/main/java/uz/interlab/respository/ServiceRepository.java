package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.interlab.entity.Photo;
import uz.interlab.entity.service.Service;

public interface ServiceRepository extends JpaRepository<Service, Long>
{

    @Transactional
    @Modifying
    @Query(value = "UPDATE service SET details_id = :detailsId WHERE id = :id", nativeQuery = true)
    void updateDetailsId(@Param("id") Long serviceId, @Param("detailsId") Long detailsId);

    @Query(value = "SELECT details_id FROM service WHERE id= :serviceId", nativeQuery = true)
    Long findDetailsIdByServiceId(@Param("serviceId") Long serviceId);
}
