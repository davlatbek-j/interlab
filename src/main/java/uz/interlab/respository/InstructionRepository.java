package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.interlab.entity.Instruction;

public interface InstructionRepository extends JpaRepository<Instruction,Long> {
}
