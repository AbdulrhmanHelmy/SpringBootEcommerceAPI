package com.helmy.ecommerce.Service.User;

import com.helmy.ecommerce.DTO.UserDTO;
import com.helmy.ecommerce.Exeption.ResourceNotFound;
import com.helmy.ecommerce.Model.Session;
import com.helmy.ecommerce.Model.User;
import com.helmy.ecommerce.Repository.SessionRepository;
import com.helmy.ecommerce.Repository.UserRepository;
import com.helmy.ecommerce.Service.EmailVerificationService;
import com.helmy.ecommerce.Util.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final SessionRepository sessionRepository;
    private final EmailVerificationService emailVerificationService;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTUtil jwtUtil, SessionRepository sessionRepository, EmailVerificationService emailVerificationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.sessionRepository = sessionRepository;
        this.emailVerificationService = emailVerificationService;
    }

    @Override
    public UserDTO Register(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setUsername(userDTO.getUsername());
        userRepository.save(user);
        emailVerificationService.createAndSendToken(user);
        return userDTO;
    }

    @Override
    public String Login(UserDTO userDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
        User user = userRepository.findByEmail(userDTO.getEmail()).orElseThrow(
                () -> new ResourceNotFound("User Not Found"));
        if (!user.getIsVerified()) {
            throw new RuntimeException("Account is not verified");
        }

        String token = jwtUtil.generateToken(user);
        Session session = new Session();
        session.setUser(user);
        session.setToken(token);
        session.setStartTime(LocalDateTime.now());
        session.setEndTime(LocalDateTime.now().plusHours(24));
        sessionRepository.save(session);
        return token;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof User) {
            return (User) principal;
        }

        throw new RuntimeException("Principal is not of type User: " + principal.getClass());
    }

}