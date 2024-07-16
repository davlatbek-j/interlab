package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.interlab.entity.service.ServiceHead;

public interface ServiceHeadRepository extends JpaRepository<ServiceHead, Long>
{
}
