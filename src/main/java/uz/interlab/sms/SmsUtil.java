package uz.interlab.sms;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import uz.interlab.exception.LotOfEffortInMinuteException;

import java.util.Date;
import java.util.Objects;
import java.util.Random;

@RequiredArgsConstructor
@Service
@Transactional
public class SmsUtil
{
    private final SmsJPAService smsJPAService;
    private final RestTemplate restTemplate;
    private final OTPRepository otpRepo;
    private final Random random;

    @Value("${eskiz.login}")
    String login;

    @Value("${eskiz.password}")
    String password;

    public LoginResponse login(String email, String password)
    {
        String url = "https://notify.eskiz.uz/api/auth/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("email", email);
        body.add("password", password);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<LoginResponse> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, LoginResponse.class);
        System.err.println("Login response from eskiz.uz: " + response.getBody());
        smsJPAService.saveToken(response.getBody());
        return response.getBody();
    }

    public SendResponse sendSms(String phone, String from)
    {
        checkTimeBreak(phone);

        int i = 100000 + random.nextInt(900000);
        System.err.println("i = " + i);

        String url = "https://notify.eskiz.uz/api/message/sms/send";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        String token;
        try
        {
            token = smsJPAService.getToken();
        } catch (IndexOutOfBoundsException ex)
        {
            token = login(login, password).getData().getToken();
        }

        headers.set("Authorization", "Bearer " + token);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("mobile_phone", phone);
        body.add("message", "This is test from Eskiz");
//        body.add("message", "Confirmation code for interlab.uz:\n" + i);
        body.add("from", Objects.requireNonNullElse(from, "4546"));
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<SendResponse> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, SendResponse.class);

        System.err.println("Send Sms response = " + response.getBody());

        if (response.getStatusCode() == HttpStatus.OK)
        {
            OTP otp = new OTP();
            otp.setOtpCode(i);
            otp.setPhone(phone);
            if (otpRepo.existsByPhone(phone))
                otp.setId(otpRepo.findIdByPhone(phone));
            otpRepo.save(otp);
            return response.getBody();
        } else if (response.getStatusCode() == HttpStatus.UNAUTHORIZED)
        {
            login(login, password);
            ResponseEntity<SendResponse> response2 = restTemplate.exchange(url, HttpMethod.POST, requestEntity, SendResponse.class);
            if (response.getStatusCode() == HttpStatus.OK) return response.getBody();
            else System.out.println(response2);
            throw new RuntimeException("Error sending SMS");
        }
        return null;
    }

    private void checkTimeBreak(String phone) // User can be received SMS code only once within 60 seconds
    {
        if (phone.equals("+998944422901") || phone.equals("+998901611476") || phone.equals("+998900968507")) // access for bechara developers number
            return;
        if (otpRepo.existsByPhone(phone))
        {
            Date createdAtByPhone = otpRepo.findCreatedAtByPhone(phone);
            Date now = new Date();
            if (now.getTime() - createdAtByPhone.getTime() < 60000)
                throw new LotOfEffortInMinuteException("SMS code can be received only once within 60 seconds");
        }
    }

    public boolean match(String phoneNumber, Integer sms)
    {
        Date createdAtByPhone = otpRepo.findCreatedAtByPhone(phoneNumber);
        if (createdAtByPhone == null)
            return false;

        Date now = new Date();
        return sms.equals(otpRepo.findOtpCodeByPhone(phoneNumber)) && now.getTime() - createdAtByPhone.getTime() < 60000;
    }

    public void clear(String phone)
    {
        otpRepo.deleteByPhone(phone);
    }
}