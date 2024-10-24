package com.hada.backtest.jpa.service;

import com.hada.backtest.constant.UserRole;
import com.hada.backtest.exception.DataNotFoundException;
import com.hada.backtest.jpa.entity.SiteUser;
import com.hada.backtest.jpa.repository.SiteUserRepository;
import java.util.Optional;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SiteUserService implements UserDetailsService {

    private final SiteUserRepository siteUserRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUserService(SiteUserRepository siteUserRepository, PasswordEncoder passwordEncoder) {
        this.siteUserRepository = siteUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SiteUser siteUser = siteUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new User(
                siteUser.getUsername(), siteUser.getPassword(), siteUser.getAuthorities());
    }

    public SiteUser save(String username, String password) {
        SiteUser siteUser = new SiteUser();
        siteUser.setUsername(username);
        siteUser.setPassword(passwordEncoder.encode(password));
        if (username.equals("admin")) {
            siteUser.setAuthorities(UserRole.ADMIN.getValue());
        } else {
            siteUser.setAuthorities(UserRole.USER.getValue());
        }
        return siteUserRepository.save(siteUser);
    }

    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = this.siteUserRepository.findByUsername(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }

    public boolean findByUsername(String username) {
        return siteUserRepository.findByUsername(username).isPresent();
    }
}
