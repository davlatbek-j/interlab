package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.interlab.entity.form.doctorPage.DoctorFormRequest;

public interface DoctorFormRequestRepository extends JpaRepository<DoctorFormRequest, Long>
{
}
