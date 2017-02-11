package ru.erfolk.actors;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.erfolk.entities.User;

import java.util.Collection;
import java.util.Collections;

public class Actor implements UserDetails {
    @Getter
    private User user;

    private Collection<UserAuthority> autorities;

    public Actor(User user) {
        this.user = user;
        this.autorities = Collections.singletonList(new UserAuthority(user));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return autorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        // todo
        return user.getUsername();
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
}
