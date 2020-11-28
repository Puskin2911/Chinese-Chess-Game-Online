package com.doubleat.ccgame.security;

import com.doubleat.ccgame.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Setter
public class CustomOAuth2User implements OAuth2User {

    private Collection<? extends GrantedAuthority> authorities = Collections.
            singletonList(new SimpleGrantedAuthority("ROLE_USER"));

    private Map<String, Object> attributes;

    @Getter
    private String id;

    private String username;

    @Getter
    private String email;

    public CustomOAuth2User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public static CustomOAuth2User create(User user) {
        return new CustomOAuth2User(
                user.getUsername(),
                user.getEmail()
        );
    }

    public static CustomOAuth2User create(User user, Map<String, Object> attributes) {
        CustomOAuth2User customOAuth2User = CustomOAuth2User.create(user);
        customOAuth2User.setAttributes(attributes);

        return customOAuth2User;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return this.username;
    }
}
