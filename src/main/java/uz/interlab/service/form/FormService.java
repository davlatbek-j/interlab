package uz.interlab.service.form;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.interlab.entity.form.LegalPageForm;
import uz.interlab.entity.form.MainPageForm;
import uz.interlab.entity.form.OnlineAppointmentForm;
import uz.interlab.entity.form.DoctorForm;
import uz.interlab.entity.form.VacancyPageForm;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.form.*;
import uz.interlab.respository.*;

import java.util.Optional;


@RequiredArgsConstructor

@Service
@Transactional
public class FormService
{

    private final MainPageFormRepository mainPageFormRepository;
    private final AppointmentFormRepository appointmentRepository;
    private final DoctorFormRepository doctorFormRepository;
    private final LegalPageFormRepository legalRepository;
    private final VacancyPageFormRepository vacancyRepository;

    public ResponseEntity<ApiResponse<MainPageFormDTO>> getMainPageForm(String lang)
    {
        ApiResponse<MainPageFormDTO> response = new ApiResponse<>();
        Optional<MainPageForm> byId = mainPageFormRepository.findById(1L);
        if (byId.isPresent())
        {
            response.setMessage("Found");
            response.setData(new MainPageFormDTO(byId.get(), lang));
            return ResponseEntity.ok(response);
        }
        response.setMessage("Form Not created yet");
        return ResponseEntity.status(404).body(response);
    }

    public ResponseEntity<ApiResponse<?>> deleteMainPageForm()
    {
        ApiResponse<?> response = new ApiResponse<>();
        if (!mainPageFormRepository.existsById(1L))
        {
            response.setMessage("Form not found or already deleted");
            return ResponseEntity.status(404).body(response);
        }
        mainPageFormRepository.deleteById(1L);
        response.setMessage("Deleted");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<MainPageForm>> createMainPageForm(MainPageForm mainPageForm)
    {
        ApiResponse<MainPageForm> response = new ApiResponse<>();
        mainPageForm.setId(1L);
        response.setData(mainPageFormRepository.save(mainPageForm));
        response.setMessage("Created");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Online appointment
    public ResponseEntity<ApiResponse<OnlineAppointmentForm>> createAppointmentForm(OnlineAppointmentForm form)
    {
        ApiResponse<OnlineAppointmentForm> response = new ApiResponse<>();
        form.setId(1L);
        response.setData(appointmentRepository.save(form));
        response.setMessage("Changes saved");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<OnlineAppointmentFormDTO>> getAppointmentForm(String lang)
    {
        ApiResponse<OnlineAppointmentFormDTO> response = new ApiResponse<>();
        Optional<OnlineAppointmentForm> byId = appointmentRepository.findById(1L);
        if (byId.isPresent())
        {
            response.setMessage("Found");
            response.setData(new OnlineAppointmentFormDTO(byId.get(), lang));
        }
        response.setMessage("Not found or not created yet");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> deleteAppointmentForm()
    {
        ApiResponse<?> response = new ApiResponse<>();
        if (!appointmentRepository.existsById(1L))
        {
            response.setMessage("Form not found or already deleted");
            return ResponseEntity.status(404).body(response);
        }
        appointmentRepository.deleteById(1L);
        response.setMessage("Deleted");
        return ResponseEntity.ok(response);
    }

    // Doctor form
    public ResponseEntity<ApiResponse<DoctorForm>> createDoctorForm(DoctorForm form)
    {
        ApiResponse<DoctorForm> response = new ApiResponse<>();
        form.setId(1L);
        response.setMessage("Changes saved");
        response.setData(doctorFormRepository.save(form));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<DoctorFormDTO>> getDoctorForm(String lang)
    {
        ApiResponse<DoctorFormDTO> response = new ApiResponse<>();
        Optional<DoctorForm> byId = doctorFormRepository.findById(1L);
        if (byId.isEmpty())
        {
            response.setMessage("Form not found or not created");
            return ResponseEntity.status(404).body(response);
        }
        response.setMessage("Found");
        response.setData(new DoctorFormDTO(byId.get(), lang));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> deleteDoctorForm()
    {
        ApiResponse<?> response = new ApiResponse<>();
        if (!doctorFormRepository.existsById(1L))
        {
            response.setMessage("Form not found or already deleted");
            return ResponseEntity.status(404).body(response);
        }
        doctorFormRepository.deleteById(1L);
        response.setMessage("Deleted");
        return ResponseEntity.ok(response);
    }

    // Legal form
    public ResponseEntity<ApiResponse<LegalPageForm>> createLegalForm(LegalPageForm form)
    {
        ApiResponse<LegalPageForm> response = new ApiResponse<>();
        form.setId(1L);
        response.setData(legalRepository.save(form));
        response.setMessage("Changes saved");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<LegalPageFormDTO>> getLegalForm(String lang)
    {
        ApiResponse<LegalPageFormDTO> response = new ApiResponse<>();
        Optional<LegalPageForm> byId = legalRepository.findById(1L);
        if (byId.isEmpty())
        {
            response.setMessage("Form not found or not created");
            return ResponseEntity.status(404).body(response);
        }
        response.setMessage("Found");
        response.setData(new LegalPageFormDTO(byId.get(), lang));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<?>> deleteLegalForm()
    {
        ApiResponse<?> response = new ApiResponse<>();
        if (!legalRepository.existsById(1L))
        {
            response.setMessage("Form not found or already deleted");
            return ResponseEntity.status(404).body(response);
        }
        legalRepository.deleteById(1L);
        response.setMessage("Deleted");
        return ResponseEntity.ok(response);
    }

    // Vacancy
    public ResponseEntity<ApiResponse<VacancyPageForm>> createVacancyForm(VacancyPageForm form)
    {
        ApiResponse<VacancyPageForm> response = new ApiResponse<>();
        form.setId(1L);
        response.setMessage("Changes saved");
        response.setData(vacancyRepository.save(form));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<VacancyPageFormDTO>> getVacancyForm(String lang)
    {
        ApiResponse<VacancyPageFormDTO> response = new ApiResponse<>();
        Optional<VacancyPageForm> byId = vacancyRepository.findById(1L);
        if (byId.isPresent())
        {
            response.setMessage("Found");
            response.setData(new VacancyPageFormDTO(byId.get(), lang));
            return ResponseEntity.ok(response);
        }
        response.setMessage("Not found or not created yet");
        return ResponseEntity.status(404).body(response);
    }

    public ResponseEntity<ApiResponse<?>> deleteVacancyForm()
    {
        ApiResponse<?> response = new ApiResponse<>();
        if (!vacancyRepository.existsById(1L))
        {
            response.setMessage("Form not found or already deleted");
            return ResponseEntity.status(404).body(response);
        }
        response.setMessage("Deleted");
        vacancyRepository.deleteById(1L);
        return ResponseEntity.status(200).body(response);
    }

}
