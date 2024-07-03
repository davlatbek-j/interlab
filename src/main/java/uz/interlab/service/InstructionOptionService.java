package uz.interlab.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.interlab.entity.InstructionOption;
import uz.interlab.exception.LanguageNotSupportException;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class InstructionOptionService {



    public List<InstructionOption> filterOptionsByLanguage(List<InstructionOption> options, String language) {

        switch (language){
            case "uz"->{
                options.stream()
                        .filter(option -> option.getDescriptionUz() != null && !option.getDescriptionUz().isEmpty())
                        .collect(Collectors.toList());
            }
            case "ru"->{
                options.stream()
                        .filter(option -> option.getDescriptionRU() != null && !option.getDescriptionRU().isEmpty())
                        .collect(Collectors.toList());
            }
            default -> {
                throw new LanguageNotSupportException("Language not supported: " + language);
            }
        }
       return options;
    }
}
