package uz.interlab.sms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface OTPRepository extends JpaRepository<OTP, Long>
{
    boolean existsByPhone(String phone);

    @Query(value = "SELECT created_at FROM otp WHERE phone= :phone", nativeQuery = true)
    Date findCreatedAtByPhone(String phone);

    @Query(value = "SELECT otp_code FROM otp WHERE phone= :phone", nativeQuery = true)
    Integer findOtpCodeByPhone(String phone);

    void deleteByPhone(String phone);

    @Query(value = "SELECT id FROM otp WHERE phone= :phone", nativeQuery = true)
    Long findIdByPhone(String phone);
}
