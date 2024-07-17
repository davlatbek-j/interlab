package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.interlab.entity.form.onlineAppointment.OnlineAppointmentForm;

public interface AppointmentFormRepository extends JpaRepository<OnlineAppointmentForm, Long>
{
}
