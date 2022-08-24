package com.evo.qualitanceProject.security.service;

import com.evo.qualitanceProject.model.AppUser;
import com.evo.qualitanceProject.model.AuthenticationProvider;
import com.evo.qualitanceProject.model.RoleName;
import com.evo.qualitanceProject.repository.UserRepository;
import com.evo.qualitanceProject.security.model.VerificationToken;
import com.evo.qualitanceProject.security.repository.VerificationTokenRepository;
import com.evo.qualitanceProject.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private JavaMailSender mailSender;
    final UserRepository userRepository;

    final VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public JwtUserDetailsService(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository) {
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findUserByUsername(username);
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(user.getRole().name()));
        boolean locked = !(user.isBanned());

        return new User(user.getUsername(), user.getPassword(),
                user.isEnabled(),
                true, true, locked,
                authorityList);
    }

    public UserDetails createUserDetails(String username, String password) {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(RoleName.USER.name()));
        return new User(username, password, authorityList);
    }

    private void confirmRegistration(AppUser user, String appUrl) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, user);
        verificationTokenRepository.save(verificationToken);

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl
                = appUrl + "/registrationConfirm?token=" + token;
        String message = "Activate account by following the link: ";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + confirmationUrl);
        mailSender.send(email);
    }

    public ResponseEntity<?> activateUser(String token) {
        System.out.println("confirmRegistration - received token: " + token);
        Map<String, Object> responseMap = new HashMap<>();

        VerificationToken verificationToken = verificationTokenRepository.findVerificationTokenByToken(token);
        if (verificationToken == null) {
            responseMap.put("error", true);
            responseMap.put("message", "Error: invalid token");
            return ResponseEntity.status(500).body(responseMap);
        }

        AppUser user = verificationToken.getUser();
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            responseMap.put("error", true);
            responseMap.put("message", "Error: token expired");
            return ResponseEntity.status(500).body(responseMap);
        }
        user.setEnabled(true);
        userRepository.save(user);

        //todo: delete verif. token?
        verificationTokenRepository.delete(verificationToken);
        responseMap.put("error", false);
        responseMap.put("message", "Account activated successfully.");
        return ResponseEntity.ok(responseMap);
    }

    public ResponseEntity<?> registerUser(String firstName, String lastName, String userName, String email
            , String password, HttpServletRequest request) {
        StringBuffer appUrl = request.getRequestURL();
        System.out.println("***********" + appUrl);
        Map<String, Object> responseMap = new HashMap<>();
        AppUser user = new AppUser();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setRole(RoleName.USER);
        user.setAuthProvider(AuthenticationProvider.LOCAL);
        user.setUsername(userName);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("error", true);
            responseMap.put("message", "Username/email Taken");
            return ResponseEntity.status(500).body(responseMap);
        }

        try {
            confirmRegistration(user, "http://localhost:4200/auth/register");
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("error", true);
            responseMap.put("message", "Error in sending email");
            return ResponseEntity.status(500).body(responseMap);

        }

        responseMap.put("error", false);
        responseMap.put("username", userName);
        responseMap.put("message", "Account created successfully. Please check your email to activate the account.");
        return ResponseEntity.ok(responseMap);
    }

    public String generateToken(String email) {

        AppUser appUser = userRepository.findUserByEmail(email);
        UserDetails userDetails = loadUserByUsername(appUser.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }

}
