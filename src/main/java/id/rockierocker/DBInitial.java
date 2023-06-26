package id.rockierocker;

import id.rockierocker.entity.Client;
import id.rockierocker.entity.Role;
import id.rockierocker.entity.User;
import id.rockierocker.repository.ClientRepository;
import id.rockierocker.repository.RoleRepository;
import id.rockierocker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;

@Component
public class DBInitial implements ApplicationRunner {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments applicationArguments) {
        Role role = roleRepository.findFirstByName("ROLE_TEST").orElse(null);
        if(role==null){
            role = new Role();
            role.setName("ROLE_TEST");
            role.setType("user_role");
            roleRepository.save(role);
        }
        role = roleRepository.findFirstByName("ROLE_READ").orElse(null);
        if(role==null){
            role = new Role();
            role.setName("ROLE_READ");
            role.setType("oauth_role");
            roleRepository.save(role);
        }
        role = roleRepository.findFirstByName("ROLE_WRITE").orElse(null);
        if(role==null){
            role = new Role();
            role.setName("ROLE_WRITE");
            role.setType("oauth_role");
            roleRepository.save(role);
        }
        Client client = clientRepository.findFirstByClientId("client-test");
        if(client==null){
            client = new Client();
            client.setClientId("client-test");
            client.setAccessTokenValiditySeconds(28800);
            client.setRefreshTokenValiditySeconds(7257600);
            client.setGrantTypes("password refresh_token authorization_code");
            client.setClientSecret(passwordEncoder.encode("@Password123"));
            client.setApproved(true);
            client.setRedirectUris("");
            client.setScopes("read write");
            client.getAuthorities().addAll(roleRepository.findByNameIn(new String[]{"ROLE_READ","ROLE_WRITE"}));
            clientRepository.save(client);
        }
    }
}
