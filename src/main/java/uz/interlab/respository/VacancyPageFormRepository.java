package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.interlab.entity.form.vacancyPage.VacancyPageForm;

public interface VacancyPageFormRepository extends JpaRepository<VacancyPageForm, Long>
{
}
