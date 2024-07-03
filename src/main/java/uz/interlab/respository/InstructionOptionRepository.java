package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.interlab.entity.InstructionOption;

public interface InstructionOptionRepository extends JpaRepository<InstructionOption,Long> {
}
