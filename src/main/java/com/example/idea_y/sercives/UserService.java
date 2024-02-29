package com.example.idea_y.sercives;

import com.example.idea_y.enums.Role;
import com.example.idea_y.models.Profile;
import com.example.idea_y.models.User;
import com.example.idea_y.repositories.ProfileRepository;
import com.example.idea_y.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    //password shifr
    private final PasswordEncoder passwordEncoder;
    public boolean createUser(User user){
        if(userRepository.findByEmail(user.getEmail())!=null) return false;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        log.info("Saving new User with email:{}", user.getEmail());
        Profile profile=new Profile();
        profile.setEmail(user.getEmail());
        profileRepository.save(profile);
        user.setProfile(profile);
        userRepository.save(user);
        return true;
    }
    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }
}
