package task13.domain;

import org.springframework.security.core.GrantedAuthority;

public enum  UserRoles implements GrantedAuthority {
    ROLE_ANONYMOUS,
    ROLE_USER,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
