package uz.interlab.service.vacancy;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.interlab.entity.vacancy.Vacancy;
import uz.interlab.payload.ApiResponse;
import uz.interlab.payload.vacancy.VacancyDTO;
import uz.interlab.payload.vacancy.VacancyDetailsDTO;
import uz.interlab.respository.VacancyRepository;
import uz.interlab.util.SlugUtil;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor

@Service
public class VacancyService
{
    private final VacancyRepository vacancyRepo;

    public ResponseEntity<ApiResponse<Vacancy>> create(Vacancy vacancy)
    {
        ApiResponse<Vacancy> response = new ApiResponse<>();
        Vacancy save = vacancyRepo.save(new Vacancy());
        Long vacancyId = save.getId();
        vacancy.setSlug(vacancyId + "-" + SlugUtil.makeSlug(vacancy.getNameUz()));
        vacancy.setId(vacancyId);
        response.setData(vacancyRepo.save(vacancy));
        response.setMessage("Created");
        return ResponseEntity.status(201).body(response);
    }

    public ResponseEntity<ApiResponse<VacancyDTO>> getBySlug(String slug, String lang)
    {
        ApiResponse<VacancyDTO> response = new ApiResponse<>();
        if (!vacancyRepo.existsBySlug(slug))
        {
            response.setMessage("Vacancy not found by slug: " + slug);
            return ResponseEntity.status(404).body(response);
        }
        Vacancy bySlug = vacancyRepo.findBySlug(slug);
        response.setMessage("Found");
        response.setData(new VacancyDTO(bySlug, lang));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<List<VacancyDTO>>> getAll(String lang, String main, String active)
    {
        ApiResponse<List<VacancyDTO>> response = new ApiResponse<>();
        List<Vacancy> all = new ArrayList<>();
        response.setData(new ArrayList<>());
        int size = 0;
        switch (main.toLowerCase())
        {
            case "all":
            {
                if (active.equalsIgnoreCase("all"))
                {
                    all = vacancyRepo.findAll();
                    all.forEach(i -> response.getData().add(new VacancyDTO(i, lang)));
                    size = all.size();
                } else if (active.equalsIgnoreCase("true") || active.equalsIgnoreCase("false"))
                {
                    all = vacancyRepo.findAllByActive(Boolean.parseBoolean(active));
                    all.forEach(i -> response.getData().add(new VacancyDTO(i, lang)));
                    size = all.size();
                } else
                    throw new IllegalArgumentException("'active' should be either 'all' or 'true' or 'false'");
                break;
            }
            case "true":
            case "false":
            {
                all = vacancyRepo.findAllByMainAndActive(Boolean.parseBoolean(main), Boolean.parseBoolean(active));
                all.forEach(i -> response.getData().add(new VacancyDTO(i, lang)));
                size = all.size();
                break;
            }
            default:
                throw new IllegalArgumentException("'main' should be either 'all' or 'true' or 'false'");
        }

        response.setMessage("Found " + size + " Vacancies(s)");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Vacancy>> getFullData(String slug)
    {
        ApiResponse<Vacancy> response = new ApiResponse<>();
        if (!vacancyRepo.existsBySlug(slug))
        {
            response.setMessage("Vacancy not found by slug: " + slug);
            return ResponseEntity.status(404).body(response);
        }
        response.setMessage("Found");
        response.setData(vacancyRepo.findBySlug(slug));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<Vacancy>> update(Long id, Vacancy newVacancy)
    {
        ApiResponse<Vacancy> response = new ApiResponse<>();
        if (!vacancyRepo.existsById(id))
        {
            response.setMessage("Vacancy not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        newVacancy.setId(id);
        newVacancy.setSlug(id + "-" + SlugUtil.makeSlug(newVacancy.getNameUz()));
        response.setMessage("Updated");
        response.setData(vacancyRepo.save(newVacancy));
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<?>> delete(Long id)
    {
        ApiResponse<?> response = new ApiResponse<>();
        if (!vacancyRepo.existsById(id))
        {
            response.setMessage("Vacancy not found by id: " + id);
            return ResponseEntity.status(404).body(response);
        }
        vacancyRepo.deleteById(id);
        response.setMessage("Deleted");
        return ResponseEntity.status(200).body(response);
    }

    public ResponseEntity<ApiResponse<VacancyDetailsDTO>> getDetails(String slug, String lang)
    {
        ApiResponse<VacancyDetailsDTO> response = new ApiResponse<>();

        if (!vacancyRepo.existsBySlug(slug))
        {
            response.setMessage("Vacancy not found by slug: " + slug);
            return ResponseEntity.status(404).body(response);
        }
        response.setMessage("Found");
        response.setData(new VacancyDetailsDTO(vacancyRepo.findBySlug(slug), lang));
        return ResponseEntity.status(200).body(response);
    }
}
