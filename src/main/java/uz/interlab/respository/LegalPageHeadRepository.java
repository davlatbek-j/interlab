package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.interlab.entity.legal.LegalPageHead;

@Repository
public interface LegalPageHeadRepository extends JpaRepository<LegalPageHead, Long> {

    @Modifying
    @Query(value = "update legal_page_head set active=:active where id=:id", nativeQuery = true)
    void changeActive(@Param("id")Long id, boolean active);

    @Query(value = "select photo_url from legal_page_head where id=:id", nativeQuery = true)
    String findPhotoUrlById(@Param("id")Long id);

}
