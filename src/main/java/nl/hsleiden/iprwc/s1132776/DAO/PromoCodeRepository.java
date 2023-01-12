package nl.hsleiden.iprwc.s1132776.DAO;

import nl.hsleiden.iprwc.s1132776.model.database.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromoCodeRepository extends JpaRepository<PromoCode, String> {
}
