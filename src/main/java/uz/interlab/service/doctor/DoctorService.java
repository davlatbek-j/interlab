package uz.interlab.service.doctor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.interlab.entity.Photo;
import uz.interlab.entity.doctor.Doctor;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.doctor.DoctorDTO;
import uz.interlab.respository.DoctorRepository;
import uz.interlab.service.PhotoService;
import uz.interlab.util.SlugUtil;

import java.util.ArrayList;
import java.util.List;

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
            String slug = save.getId() + "-" + SlugUtil.makeSlug(save.getFullNameUz());
            doctorRepo.updateSlug(slug, save.getId());

            save.setSlug(slug);
            response.setMessage("Created");
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

    public ResponseEntity<ApiResponse<List<DoctorDTO>>> findAll(String lang, String main, String active)
    {
        ApiResponse<List<DoctorDTO>> response = new ApiResponse<>();
        List<Doctor> all = new ArrayList<>();
        response.setData(new ArrayList<>());

        switch (main.toLowerCase())
        {
            case "all":
            {
                if (active.equalsIgnoreCase("all"))
                    all = doctorRepo.findAll();
                else if (active.equalsIgnoreCase("true") || active.equalsIgnoreCase("false"))
                    all = doctorRepo.findByActive(Boolean.parseBoolean(active));
                else
                    throw new IllegalArgumentException("'active' should be either 'all' or 'true' or 'false'");

                all.forEach(i -> response.getData().add(new DoctorDTO(i, lang)));
                response.setMessage("Found " + all.size() + " doctor(s)");
                break;
            }
            case "true":
            case "false":
            {
                if (active.equalsIgnoreCase("all"))
                    all = doctorRepo.findByMain(Boolean.parseBoolean(main));
                else if (active.equalsIgnoreCase("true") || active.equalsIgnoreCase("false"))
                    all = doctorRepo.findByMainAndActive(Boolean.parseBoolean(main), Boolean.parseBoolean(active));
                else
                    throw new IllegalArgumentException("'active' should be either 'all' or 'true' or 'false'");

                all.forEach(i -> response.getData().add(new DoctorDTO(i, lang)));
                response.setMessage("Found " + response.getData().size() + " doctor(s)");
                break;
            }
            default:
                throw new IllegalArgumentException("'main' should be either 'all' or 'true' or 'false'");
        }

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
        String slug = doctorRepo.findSlugById(id);
        Doctor newDoctor = new Doctor();
        try
        {
            if (newJson != null)
            {
                newDoctor = jsonMapper.readValue(newJson, Doctor.class);
                if (newPhoto == null || !(newPhoto.getSize() > 0))
                    newDoctor.setPhotoUrl(oldPhotoUrl);
                newDoctor.setId(id);
                newDoctor.setSlug(slug);
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

    public ResponseEntity<ApiResponse<DoctorDTO>> findBySlug(String slug, String lang)
    {
        ApiResponse<DoctorDTO> response = new ApiResponse<>();
        if (!doctorRepo.existsBySlug(slug))
        {
            response.setMessage("Doctor not found by slug: " + slug);
            return ResponseEntity.status(404).body(response);
        }
        Doctor doctor = doctorRepo.findBySlug(slug);
        response.setMessage("Found");
        response.setData(new DoctorDTO(doctor, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Doctor>> findBySlug(String slug)
    {
        ApiResponse<Doctor> response = new ApiResponse<>();
        if (!doctorRepo.existsBySlug(slug))
        {
            response.setMessage("Doctor not found by slug: " + slug);
            return ResponseEntity.status(404).body(response);
        }
        Doctor doctor = doctorRepo.findBySlug(slug);
        response.setMessage("Found");
        response.setData(doctor);
        return ResponseEntity.status(200).body(response);
    }

}
