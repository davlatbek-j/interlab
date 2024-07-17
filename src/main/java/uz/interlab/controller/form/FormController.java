package uz.interlab.controller.form;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.interlab.entity.form.LegalPageForm;
import uz.interlab.entity.form.MainPageForm;
import uz.interlab.entity.form.OnlineAppointmentForm;
import uz.interlab.entity.form.DoctorForm;
import uz.interlab.entity.form.VacancyPageForm;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.form.*;
import uz.interlab.service.form.FormService;

@RequiredArgsConstructor

@RestController
@RequestMapping("/form")
public class FormController
{
    private final FormService formService;

    @PostMapping("/main-page")
    public ResponseEntity<ApiResponse<MainPageForm>> createOrUpdateMainPageForm(@RequestBody MainPageForm mainPageForm)
    {
        return formService.createMainPageForm(mainPageForm);
    }

    @GetMapping("/main-page")
    public ResponseEntity<ApiResponse<MainPageFormDTO>> getMainPageForm(
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return formService.getMainPageForm(lang);
    }

    @DeleteMapping("/main-page")
    public ResponseEntity<ApiResponse<?>> deleteMainPageForm()
    {
        return formService.deleteMainPageForm();
    }

    // Online Appointment (Записаться на приём онлайн и получить услугу минуя живую очередь)
    @PostMapping("/appointment")
    public ResponseEntity<ApiResponse<OnlineAppointmentForm>> createOrUpdateAppointmentForm(@RequestBody OnlineAppointmentForm form)
    {
        return formService.createAppointmentForm(form);
    }

    @GetMapping("/appointment")
    public ResponseEntity<ApiResponse<OnlineAppointmentFormDTO>> getAppointmentForm(
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return formService.getAppointmentForm(lang);
    }

    @DeleteMapping("/appointment")
    public ResponseEntity<ApiResponse<?>> deleteAppointmentForm()
    {
        return formService.deleteAppointmentForm();
    }

    //Online registration (Doctor)
    @PostMapping("/doctor")
    public ResponseEntity<ApiResponse<DoctorForm>> createOrUpdateDoctorForm(@RequestBody DoctorForm form)
    {
        return formService.createDoctorForm(form);
    }

    @GetMapping("/doctor")
    public ResponseEntity<ApiResponse<DoctorFormDTO>> getDoctorForm(
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return formService.getDoctorForm(lang);
    }

    @DeleteMapping("/doctor")
    public ResponseEntity<ApiResponse<?>> deleteDoctorForm()
    {
        return formService.deleteDoctorForm();
    }

    // Legal Page form
    @PostMapping("/legal")
    public ResponseEntity<ApiResponse<LegalPageForm>> createOrUpdateLegalForm(@RequestBody LegalPageForm form)
    {
        return formService.createLegalForm(form);
    }

    @GetMapping("/legal")
    public ResponseEntity<ApiResponse<LegalPageFormDTO>> getLegalForm(
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return formService.getLegalForm(lang);
    }

    @DeleteMapping("/legal")
    public ResponseEntity<ApiResponse<?>> deleteLegalForm()
    {
        return formService.deleteLegalForm();
    }

    // Vacancy page form
    @PostMapping("/vacancy")
    public ResponseEntity<ApiResponse<VacancyPageForm>> createOrUpdateVacancyForm(@RequestBody VacancyPageForm form)
    {
        return formService.createVacancyForm(form);
    }

    @GetMapping("/vacancy")
    public ResponseEntity<ApiResponse<VacancyPageFormDTO>> getVacancyPageForm(
            @RequestHeader(value = "Accept-Language") String lang)
    {
        return formService.getVacancyForm(lang);
    }

    @DeleteMapping("/vacancy")
    public ResponseEntity<ApiResponse<?>> deleteVacancyPageForm()
    {
        return formService.deleteVacancyForm();
    }

}
