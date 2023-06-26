package id.rockierocker.service;

import id.rockierocker.dto.RegisterDto;
import id.rockierocker.entity.User;
import id.rockierocker.exception.BadRequest;
import id.rockierocker.repository.RoleRepository;
import id.rockierocker.repository.UserRepository;
import id.rockierocker.util.UsernameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;
    @Override
    public User register(RegisterDto registerDto) {
        Boolean uppercaseFound = false;
        for(char c : registerDto.getPassword().toCharArray()){
            uppercaseFound = Character.isUpperCase(c);
            if(uppercaseFound){
                break;
            }
        }
        if(!uppercaseFound){
            throw new BadRequest("Password mandatory min 6, max 16, containing at least 1 capital letter");
        }
        User findByPhoneNumber = userRepository.findFirstByPhoneNumber(registerDto.getPhoneNumber()).orElse(null);
        if(findByPhoneNumber!=null){
            throw new BadRequest("Phone Number already registered");
        }
        User user = new User();
        user.setPhoneNumber(registerDto.getPhoneNumber());
        user.setName(registerDto.getName());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRoles(roleRepository.findByNameIn(new String[]{"ROLE_TEST"}));
        return userRepository.save(user);
    }

    @Override
    public User updateCurrentName(String newName) {
        String currentPhoneNumber = UsernameUtils.getCurrentUsername();
        User user = userRepository.findFirstByPhoneNumber(currentPhoneNumber).orElseThrow(()->new BadRequest("Phone Number not found"));
        user.setName(newName);
        return userRepository.save(user);
    }

    @Override
    public String getCurrentName() {
        String currentPhoneNumber = UsernameUtils.getCurrentUsername();
        User user = userRepository.findFirstByPhoneNumber(currentPhoneNumber).orElseThrow(()->new BadRequest("Phone Number not found"));
        return user.getName();
    }
}
