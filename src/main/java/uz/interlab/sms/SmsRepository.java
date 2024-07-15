package uz.interlab.sms;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsRepository extends JpaRepository<SmsToken, Long>
{
}
