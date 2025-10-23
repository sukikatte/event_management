package ucd.comp3013j.ems.model.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ucd.comp3013j.ems.model.*;
import ucd.comp3013j.ems.model.dto.ModifyAccountDTO;
import ucd.comp3013j.ems.model.services.AccountService;
import ucd.comp3013j.ems.model.services.EventService;
import ucd.comp3013j.ems.websecurity.AccountWrapper;

import java.util.List;

@Controller
public class AccountController {
    private final AccountService accountService;
    private final EventService eventService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AccountController(AccountService as, EventService es) {
        this.accountService = as;
        this.eventService = es;
    }

    @GetMapping(value = {"/", ""})
    public String home(Model model) {
        model.addAttribute("registration", new ModifyAccountDTO());
        System.out.println("Printing list of accounts");
        for (Account a: accountService.getAccounts()){
            System.out.println("Account name "+ a.getName());
        }
        return "00-login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        //return "05-create-account";
        return "07-sign-user";
    }

    @PostMapping("/register")
    public String registerPost(
            @ModelAttribute("registration") ModifyAccountDTO registration,
            BindingResult result,
            Model model) {
        registration.setPassword(bCryptPasswordEncoder.encode(registration.getPassword()));
        Account existingUser = accountService.getAccount(registration.getEmail());
        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null, "There is already an account registered with the same email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", registration);
            return "/07-sign-user";
        }
        accountService.saveUser(registration);
        return "03-main-user";
    }

    @GetMapping("/createAccount")
    public String showCreateAccountForm(Model model) {
        return "05-create-account";
    }

    @PostMapping("/createAccount")
    public String createAccount(
            @ModelAttribute("registration") ModifyAccountDTO registration,
            @RequestParam("accountType") String accountType,
            BindingResult result,
            Model model) {
        registration.setPassword(bCryptPasswordEncoder.encode(registration.getPassword()));

        Account existingUser = accountService.getAccount(registration.getEmail());
        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null, "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", registration);
            return "27-add-account-error";
        }

        ErrorType e;
        if ("Admin".equals(accountType)) {
            e = accountService.createAdministratorAccount(registration);
        } else if ("Organizer".equals(accountType)) {
            e = accountService.createOrganizerAccount(registration);
        } else if ("User".equals(accountType)) {
            e = accountService.createUserAccount(registration);
        } else {
            result.rejectValue("accountType", null, "Invalid account type");
            model.addAttribute("user", registration);
            return "27-add-account-error";
        }

        if (e == ErrorType.EMAIL_USED) {
            result.rejectValue("email", null, "There is already an account registered with the same email");
            model.addAttribute("user", registration);
            return "27-add-account-error";
        }

        // according to account type, redirect to the appropriate page
        if ("Admin".equals(accountType)) {
            return "01-main-admin";
        } else if ("Organizer".equals(accountType)) {
            return "02-main-organiser";
        } else {
            return "03-main-user";
        }

    }



    @GetMapping(value = {"/administrator", "/administrator/"})
    public String adminPage(Authentication authentication, Model model) {
        if (authentication.getPrincipal() instanceof AccountWrapper aw) {
            Administrator account = accountService.getAdministratorAccount(aw.getUsername());
            model.addAttribute("account", account);
        }

        System.out.println("Admin page");
        List<Account> accounts = accountService.getAccounts();
        System.out.println("Number of accounts: " + accounts.size());
        model.addAttribute("accounts", accounts);
        return "01-main-admin";
    }

    @GetMapping(value = {"/user", "/user/"})
    public String customerPage(Authentication authentication, Model model) {
        if (authentication.getPrincipal() instanceof AccountWrapper aw) {
            User account = accountService.getUserAccount(aw.getUsername());
            model.addAttribute("user", account);
        }
        return "03-main-user";
    }

    @GetMapping(value = {"/organiser", "/organiser/"})
    public String organiserPage(Authentication authentication, Model model) {
        if (authentication.getPrincipal() instanceof AccountWrapper aw) {
            Organiser account = accountService.getOrganizerAccount(aw.getUsername());
            model.addAttribute("organiser", account);
        }
        return "02-main-organiser";
    }


    @GetMapping("/displayAccount")
    public String displayAccount(@RequestParam long accountId, Model model) {
        Account account = accountService.getAccount(accountId);
        model.addAttribute("account", account);
        return "06-view&modify-account-admin-user";
    }

    @GetMapping("/modifyAccount")
    public String modifyAccount(@RequestParam long accountId, Model model) {
        Account account = accountService.getAccount(accountId);
        model.addAttribute("account", account);
        return "06-view&modify-account-admin-user";
    }

    @PostMapping("/modifyAccount")
    public String modifyAccount(@ModelAttribute ModifyAccountDTO accdto, Model model) {
        accountService.updateAccount(accdto);
        return "redirect:/displayAccount?accountId=" + accdto.getId() + "&modify_success";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("registration", new ModifyAccountDTO());
        return "00-login";
    }

    @GetMapping("/profile")
    public String userProfile(Authentication authentication, Model model) {
        if (authentication.getPrincipal() instanceof AccountWrapper aw) {
            User account = accountService.getUserAccount(aw.getUsername());
            model.addAttribute("user", account);
        }
        return "08-profile-user"; // 返回 Thymeleaf 模板
    }

    @GetMapping("/adminProfile")
    public String adminProfile(Authentication authentication, Model model) {
        if (authentication.getPrincipal() instanceof AccountWrapper aw) {
            Administrator account = accountService.getAdministratorAccount(aw.getUsername());
            model.addAttribute("admin", account);
        }
        return "16-profile-admin"; // 返回管理员页面模板
    }

    @GetMapping("/organiserProfile")
    public String organiserProfile(Authentication authentication, Model model) {
        if (authentication.getPrincipal() instanceof AccountWrapper aw) {
            Organiser account = accountService.getOrganizerAccount(aw.getUsername());
            model.addAttribute("organiser", account);
        }
        return "21-profile-organiser"; // 返回 Organiser 页面模板
    }



    @PostMapping("/updateProfile")
    public String updateProfile(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam(required = false) String password,
            Authentication authentication,
            Model model) {
        if (authentication.getPrincipal() instanceof AccountWrapper aw) {
            String currentEmail = aw.getUsername(); // 当前用户的唯一标识（假设是邮箱）

            // 查找当前用户账户
            Account account = accountService.getAccount(currentEmail);

            if (account != null) {
                // 更新账户信息
                account.setName(username);
                account.setEmail(email);
                if (password != null && !password.isEmpty()) {
                    account.setPassword(new BCryptPasswordEncoder().encode(password));
                }

                // 保存更新后的账户
                accountService.saveAccount(account);

                // 更新 Spring Security 的认证对象
                AccountWrapper updatedUserDetails = new AccountWrapper(account); // 更新后的用户详情
                Authentication newAuth = new UsernamePasswordAuthenticationToken(
                        updatedUserDetails,
                        authentication.getCredentials(),
                        updatedUserDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(newAuth);

                // 更新成功提示
                return "redirect:/profile?update_success";
            } else {
                model.addAttribute("error", "Account not found.");
            }
        }

        // 更新失败，返回错误消息
        model.addAttribute("error", "Failed to update profile. Please try again.");
        return "08-profile-user";
    }

    @PostMapping("/updateAdminProfile")
    public String updateAdminProfile(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam(required = false) String password,
            Authentication authentication,
            Model model) {
        if (authentication.getPrincipal() instanceof AccountWrapper aw) {
            String currentEmail = aw.getUsername(); // 当前管理员的唯一标识（假设是邮箱）

            // 查找当前管理员账户
            Account account = accountService.getAccount(currentEmail);

            if (account != null) {
                // 更新账户信息
                account.setName(username);
                account.setEmail(email);
                if (password != null && !password.isEmpty()) {
                    account.setPassword(new BCryptPasswordEncoder().encode(password));
                }

                // 保存更新后的账户
                accountService.saveAccount(account);

                // 更新 Spring Security 的认证对象
                AccountWrapper updatedAdminDetails = new AccountWrapper(account); // 更新后的管理员详情
                Authentication newAuth = new UsernamePasswordAuthenticationToken(
                        updatedAdminDetails,
                        authentication.getCredentials(),
                        updatedAdminDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(newAuth);

                // 更新成功提示
                return "redirect:/adminProfile?update_success";
            } else {
                model.addAttribute("error", "Account not found.");
            }
        }

        // 更新失败，返回错误消息
        model.addAttribute("error", "Failed to update profile. Please try again.");
        return "16-profile-admin";
    }

    @PostMapping("/updateOrganiserProfile")
    public String updateOrganiserProfile(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String telephone,
            @RequestParam(required = false) String address,
            Authentication authentication,
            Model model) {
        if (authentication.getPrincipal() instanceof AccountWrapper aw) {
            String currentEmail = aw.getUsername(); // 当前 Organiser 的唯一标识（假设是邮箱）

            // 查找当前 Organiser 账户
            Account account = accountService.getAccount(currentEmail);

            if (account instanceof Organiser organiserAccount) {
                // 更新 Organiser 信息
                organiserAccount.setName(username);
                organiserAccount.setEmail(email);
                if (password != null && !password.isEmpty()) {
                    organiserAccount.setPassword(new BCryptPasswordEncoder().encode(password));
                }
                if (companyName != null) {
                    organiserAccount.setCompanyName(companyName);
                }
                if (telephone != null) {
                    organiserAccount.setPhoneNumber(telephone);
                }
                if (address != null) {
                    organiserAccount.setAddress(address);
                }

                // 保存更新后的账户
                accountService.saveAccount(organiserAccount);

                // 更新 Spring Security 的认证对象
                AccountWrapper updatedOrganiserDetails = new AccountWrapper(organiserAccount); // 更新后的 Organiser 详情
                Authentication newAuth = new UsernamePasswordAuthenticationToken(
                        updatedOrganiserDetails,
                        authentication.getCredentials(),
                        updatedOrganiserDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(newAuth);

                // 更新成功提示
                return "redirect:/organiserProfile?update_success";
            } else {
                model.addAttribute("error", "Account not found.");
            }
        }

        // 更新失败，返回错误消息
        model.addAttribute("error", "Failed to update profile. Please try again.");
        return "21-profile-organiser";
    }

}
