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

    @GetMapping("/get/{doctor-id}")
    public ResponseEntity<ApiResponse<DoctorDetailsDTO>> getByDoctorId(
            @PathVariable("doctor-id") Long doctorId,
            @RequestHeader("Accept-Language") String lang)
    {
        return detailsService.findByDoctorId(doctorId,lang);
    }

    /*@PutMapping("/update/{doctor-id}")
    public ResponseEntity<ApiResponse<DoctorDetails>> update(
            @PathVariable("doctor-id") Long doctorId,
            @RequestBody DoctorDetails doctorDetails )
    {
        return detailsService.update(doctorId,doctorDetails);
    }

    @DeleteMapping("/delete/{doctor-id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable("doctor-id") Long doctorId)
    {
        return detailsService.delete(doctorId);
    }*/
}
