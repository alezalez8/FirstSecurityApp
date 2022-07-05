package org.shunin.firstsecurityapp.security;

import org.shunin.firstsecurityapp.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AuthProviderImpl implements AuthenticationProvider {
    private final PersonDetailsService personDetailsService;

    @Autowired
    public AuthProviderImpl(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // search user by name
        String username = authentication.getName();
        // return user wrapped by UserDetails
        UserDetails personDetails = personDetailsService.loadUserByUsername(username);

        // get password from web form
        String password = authentication.getCredentials().toString();
        // compare
        if(!password.equals(personDetails.getPassword()))
            throw new BadCredentialsException("Incorrect password");

        // return (if OK) Autentication with Principal inside
        return new UsernamePasswordAuthenticationToken(personDetails, password,
                Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
