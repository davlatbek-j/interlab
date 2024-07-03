package uz.interlab.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.interlab.entity.Doctor;
import uz.interlab.entity.Instruction;
import uz.interlab.entity.InstructionOption;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.DoctorDTO;
import uz.interlab.payload.InstructionDto;
import uz.interlab.payload.InstructionOptionDto;
import uz.interlab.respository.InstructionOptionRepository;
import uz.interlab.respository.InstructionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class InstructionService {
    private  final InstructionOptionRepository instructionOptionRepository;
    private final InstructionRepository instructionRepository;
    private final InstructionOptionService instructionOptionService;

    public ResponseEntity<ApiResponse<Instruction>> create(String name, List<InstructionOption> options) {


        ApiResponse<Instruction> response = new ApiResponse<>();

        Instruction instruction = new Instruction();
        instruction.setName(name);


        List<InstructionOption> uzOptions = options.stream()
                .filter(option -> option.getDescriptionUz() != null && !option.getDescriptionUz().isEmpty())
                .toList();

        List<InstructionOption> ruOptions = options.stream()
                .filter(option -> option.getDescriptionRU() != null && !option.getDescriptionRU().isEmpty())
                .toList();


        instructionOptionRepository.saveAll(uzOptions);
        instructionOptionRepository.saveAll(ruOptions);


        instruction.setOption(options);


        Instruction save = instructionRepository.save(instruction);
        response.setData(save);

        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<InstructionDto>> getById(Long id, String language) {
        ApiResponse<InstructionDto> response = new ApiResponse<>();
        if (instructionRepository.existsById(id))
        {
            response.setMessage("Doctor not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Instruction instruction = instructionRepository.findById(id).get();
        List<InstructionOption> instructionOptions = instructionOptionService.filterOptionsByLanguage(instruction.getOption(), language);
        instruction.setOption(instructionOptions);
        response.setMessage("Found");
        response.setData(new InstructionDto(instruction));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<InstructionDto>>> findAll(String lang)
    {
        ApiResponse<List<InstructionDto>> response = new ApiResponse<>();
        List<Instruction> all = instructionRepository.findAll();
        for (Instruction instruction : all) {
            List<InstructionOption> instructionOptions = instructionOptionService.filterOptionsByLanguage(instruction.getOption(), lang);
            instruction.setOption(instructionOptions);
        }
        response.setData(new ArrayList<>());
        all.forEach(i -> response.getData().add(new InstructionDto(i)));
        response.setMessage("Found " + all.size() + " doctor(s)");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<InstructionDto>> updateInstruction(Long id, String name, List<InstructionOption> option) {
        Optional<Instruction> optionalInstruction = instructionRepository.findById(id);

        if (optionalInstruction.isPresent()) {
            Instruction instruction = optionalInstruction.get();
            instruction.setName(name);

            List<InstructionOption> options = new ArrayList<>(option);


            instruction.setOption(options);

            instructionOptionRepository.saveAll(options);

            instructionRepository.save(instruction);

            InstructionDto instructionDto = new InstructionDto(instruction);

            return new ResponseEntity<>(new ApiResponse<>("Instruction updated successfully", instructionDto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse<>("Instruction not found", null), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id)
    {
        ApiResponse<?> response = new ApiResponse<>();
        if (!instructionRepository.existsById(id))
        {
            response.setMessage("Doctor not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        response.setMessage("Successfully deleted");
        instructionRepository.deleteById(id);
        return ResponseEntity.status(200).body(response);
    }
}