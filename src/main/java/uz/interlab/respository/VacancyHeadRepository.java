package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.interlab.entity.vacancy.VacancyHead;

@Repository
public interface VacancyHeadRepository extends JpaRepository<VacancyHead, Long> {

    @Modifying
    @Query(value = "update vacancy_head set active=:active where id=:id", nativeQuery = true)
    void changeActive(@Param("id")Long id, boolean active);

    @Query(value = "select photo_url from vacancy_head where id=:id", nativeQuery = true)
    String findPhotoUrlById(@Param("id")Long id);

}
