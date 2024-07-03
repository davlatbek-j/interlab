package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.interlab.entity.doctor.DoctorDetails;

public interface DoctorDetailsRepository extends JpaRepository<DoctorDetails, Long>
{

}
