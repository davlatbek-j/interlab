package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.interlab.entity.form.LegalPageForm;

public interface LegalPageFormRepository extends JpaRepository<LegalPageForm, Long>
{
}
