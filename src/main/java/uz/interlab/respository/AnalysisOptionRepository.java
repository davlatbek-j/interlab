package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.interlab.entity.analysis.AnalysisOption;

import java.util.Optional;

@Repository
public interface AnalysisOptionRepository extends JpaRepository<AnalysisOption, Long> {

    @Modifying
    @Query(value = "update analysis_option set popular=:popular where id=:id", nativeQuery = true)
    void changePopular(@Param("id")Long id, boolean popular);

    Optional<AnalysisOption> findBySlug(String slug);

}
