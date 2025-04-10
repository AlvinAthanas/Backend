package com.example.cms_backend.Security;

import com.example.cms_backend.Model.Entities.Authority;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).get();

        if (user.getEmail().isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        List<String> roles = user.getRoles().stream()
                .map(role -> role.getName())  // "ADMIN" -> "ROLE_ADMIN"
                .toList();

        // Convert authorities (permissions) to a list of Strings
        List<String> authorities = user.getAuthorities().stream()
                .map(Authority::getName) // "VIEW_REPORTS"
                .toList();

        List<SimpleGrantedAuthority> grantedAuthorities =
                roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        grantedAuthorities.addAll(
                authorities.stream().map(SimpleGrantedAuthority::new).toList()
        );
        System.out.println("Granted Authorities: " + grantedAuthorities);
        //TODO: ADD ROLES AND AUTHORITIES
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .roles("PARISH_MEMBER")
//                .authorities(grantedAuthorities)
                .password(user.getPassword())
                .build();
    }
}
