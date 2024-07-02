package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.interlab.entity.ServiceDetails;

public interface DetailsRepository extends JpaRepository<ServiceDetails,Long>
{

}
