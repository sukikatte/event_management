package ucd.comp3013j.ems.websecurity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ucd.comp3013j.ems.model.Account;

import java.util.Arrays;
import java.util.Collection;

public class AccountWrapper implements UserDetails {
    private final Account account;

    public AccountWrapper(Account user) {
        this.account = user;
        System.out.println("Created accoutn " + user.getName());
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(account.getRole().toString());
        System.out.println("AccountWrapper.getAuthorities: " + Arrays.asList(authority));
        return Arrays.asList(authority);
    }

    public String getPassword() {
        return account.getPassword();
    }

    public String getUsername() {
        return account.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Account getAccount() {
        return account;
    }
}
