package uz.interlab.respository.instruction;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.interlab.entity.instruction.InstructionDetails;

public interface InstructionDetailsRepository extends JpaRepository<InstructionDetails, Long>
{
}
