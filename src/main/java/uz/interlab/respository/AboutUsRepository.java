package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.interlab.entity.AboutUs;

import java.util.Optional;

@Repository
public interface AboutUsRepository extends JpaRepository<AboutUs, Long> {

    @Query(value = "select photo_url from about_us where id=:id", nativeQuery = true)
    String findPhotoUrlById(@Param("id")Long id);

    @Modifying
    @Query(value = "update about_us set active=:active where id=:id", nativeQuery = true)
    void changeActive(@Param("id")Long id, boolean active);

    Optional<AboutUs> findTopByOrderByIdDesc();

}
