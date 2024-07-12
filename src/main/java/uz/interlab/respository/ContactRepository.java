package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.interlab.entity.legal.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {



}
