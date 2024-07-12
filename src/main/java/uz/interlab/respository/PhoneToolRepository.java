package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.interlab.entity.PhoneTool;

@Repository
public interface PhoneToolRepository extends JpaRepository<PhoneTool, Long> {
}
