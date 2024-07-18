package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.interlab.entity.ResultsPage;

public interface ResultsPageRepository extends JpaRepository<ResultsPage, Long>
{
    @Transactional
    @Modifying
    @Query(value = "UPDATE results_page SET video_url = :url WHERE id = :id", nativeQuery = true)
    void updateVideoUrl(@Param("url") String url, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE results_page SET video_intro_url = :url WHERE id = :id", nativeQuery = true)
    void updateIntroUrl(@Param("url") String url, @Param("id") Long id);

}
