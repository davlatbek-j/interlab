package uz.interlab.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.interlab.entity.footer.Footer;

public interface FooterRepository extends JpaRepository<
        Footer, Long>
{
}
