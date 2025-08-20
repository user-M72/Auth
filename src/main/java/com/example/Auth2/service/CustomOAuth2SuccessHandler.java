package com.example.Auth2.service;

import com.example.Auth2.entity.User;
import com.example.Auth2.repository.UserRepository;
import com.example.Auth2.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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
        String registrationId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();

        String email = null;
        String name = null;
        String picture = null;
        String sub = null;
        String login = null;
        String id = null;
        String avatar = null;

        if ("google".equals(registrationId)) {
            // -------- GOOGLE --------
            email = oAuth2User.getAttribute("email");
            name = oAuth2User.getAttribute("name");
            sub = oAuth2User.getAttribute("sub");
            picture = oAuth2User.getAttribute("picture");

        } else if ("github".equals(registrationId)) {
            // -------- GITHUB --------

            login = oAuth2User.getAttribute("login");
            avatar = oAuth2User.getAttribute("avatar_url");
            email = oAuth2User.getAttribute("email"); // может быть null
            name = oAuth2User.getAttribute("name");

            if (name == null) {
                name = login; // если name пустой, используем login
            }
        }

        // --- сохраняем пользователя в БД ---
        final String finalEmail = email;
        final String finalName = name;

        if (finalEmail != null) {
            userRepository.findByEmail(finalEmail).orElseGet(() -> {
                User newUser = User.builder()
                        .username(finalName)
                        .email(finalEmail)
                        .password(null)
                        .build();
                return userRepository.save(newUser);
            });
        }

        // --- генерируем токен ---
        String token = jwtUtil.generateToken(email != null ? email : (login != null ? login : "guest"));

        // --- редиректим с параметрами ---
        String redirectUrl = "/user?token=" + token
                + "&name=" + (name != null ? name : "")
                + "&email=" + (email != null ? email : "")
                + "&sub=" + (sub != null ? sub : "")
                + "&picture=" + (picture != null ? URLEncoder.encode(picture, "UTF-8") : "")
                + "&login=" + (login != null ? login : "")
                + "&avatar=" + (avatar != null ? URLEncoder.encode(avatar, "UTF-8") : "");

        response.sendRedirect(redirectUrl);
    }


}
