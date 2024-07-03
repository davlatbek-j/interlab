package uz.interlab.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.interlab.entity.Instruction;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.InstructionDto;
import uz.interlab.respository.InstructionRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class InstructionService {


    private final InstructionRepository instructionRepository;

    public ResponseEntity<ApiResponse<Instruction>> create(Instruction instruction){
        ApiResponse<Instruction> response=new ApiResponse<>();
        instruction.setId(null);
        Instruction save = instructionRepository.save(instruction);
        response.setData(save);
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<InstructionDto>> findById(Long id, String lang) {
        ApiResponse<InstructionDto> response = new ApiResponse<>();
        if (instructionRepository.findById(id).isEmpty()) {
            response.setMessage("Instruction not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Instruction instruction = instructionRepository.findById(id).get();
        response.setMessage("Found");
        response.setData(new InstructionDto(instruction, lang));
        return ResponseEntity.status(200).body(response);
    }

        public ResponseEntity<ApiResponse<List<InstructionDto>>> findAll(String lang) {
        ApiResponse<List<InstructionDto>> response = new ApiResponse<>();
        List<Instruction> all = instructionRepository.findAll();
        response.setData(new ArrayList<>());
        all.forEach(instruction -> response.getData().add(new InstructionDto(instruction, lang)));
        response.setMessage("Found " + all.size() + " Instruction(s)");
        return ResponseEntity.status(200).body(response);
    }

        public ResponseEntity<ApiResponse<Instruction>> findById(Long id) {
        ApiResponse<Instruction> response = new ApiResponse<>();
        if (instructionRepository.findById(id).isEmpty()) {
            response.setMessage("Instruction not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Instruction instruction = instructionRepository.findById(id).get();
        response.setMessage("Found");
        response.setData(instruction);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Instruction>> update(Long id, Instruction updateInstruction){
        ApiResponse<Instruction> response=new ApiResponse<>();
        if (instructionRepository.findById(id).isEmpty()){
            response.setMessage("Instruction not found by id: "+id);
            return ResponseEntity.status(404).body(response);
        }
        Instruction instruction = instructionRepository.findById(id).get();
        instruction.setId(id);
        instruction.setNameUz(updateInstruction.getNameUz());
        instruction.setNameRu(updateInstruction.getNameRu());
        instruction.setOptions(updateInstruction.getOptions());
        Instruction save = instructionRepository.save(instruction);
        response.setData(save);
        return ResponseEntity.status(201).body(response);

    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (instructionRepository.findById(id).isEmpty()) {
            response.setMessage("Instruction not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        response.setMessage("Successfully deleted");
        instructionRepository.deleteById(id);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> changeActive(Long id) {
        ApiResponse<?> response = new ApiResponse<>();
        if (instructionRepository.findById(id).isEmpty()) {
            response.setMessage("Instruction not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Instruction instruction = instructionRepository.findById(id).get();
        boolean active = !instruction.isActive();
        instructionRepository.changeActive(id, active);
        response.setMessage("Successfully changed! Instruction active: " + active);
        return ResponseEntity.status(200).body(response);
    }

}