package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.interlab.entity.form.FormRequest;

public interface FormRequestRepository extends JpaRepository<FormRequest, Long>
{
}
