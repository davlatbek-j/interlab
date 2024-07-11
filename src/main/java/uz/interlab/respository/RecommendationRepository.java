package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.interlab.entity.instruction.Recommendation;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long>
{
}
