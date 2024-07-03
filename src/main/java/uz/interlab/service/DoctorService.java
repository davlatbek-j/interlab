package uz.interlab.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.Doctor;
import uz.interlab.entity.Instruction;
import uz.interlab.entity.InstructionOption;
import uz.interlab.entity.Photo;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.DoctorDTO;
import uz.interlab.payload.InstructionDto;
import uz.interlab.payload.InstructionOptionDto;
import uz.interlab.respository.DoctorRepository;
import uz.interlab.respository.InstructionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class DoctorService
{

    private final DoctorRepository doctorRepo;
    private final PhotoService photoService;
    private final ObjectMapper jsonMapper;


    public ResponseEntity<ApiResponse<Doctor>> create(String strDoctor, MultipartFile photoFile)
    {
        ApiResponse<Doctor> response = new ApiResponse<>();
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            Doctor doctor = mapper.readValue(strDoctor, Doctor.class);
            doctor.setId(null);

            Photo photo = photoService.save(photoFile);

            doctor.setPhotoUrl(photo.getHttpUrl());
            Doctor save = doctorRepo.save(doctor);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e)
        {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    public ResponseEntity<ApiResponse<DoctorDTO>> findById(Long id, String lang)
    {
        ApiResponse<DoctorDTO> response = new ApiResponse<>();
        if (!doctorRepo.existsById(id))
        {
            response.setMessage("Doctor not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Doctor doctor = doctorRepo.findById(id).get();
        response.setMessage("Found");
        response.setData(new DoctorDTO(doctor, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<DoctorDTO>>> findAll(String lang)
    {
        ApiResponse<List<DoctorDTO>> response = new ApiResponse<>();
        List<Doctor> all = doctorRepo.findAll();
        response.setData(new ArrayList<>());
        all.forEach(i -> response.getData().add(new DoctorDTO(i, lang)));
        response.setMessage("Found " + all.size() + " doctor(s)");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Doctor>> findById(Long id)
    {
        ApiResponse<Doctor> response = new ApiResponse<>();
        if (!doctorRepo.existsById(id))
        {
            response.setMessage("Doctor not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        Doctor doctor = doctorRepo.findById(id).get();
        response.setMessage("Found");
        response.setData(doctor);
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Doctor>> update(Long id, String newJson, MultipartFile newPhoto)
    {
        ApiResponse<Doctor> response = new ApiResponse<>();
        if (!doctorRepo.existsById(id))
        {
            response.setMessage("Doctor not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        String oldPhotoUrl = doctorRepo.findPhotoUrlById(id);
        Doctor newDoctor = new Doctor();
        try
        {
            if (newJson != null)
            {
                newDoctor = jsonMapper.readValue(newJson, Doctor.class);
                if (newPhoto == null || !(newPhoto.getSize() > 0))
                    newDoctor.setPhotoUrl(oldPhotoUrl);
                newDoctor.setId(id);
            } else
                newDoctor = doctorRepo.findById(id).get();

            if (newPhoto != null && newPhoto.getSize() > 0)
            {
                Photo photo = photoService.save(newPhoto);
                newDoctor.setPhotoUrl(photo.getHttpUrl());
            }
            Doctor save = doctorRepo.save(newDoctor);
            response.setData(save);
            return ResponseEntity.status(201).body(response);
        } catch (JsonProcessingException e)
        {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }

    public ResponseEntity<ApiResponse<?>> deleteById(Long id)
    {
        ApiResponse<?> response = new ApiResponse<>();
        if (!doctorRepo.existsById(id))
        {
            response.setMessage("Doctor not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        response.setMessage("Successfully deleted");
        doctorRepo.deleteById(id);
        return ResponseEntity.status(200).body(response);
    }



}
