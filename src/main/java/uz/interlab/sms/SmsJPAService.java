package uz.interlab.sms;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SmsJPAService
{
    private final SmsRepository smsRepository;

    public Long saveToken(LoginResponse response)
    {
        String token = response.getData().getToken();
        SmsToken smsToken = new SmsToken(token);
        List<SmsToken> all = smsRepository.findAll();
        if (all.isEmpty())
        {
            System.out.println("Token saved " + smsToken);
            return smsRepository.save(smsToken).getId();
        } else
        {
            Long id = all.get(0).getId();
            smsToken.setId(id);
            System.out.println("Token saved " + smsToken);
            return smsRepository.save(smsToken).getId();
        }
    }

    public String getToken()
    {
        List<SmsToken> all = smsRepository.findAll();
        if (all.isEmpty())
        {

        }
        return all.get(0).getToken();
    }

}
