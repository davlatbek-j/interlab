package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.interlab.entity.doctor.DoctorHead;

public interface DoctorHeadRepository extends JpaRepository<DoctorHead, Long>
{
}
