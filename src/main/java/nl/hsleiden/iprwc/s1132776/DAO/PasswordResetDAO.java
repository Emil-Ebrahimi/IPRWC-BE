package nl.hsleiden.iprwc.s1132776.DAO;

import nl.hsleiden.iprwc.s1132776.model.database.PasswordReset;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PasswordResetDAO {

    private PasswordResetRepository passwordResetRepository;

    public PasswordResetDAO(PasswordResetRepository passwordResetRepository) {
        this.passwordResetRepository = passwordResetRepository;
    }

    public String saveToDatabase(PasswordReset passwordReset) {
        PasswordReset temp = this.passwordResetRepository.save(passwordReset);
        return temp.getId();
    }

    public List<PasswordReset> getAll() {
        return this.passwordResetRepository.findAll();
    }

    public Optional<PasswordReset> getPasswordResetByCode(String code) {
        return this.passwordResetRepository.getPasswordResetByCode(code);
    }

    public PasswordReset getByEmail(String email){
        return this.passwordResetRepository.getPasswordResetByEmail(email);
    }

    public void deleteById(String id){
        this.passwordResetRepository.deleteById(id);
    }

    public Optional<PasswordReset> getByToken(String token){
        return this.passwordResetRepository.findById(token);
    }
}
