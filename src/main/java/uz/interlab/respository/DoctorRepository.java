package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.interlab.entity.doctor.Doctor;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long>
{
    @Query(value = "SELECT photo_url from doctor where id = :id", nativeQuery = true)
    String findPhotoUrlById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE doctor SET details_id = :detailsId WHERE id = :doctorId", nativeQuery = true)
    void setDetailsId(@Param("doctorId") Long doctorId, @Param("detailsId") Long detailsId);

    @Query(value = "SELECT details_id FROM doctor WHERE id =:doctorId", nativeQuery = true)
    Long findDetailsId(@Param("doctorId") Long doctorId);

    List<Doctor> findByMain(boolean main);

    List<Doctor> findByMainAndActive(boolean main, boolean active);

    List<Doctor> findByActive(boolean active);

    @Transactional
    @Modifying
    @Query(value = "UPDATE doctor SET slug = :slug WHERE id = :doctorId", nativeQuery = true)
    void updateSlug(@Param("slug") String slug, @Param("doctorId") Long doctorId);

    boolean existsBySlug(String slug);

    Doctor findBySlug(String slug);

    @Query(value = "SELECT slug FROM doctor WHERE id=:id", nativeQuery = true)
    String findSlugById(@Param("id") Long doctorId);
}
