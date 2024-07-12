package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.interlab.entity.license.LicensePhoto;

@Repository
public interface LicensePhotoRepository extends JpaRepository<LicensePhoto, Long> {

    @Query(value = "select photo_url from license_photo where id=:id", nativeQuery = true)
    String findPhotoUrlById(@Param("id")Long id);

    @Modifying
    @Query(value = "update license_photo set active=:active where id=:id", nativeQuery = true)
    void changeActive(@Param("id")Long id, boolean active);

}
