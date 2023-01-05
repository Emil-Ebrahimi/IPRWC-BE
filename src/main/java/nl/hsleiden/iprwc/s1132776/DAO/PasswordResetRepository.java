package nl.hsleiden.iprwc.s1132776.DAO;

import nl.hsleiden.iprwc.s1132776.model.database.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, String> {

    PasswordReset getPasswordResetByEmail(String email);

    Optional<PasswordReset> getPasswordResetByCode(String code);
}
