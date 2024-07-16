package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.interlab.security.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{

    User findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users SET password = :password WHERE phone_number= :phone", nativeQuery = true)
    void updatePassword(@Param("password") String newPassword, @Param("phone") String phone);
}
