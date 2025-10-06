package ucd.comp3013j.ems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ucd.comp3013j.ems.model.Administrator;
import ucd.comp3013j.ems.model.User;
import ucd.comp3013j.ems.model.Organiser;
import ucd.comp3013j.ems.model.repos.AdminRepository;
import ucd.comp3013j.ems.model.repos.UserRepository;
import ucd.comp3013j.ems.model.repos.OrganiserRepository;

@Component
//@Profile("dev")
public class ApplicationRunner implements CommandLineRunner {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrganiserRepository organiserRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public void run(String... args) throws Exception {
//        Administrator admin = new Administrator("admin@ucd.ie","Sean Russell", bCryptPasswordEncoder.encode( "admin"));
//        adminRepository.save(admin);
//        User customer = new User("sean","sean", bCryptPasswordEncoder.encode( "sean"));
//        userRepository.save(customer);
//        Organiser organiser = new Organiser("dave","dave", bCryptPasswordEncoder.encode( "dave"), "ACME 123","123 Fake Street", "0877777777");
//        organiserRepository.save(organiser);
    }
}
