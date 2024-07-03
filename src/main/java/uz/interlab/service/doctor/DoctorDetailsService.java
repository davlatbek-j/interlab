package uz.interlab.service.doctor;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.interlab.entity.doctor.DoctorDetails;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.doctor.DoctorDetailsDTO;
import uz.interlab.respository.DoctorDetailsRepository;
import uz.interlab.respository.DoctorRepository;

@RequiredArgsConstructor

@Service
@Transactional
public class DoctorDetailsService
{
    private final DoctorRepository doctorRepo;
    private final DoctorDetailsRepository detailsRepo;

    public ResponseEntity<ApiResponse<DoctorDetails>> create(Long doctorId, DoctorDetails details)
    {
        ApiResponse<DoctorDetails> response = new ApiResponse<>();

        if (!doctorRepo.existsById(doctorId))
        {
            response.setMessage("Doctor not found by id: " + doctorId);
            return ResponseEntity.status(404).body(response);
        }

        DoctorDetails save = detailsRepo.save(details);
        doctorRepo.setDetailsId(doctorId, save.getId());

        response.setMessage("Created");
        response.setData(save);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<DoctorDetailsDTO>> findByDoctorId(Long doctorId, String lang)
    {
        ApiResponse<DoctorDetailsDTO> response = new ApiResponse<>();
        if (!doctorRepo.existsById(doctorId))
        {
            response.setMessage("Doctor not found by id: " + doctorId);
            return ResponseEntity.status(404).body(response);
        }
        Long detailsId = doctorRepo.findDetailsId(doctorId);
        DoctorDetails doctorDetails = detailsRepo.findById(detailsId).get();
        response.setMessage("Fund");
        response.setData(new DoctorDetailsDTO(doctorDetails, lang));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<DoctorDetails>> update(Long doctorId, DoctorDetails doctorDetails)
    {
        ApiResponse<DoctorDetails> response = new ApiResponse<>();
        if (!doctorRepo.existsById(doctorId))
        {
            response.setMessage("Doctor not found by id: " + doctorId);
            return ResponseEntity.status(404).body(response);
        }
        Long detailsId = doctorRepo.findDetailsId(doctorId);

        DoctorDetails newDoctorDetails = detailsRepo.save(doctorDetails);
        newDoctorDetails.setId(detailsId);

        detailsRepo.save(newDoctorDetails);

        response.setMessage("Updated");
        response.setData(newDoctorDetails);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> delete(Long doctorId)
    {
        ApiResponse<?> response = new ApiResponse<>();
        if (!doctorRepo.existsById(doctorId))
        {
            response.setMessage("Doctor not found by id: " + doctorId);
            return ResponseEntity.status(404).body(response);
        }
        Long detailsId = doctorRepo.findDetailsId(doctorId);
        detailsRepo.deleteById(detailsId);
        response.setMessage("Deleted");
        return ResponseEntity.ok(response);
    }

}
