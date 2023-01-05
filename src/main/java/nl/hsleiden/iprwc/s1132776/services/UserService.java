package nl.hsleiden.iprwc.s1132776.services;

import nl.hsleiden.iprwc.s1132776.DAO.UserRepository;
import nl.hsleiden.iprwc.s1132776.model.database.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
	 private final UserRepository userRepository;
	 private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User save(User user) {
		String rawPassword = user.getPassword();
		String encodedPassword = passwordEncoder.encode(rawPassword);
		user.setPassword(encodedPassword);
		
		return userRepository.save(user);
	}
}
