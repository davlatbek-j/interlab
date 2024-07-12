package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.interlab.entity.ProfileMyAddress;

@Repository
public interface ProfileMyAddressRepository extends JpaRepository<ProfileMyAddress, Long> {

    @Modifying
    @Query(value = "update profile_my_address set active=:active where id=:id", nativeQuery = true)
    void changeActive(@Param("id")Long id, boolean active);

}
