package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.interlab.entity.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long>
{
    Photo findByIdOrName(Long id, String name);
}
