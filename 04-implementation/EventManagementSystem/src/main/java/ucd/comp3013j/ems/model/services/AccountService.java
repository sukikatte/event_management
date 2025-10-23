package ucd.comp3013j.ems.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucd.comp3013j.ems.model.*;
import ucd.comp3013j.ems.model.dto.ModifyAccountDTO;
import ucd.comp3013j.ems.model.repos.AdminRepository;
import ucd.comp3013j.ems.model.repos.UserRepository;
import ucd.comp3013j.ems.model.repos.OrganiserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final OrganiserRepository organiserRepository;

    @Autowired
    public AccountService(UserRepository userRepository, AdminRepository administratorRepository, OrganiserRepository organizerRepository) {
        this.userRepository = userRepository;
        this.adminRepository = administratorRepository;
        this.organiserRepository = organizerRepository;
    }

    /**
     * 根据账户 ID 获取账户信息
     */
    public Account getAccount(long accountId) {
        Account account = userRepository.findById(accountId);
        if (account == null) {
            account = adminRepository.findById(accountId);
            if (account == null) {
                account = organiserRepository.findById(accountId);
            }
        }
        return account;
    }

    /**
     * 根据邮箱获取账户信息
     */
    public Account getAccount(String email) {
        Account account = userRepository.findByEmail(email);
        if (account == null) {
            account = adminRepository.findByEmail(email);
            if (account == null) {
                account = organiserRepository.findByEmail(email);
            }
        }
        return account;
    }

    /**
     * 创建用户账户
     *
     * @return
     */
    public ErrorType createUserAccount(ModifyAccountDTO dto) {
        User u = userRepository.findByEmail(dto.getEmail());
        if (u != null){
            return ErrorType.EMAIL_USED;
        }
        User user = new User(dto.getName(), dto.getEmail(), dto.getPassword());
        userRepository.save(user);
        return ErrorType.NONE;
    }


    public ErrorType createOrganizerAccount(ModifyAccountDTO dto) {
        Organiser o = organiserRepository.findByEmail(dto.getEmail());
        if (o != null){
            return ErrorType.EMAIL_USED;
        }
        Organiser organizer = new Organiser(dto.getName(), dto.getEmail(), dto.getPassword(),
                dto.getCompanyName(), dto.getPhoneNumber(), dto.getAddress());
        organiserRepository.save(organizer);
        return ErrorType.NONE;
    }


    public ErrorType createAdministratorAccount(ModifyAccountDTO dto) {
        Administrator a = adminRepository.findByEmail(dto.getEmail());
        if (a != null){
            return ErrorType.EMAIL_USED;
        }
        Administrator admin = new Administrator(dto.getName(), dto.getEmail(), dto.getPassword());
        adminRepository.save(admin);
        return ErrorType.NONE;
    }


    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        accounts.addAll(userRepository.findAll());
        accounts.addAll(adminRepository.findAll());
        accounts.addAll(organiserRepository.findAll());
        return accounts;
    }


    public Administrator getAdministratorAccount(String email) {
        return adminRepository.findByEmail(email);
    }


    public Organiser getOrganizerAccount(String email) {
        return organiserRepository.findByEmail(email);
    }


    public User getUserAccount(String email) {
        return userRepository.findByEmail(email);
    }


    public void saveUser(ModifyAccountDTO registration) {
        if ("ORGANISER".equals(registration.getRole())) {
            // 创建Organiser账户
            Organiser organiser = new Organiser(
                registration.getName(),
                registration.getEmail(),
                registration.getPassword(),
                registration.getCompanyName() != null ? registration.getCompanyName() : "",
                registration.getAddress() != null ? registration.getAddress() : "",
                registration.getPhoneNumber() != null ? registration.getPhoneNumber() : ""
            );
            organiserRepository.save(organiser);
            System.out.println("Saved Organiser: " + organiser);
        } else if ("ADMINISTRATOR".equals(registration.getRole())) {
            // 创建Administrator账户
            Administrator administrator = new Administrator(
                registration.getEmail(),
                registration.getName(),
                registration.getPassword()
            );
            adminRepository.save(administrator);
            System.out.println("Saved Administrator: " + administrator);
        } else {
            // 默认创建User账户
            User user = new User(registration);
            userRepository.save(user);
            System.out.println("Saved User: " + user);
        }
    }

    public List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();
        accounts.addAll(userRepository.findAll());
        accounts.addAll(adminRepository.findAll());
        accounts.addAll(organiserRepository.findAll());
        return accounts;
    }

    public ErrorType updateAccount(ModifyAccountDTO accdto) {
        // Update User
        User user = userRepository.findByEmail(accdto.getEmail());
        if (user != null) {
            if (accdto.getName() != null) {
                user.setName(accdto.getName());
            }
            if (accdto.getPassword() != null) {
                user.setPassword(accdto.getPassword());
            }
            userRepository.save(user);
            return ErrorType.NONE;
        }

        // Update Organiser
        Organiser organiser = organiserRepository.findByEmail(accdto.getEmail());
        if (organiser != null) {
            if (accdto.getName() != null) {
                organiser.setName(accdto.getName());
            }
            if (accdto.getPassword() != null) {
                organiser.setPassword(accdto.getPassword());
            }
            if (accdto.getCompanyName() != null) {
                organiser.setCompanyName(accdto.getCompanyName());
            }
            if (accdto.getPhoneNumber() != null) {
                organiser.setPhoneNumber(accdto.getPhoneNumber());
            }
            if (accdto.getAddress() != null) {
                organiser.setAddress(accdto.getAddress());
            }
            organiserRepository.save(organiser);
            return ErrorType.NONE;
        }

        // Update Administrator
        Administrator admin = adminRepository.findByEmail(accdto.getEmail());
        if (admin != null) {
            if (accdto.getName() != null) {
                admin.setName(accdto.getName());
            }
            if (accdto.getPassword() != null) {
                admin.setPassword(accdto.getPassword());
            }
            adminRepository.save(admin);
            return ErrorType.NONE;
        }

        return ErrorType.EMAIL_USED;
    }


    public void registerAccount(String email, String name, String encode) {

        if (userRepository.findByEmail(email) != null ||
                organiserRepository.findByEmail(email) != null ||
                adminRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException("Email is already in use: " + email);
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setName(name);
        newUser.setPassword(encode);

        userRepository.save(newUser);
    }

    // 保存账户信息到数据库
    public void saveAccount(Account account) {
        if (account instanceof User) {
            userRepository.save((User) account);
        } else if (account instanceof Administrator) {
            adminRepository.save((Administrator) account);
        } else if (account instanceof Organiser) {
            organiserRepository.save((Organiser) account);
        } else {
            throw new IllegalArgumentException("Unsupported account type");
        }
    }
}
