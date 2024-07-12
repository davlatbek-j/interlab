package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.interlab.entity.vacancy.Vacancy;

import java.util.List;

public interface VacancyRepository extends JpaRepository<Vacancy, Long>
{
    boolean existsBySlug(String slug);

    @Query(value = "SELECT * FROM vacancy WHERE slug = :slug", nativeQuery = true)
    Vacancy findBySlug(String slug);

    List<Vacancy> findAllByActive(boolean active);

    List<Vacancy> findAllByMainAndActive(boolean main, boolean active);
}
