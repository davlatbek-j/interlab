package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.interlab.entity.instruction.InstructionHead;

public interface InstructionHeadRepository extends JpaRepository<InstructionHead, Long>
{
    @Query(value = "SELECT icon_url FROM instruction_head WHERE id = :id", nativeQuery = true)
    String findIconUrlById(Long id);
}
