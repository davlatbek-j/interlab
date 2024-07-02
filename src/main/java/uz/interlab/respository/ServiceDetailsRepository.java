package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import uz.interlab.entity.ServiceDetails;

public interface ServiceDetailsRepository extends JpaRepository<ServiceDetails, Integer>
{

}
