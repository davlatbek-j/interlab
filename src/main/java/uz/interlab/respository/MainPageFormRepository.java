package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.interlab.entity.form.MainPageForm;

public interface MainPageFormRepository extends JpaRepository<MainPageForm, Long>
{
}
