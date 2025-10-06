package ucd.comp3013j.ems.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ucd.comp3013j.ems.model.Account;
import ucd.comp3013j.ems.model.repos.AdminRepository;
import ucd.comp3013j.ems.model.repos.OrganiserRepository;
import ucd.comp3013j.ems.model.repos.UserRepository;
import ucd.comp3013j.ems.websecurity.AccountWrapper;

@Service
public class LoginService implements UserDetailsService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrganiserRepository organiserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("trying to load account with username " + username);
        Account user = adminRepository.findByEmail(username);
        if (user == null) {
            user = userRepository.findByEmail(username);
        }
        if (user == null) {
            user = organiserRepository.findByEmail(username);
        }
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return new AccountWrapper(user);
//        return new User(user.getEmail(), bCryptPasswordEncoder.encode(user.getPassword()), mapRolesToAuthorities(user.getRole()));
    }
}
