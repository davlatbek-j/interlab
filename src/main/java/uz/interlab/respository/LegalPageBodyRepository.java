package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.interlab.entity.legal.LegalPageBody;

@Repository
public interface LegalPageBodyRepository extends JpaRepository<LegalPageBody,Long> {

    @Modifying
    @Query(value = "update legal_page_body set active=:active where id=:id", nativeQuery = true)
    void changeActive(@Param("id")Long id, boolean active);

}
