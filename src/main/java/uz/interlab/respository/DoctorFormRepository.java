package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.interlab.entity.form.onlineRegstration.DoctorForm;

public interface DoctorFormRepository extends JpaRepository<DoctorForm, Long>
{
}
