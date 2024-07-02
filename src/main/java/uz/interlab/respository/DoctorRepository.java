package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.interlab.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>
{
    @Query(value = "SELECT photo_url from doctor where id = :id", nativeQuery = true)
    String findPhotoUrlById(@Param("id") Long id);
}
