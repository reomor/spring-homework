package task13.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.*;

@NoArgsConstructor
@Data
@Document
public class User implements UserDetails {
    @Id
    @ApiModelProperty(notes = "MongoDB generated id", example = "5b77ff92f489de2738fa6c07")
    private String id;
    @ApiModelProperty(notes = "User name", example = "Alexey")
    private String name;
    @Indexed(unique = true, sparse = true)
    @ApiModelProperty(notes = "User email is used to authenticate", example = "email@email.com")
    private String email;
    @ApiModelProperty(notes = "Hashed password", example = "")
    private String passwordHash;
    @ApiModelProperty(notes = "Salt for user's password, random for every user")
    private String passwordSalt;
    @ApiModelProperty(notes = "List of roles from enumeration")
    private Set<UserRoles> rolesList;

    public User(String name, String email, String passwordHash, String passwordSalt, UserRoles role, UserRoles... roles) {
        this(null, name, email, passwordHash, passwordSalt, EnumSet.of(role, roles));
    }

    public User(String id, String name, String email, String passwordHash, String passwordSalt, Set<UserRoles> rolesList) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
        setRolesList(rolesList);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rolesList;
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public Set<UserRoles> getRolesList() {
        return rolesList;
    }

    public void setRolesList(UserRoles role, UserRoles... roles) {
        setRolesList(EnumSet.of(role, roles));
    }

    public void setRolesList(Set<UserRoles> rolesList) {
        this.rolesList = CollectionUtils.isEmpty(rolesList) ? Collections.emptySet() : EnumSet.copyOf(rolesList);
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
