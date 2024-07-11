package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.interlab.entity.Address;
@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

}
