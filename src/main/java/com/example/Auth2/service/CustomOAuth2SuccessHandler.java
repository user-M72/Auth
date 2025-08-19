package com.example.Auth2.service;

import com.example.Auth2.entity.User;
import com.example.Auth2.repository.UserRepository;
import com.example.Auth2.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String sub = oAuth2User.getAttribute("sub");
        String picture = oAuth2User.getAttribute("picture");


        String finalEmail = email;
        String finalName = name;

        userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = User.builder()
                    .username(finalName)
                    .email(finalEmail)
                    .password(null)
                    .build();
            return userRepository.save(newUser);
        });

        if (authentication.getPrincipal() instanceof UserDetails userDetails){
            email = userDetails.getUsername();
            User dbUser = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
            name = dbUser.getUsername();

        }

        String token = jwtUtil.generateToken(email);


        response.sendRedirect("/user?token=" + token
                +"&name="+name
                +"&email="
                +email+"&sub="
                +sub+"&picture="
                +URLEncoder.encode(picture, "UTF-8"));
    }
}
