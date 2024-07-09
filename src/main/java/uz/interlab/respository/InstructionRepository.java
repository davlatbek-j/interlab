package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.interlab.entity.instruction.Instruction;

@Repository
public interface InstructionRepository extends JpaRepository<Instruction, Long>
{

    @Modifying
    @Query(value = "update instruction set active=:active where id=:id", nativeQuery = true)
    void changeActive(@Param("id") Long id, boolean active);

    boolean existsBySlug(String slug);

    Instruction findBySlug(String slug);

    @Transactional
    @Modifying
    @Query(value = "update instruction set details_id = :detailsId  where id = :id", nativeQuery = true)
    void updateDetailsId(@Param("detailsId") Long detailsId, @Param("id") Long instructionId);

    @Query(value = "SELECT details_id FROM instruction WHERE slug = :slug", nativeQuery = true)
    Long findDetailsIdBySlug(String slug);

    @Query(value = "SELECT details_id FROM instruction WHERE id = :id", nativeQuery = true)
    Long findDetailsId(@Param("id") Long instructionId);
}
