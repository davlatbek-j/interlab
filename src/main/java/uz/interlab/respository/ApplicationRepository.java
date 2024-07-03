package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.interlab.entity.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
