package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.interlab.entity.vacancy.VacancyBody;

@Repository
public interface VacancyBodyRepository extends JpaRepository<VacancyBody, Long> {

    @Modifying
    @Query(value = "update vacancy_body set active=:active where id=:id", nativeQuery = true)
    void changeActive(@Param("id")Long id, boolean active);

    @Query(value = "select icon_url from vacancy_body where id=:id", nativeQuery = true)
    String findPhotoUrlById(@Param("id")Long id);

}
