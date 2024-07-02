package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.interlab.entity.Service;

public interface ServiceRepository extends JpaRepository<Service, Long>
{

    @Transactional
    @Modifying
    @Query(value = "UPDATE service SET details_url = :detailsUrl WHERE id = :id", nativeQuery = true)
    void updateDetailsId(@Param("id") Long serviceId, @Param("detailsUrl") String detailsUrl);
}
