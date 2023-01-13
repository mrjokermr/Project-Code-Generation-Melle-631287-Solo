package io.swagger.service;

import io.swagger.Security.JwtTokenProvider;
import io.swagger.mapper.UserMapper;
import io.swagger.model.*;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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

    @Autowired
    private BankAccountService bankAccountService;

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

    public User GetUserInfoById(Integer id) {
        try {
            return userRepository.findById(id).get();
        }
        catch(Exception e) {
            return null;
        }
    }

    public void SaveUser(User u) {
        userRepository.save(u);
    }

    public Integer GetCurrentAuthorizedUserId() {
        return GetCurrentUserByAuthorization().getId();
    }

    public Boolean AuthUserIsOwnerOfThisIban(String iban) {
        BankAccount bankAccount = bankAccountService.GetBankAccountByIban(iban);
        User authorizedUser = GetCurrentUserByAuthorization();

        if(bankAccount.getOwnerId().equals(authorizedUser.getId())) return true;
        else return false;
    }
    public Boolean CustomerIsExecutingApiCallThatIsNotTargetedForHimself(Integer targetId) {
        if(targetId < 0) return true;

        User userMakingTheCall = GetCurrentUserByAuthorization();

        if(userMakingTheCall == null) return true;
        //this line below is for making sure that the current user is not an employee or admin because they can allways execute a action for another user
        else if(userMakingTheCall.getUserType().equals(UserAccountType.ROLE_EMPLOYEE) || userMakingTheCall.getUserType().equals(UserAccountType.ROLE_BANKADMIN)) return false;
        else if(targetId != null && targetId.equals(userMakingTheCall.getId())) {
            return false;
        }
        else return true;
    }

    public Boolean CustomerIsExecutingApiCallThatIsNotTargetedForHimself(String targetIban) {
        User userMakingTheCall = GetCurrentUserByAuthorization();

        if(userMakingTheCall == null) return true;
            //this line below is for making sure that the current user is not an employee or admin because they can allways execute a action for another user
        else if(userMakingTheCall.getUserType().equals(UserAccountType.ROLE_EMPLOYEE) || userMakingTheCall.getUserType().equals(UserAccountType.ROLE_BANKADMIN)) return false;
        else if(targetIban != null) {
            //get ibans for the user
            List<BankAccount> bankAccountsOwnedByCustomer = bankAccountService.GetAllBankAccountsForUser(userMakingTheCall.getId());

            for(BankAccount ba : bankAccountsOwnedByCustomer) {
                //the ibans match so the user performing is performing an action with his own Iban
                if(ba.getIban().equals(targetIban)) return false;

            }

            return true; //the targeted IBAN doesn't match the customers IBAN(s)
        }
        else return true;
    }

    public User GetCurrentUserByAuthorization() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserName = authentication.getName();

            return userRepository.findByUsername(currentUserName);
        }
        catch(Exception e) { return null; }

    }

    public List<User> GetMatchingUsersByFirstAndLastName(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastNameLike(firstName, lastName);
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

    public Boolean UsernameIsUnique(String username) {
        if(userRepository.findByUsername(username) == null) return true;
        else return false;
    }

    public User UpdateUserInfoByBody(UpdateUserRequestDTO body) {
        try {
            User targetUser = userRepository.findById(body.getTargetUserId()).get();
            if(targetUser == null) return null;
            else {
                if(!body.getFirstName().isEmpty()) targetUser.setFirstName(body.getFirstName());
                if(!body.getLastName().isEmpty()) targetUser.setLastName(body.getLastName());
                if(body.getDayLimit() != null) targetUser.setDayLimit(body.getDayLimit());
                if(body.getTransactionLimit() != null && body.getTransactionLimit() >= 0) targetUser.setTransactionLimit(body.getTransactionLimit());

                userRepository.save(targetUser);
                return targetUser;
            }


        }
        catch(Exception e) { return null; }
    }

    public User RegisterUserByAEmployee(NewUserEmployeeRequestDTO body) {
        try {
            User u = new User();
            u.setDayLimit(body.getDayLimit());
            u.setFirstName(body.getFirstName());
            u.setLastName(body.getLastName());
            u.setPassword(body.getPassword());
            u.setTransactionLimit(body.getTransactionLimit());
            u.setUsername(body.getUsername());
            u.setUserType(body.getUserType());
            u.setCreationDate(new Date());

            userRepository.save(u);
            return u;
        }
        catch(Exception e) { return null; }
    }

    public User RegisterUserAsCustomer(NewUserRequestDTO body) {
        try {
            User u = new User();
            u.setDayLimit(body.getDayLimit());
            u.setFirstName(body.getFirstName());
            u.setLastName(body.getLastName());
            u.setPassword(body.getPassword());
            u.setTransactionLimit(body.getTransactionLimit());
            u.setUsername(body.getUsername());
            u.setUserType(UserAccountType.ROLE_CUSTOMER);
            u.setCreationDate(new Date());

            userRepository.save(u);
            return u;
        }
        catch(Exception e) { return null; }

    }
    public List<User> GetAllUsers(Boolean onlyUsersWithoutABankAccount) {
        List<User> allUsers = userRepository.findAll();
        allUsers.removeIf(u -> u.getUserType().equals(UserAccountType.ROLE_BANKADMIN)); //remove the bank account owner user from the list

        if(!onlyUsersWithoutABankAccount) return allUsers;
        else {
            List<User> usersWithoutABankAccount = new ArrayList<>();
            for(User u : allUsers) if(bankAccountService.GetAllBankAccountsForUser(u.getId()).size() == 0) usersWithoutABankAccount.add(u);

            return usersWithoutABankAccount;
        }
    }

}
