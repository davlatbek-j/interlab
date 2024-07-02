package uz.interlab.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration
{

    @Bean
    public ObjectMapper objectMapper()
    {
        return new ObjectMapper();
    }
}
