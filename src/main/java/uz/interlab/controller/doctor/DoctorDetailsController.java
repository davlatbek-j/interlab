package uz.interlab.controller.doctor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.interlab.entity.doctor.DoctorDetails;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.doctor.DoctorDetailsDTO;
import uz.interlab.service.doctor.DoctorDetailsService;

@RequiredArgsConstructor

@Controller
@RequestMapping("/doctor-details")
public class DoctorDetailsController
{
    private final DoctorDetailsService detailsService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<DoctorDetails>> create(
            @RequestBody DoctorDetails details,
            @RequestParam(value = "doctor-id") Long doctorId)
    {
        return detailsService.create(doctorId, details);
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<DoctorDetailsDTO>> getByDoctorId(
            @RequestParam("doctor-id") Long doctorId,
            @RequestParam("lang") String lang)
    {
        return detailsService.findByDoctorId(doctorId, lang);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<DoctorDetails>> update(
            @RequestParam("doctor-id") Long doctorId,
            @RequestBody DoctorDetails newDoctorDetails)
    {
        return detailsService.update(doctorId, newDoctorDetails);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<?>> delete(
            @RequestParam("doctor-id") Long doctorId)
    {
        return detailsService.delete(doctorId);
    }
}
