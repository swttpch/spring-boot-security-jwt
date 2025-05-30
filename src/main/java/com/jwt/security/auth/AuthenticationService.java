package com.jwt.security.auth;

import com.jwt.security.config.JwtService;
import com.jwt.security.user.Role;
import com.jwt.security.user.User;
import com.jwt.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	public AutheticationResponse register(RegisterRequest request) {
		var user = User
				.builder()
				.firstName(request.getFirstname())
				.lastName(request.getLastname())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.USER)
				.build();
		repository.save(user);
		var jwtToken = jwtService.generateToken(user);
		return AutheticationResponse.builder().token(jwtToken).build();
	}

	public AutheticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassoword()
				)
		);
		var user = repository.findByEmail(request.getEmail()).orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		return AutheticationResponse.builder().token(jwtToken).build();
	}
}
