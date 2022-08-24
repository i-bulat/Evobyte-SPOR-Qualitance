package com.evo.qualitanceProject.security.service;

import com.evo.qualitanceProject.model.AppUser;
import com.evo.qualitanceProject.model.AuthenticationProvider;
import com.evo.qualitanceProject.security.model.CustomOAuth2User;
import com.evo.qualitanceProject.security.util.JwtTokenUtil;
import com.evo.qualitanceProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2LoginSuccesHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService service;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getEmail();
        String firstName = oAuth2User.getFirstName();
        String lastName = oAuth2User.getLastName();
        System.out.println("***************** EMAIL " + email);
        AppUser appUser = service.findUserByEmail(email);
        String token = userDetailsService.generateToken(email);
        String url = "http://localhost:4200?token=" +token;
        if (appUser == null) {
            service.createNewCustomerAfterOauthLogin(firstName, lastName, email, AuthenticationProvider.GOOGLE);
            redirectStrategy.sendRedirect(request, response, url);
        } else {
            appUser.setFirstName(firstName);
            appUser.setLastName(lastName);
            appUser.setEnabled(true);
            appUser.setAuthProvider(AuthenticationProvider.GOOGLE);
            service.updateUser(appUser.getId(), appUser);
            redirectStrategy.sendRedirect(request, response, url);
        }

        super.onAuthenticationSuccess(request, response, authentication);


    }

}
