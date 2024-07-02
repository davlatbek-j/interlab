package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import uz.interlab.entity.Service;

public interface ServiceRepository extends JpaRepository<Service, Long>
{

}
