package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.interlab.entity.service.ServiceDetails;

public interface DetailsRepository extends JpaRepository<ServiceDetails, Long>
{

    @Transactional
    @Modifying
    @Query(value = "UPDATE service_details SET photo_url = :photoUrl WHERE id = :id", nativeQuery = true)
    void setPhotoUrl(@Param("photoUrl") String photoUrl, @Param("id") Long id);

}
