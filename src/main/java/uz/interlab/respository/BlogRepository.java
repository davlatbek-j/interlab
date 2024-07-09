package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.interlab.entity.blog.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    @Query(value = "select photo_url from blog where id=:id", nativeQuery = true)
    String findPhotoUrlById(@Param("id")Long id);

    @Modifying
    @Query(value = "update blog set active=:active where id=:id", nativeQuery = true)
    void changeActive(@Param("id")Long id, boolean active);

    @Transactional
    @Modifying
    @Query(value = "UPDATE blog SET slug = :slug WHERE id = :id", nativeQuery = true)
    void updateSlug(@Param("slug") String slug, @Param("id") Long serviceId);

    @Query(value = "SELECT slug FROM blog WHERE id = :id", nativeQuery = true)
    String findSlugById(@Param("id") Long blogId);

}
