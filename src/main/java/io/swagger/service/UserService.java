package io.swagger.service;

import io.swagger.Security.JwtTokenProvider;
import io.swagger.mapper.UserMapper;
import io.swagger.model.LoginResponseDTO;
import io.swagger.model.User;
import io.swagger.model.UserAccountType;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User foundUser = userRepository.findByUsername(s);

        if(foundUser == null) {
            throw new UsernameNotFoundException("User '" + s + "' not found!");
        }
        else {
            return org.springframework.security.core.userdetails.User
                    .withUsername(s)
                    .password(foundUser.getPassword())
                    .authorities(foundUser.getUserType())
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();
        }
    }

    public LoginResponseDTO Login(String username, String password) {
        String token = "";
        try {
            User foundUser = userRepository.findByUsername(username);

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            token = jwtTokenProvider.createToken(username, userRepository.findByUsername(username).GetUserAccountTypeAsList());

            LoginResponseDTO lrDTO = new LoginResponseDTO();
            lrDTO.setToken(token);
            lrDTO.setUser(UserMapper.UserToUserResponseDTO(foundUser));

            return lrDTO;

        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid username/password");
        }

    }
    public User TestNewUserAdd() {
        User u = new User();
        u.setUsername("CarolinaVeldman2022");
        u.transactionLimit(30.0);
        u.setCreationDate(new Date());
        u.setDayLimit(10);
        u.setFirstName("Carolina");
        u.setLastName("Veldman");
        u.setUserType(UserAccountType.ROLE_EMPLOYEE);
        u.setPassword(passwordEncoder.encode("geheim"));

        userRepository.save(u);

        return u;
    }

    public List<User> GetAllUsers() {
        return userRepository.findAll();
    }

}
