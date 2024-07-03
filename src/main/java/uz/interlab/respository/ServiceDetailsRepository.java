package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.interlab.entity.service.ServiceDetails;

public interface ServiceDetailsRepository extends JpaRepository<ServiceDetails, Integer>
{

}
