package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.interlab.entity.Address;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

    @Modifying
    @Query(value = "update address set active=:active where id=:id", nativeQuery = true)
    void changeActive(@Param("id")Long id, boolean active);

    @Modifying
    @Query(value = "UPDATE address SET slug = :slug WHERE id = :id", nativeQuery = true)
    void updateSlug(@Param("slug") String slug, @Param("id") Long addressId);

    @Query(value = "SELECT slug FROM address WHERE id = :id", nativeQuery = true)
    String findSlugById(@Param("id") Long addressId);

    Optional<Address> findBySlug(String slug);

}
