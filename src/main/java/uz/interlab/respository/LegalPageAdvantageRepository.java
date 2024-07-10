package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.interlab.entity.legal.LegalPageAdvantage;

@Repository
public interface LegalPageAdvantageRepository extends JpaRepository<LegalPageAdvantage, Long> {

    @Modifying
    @Query(value = "update legal_page_advantage set active=:active where id=:id", nativeQuery = true)
    void changeActive(@Param("id")Long id, boolean active);

}
