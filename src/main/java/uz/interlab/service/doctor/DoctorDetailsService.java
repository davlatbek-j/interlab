package uz.interlab.service.doctor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.interlab.entity.doctor.DoctorDetails;
import uz.interlab.payload.ApiResponse;
import uz.interlab.respository.DoctorDetailsRepository;
import uz.interlab.respository.DoctorRepository;

@RequiredArgsConstructor

@Service
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
}
