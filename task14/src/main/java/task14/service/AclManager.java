package task14.service;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;

import java.io.Serializable;

// https://github.com/iamthiago/security-acl/blob/master/src/main/java/com/security/service/AclManager.java
public interface AclManager {
    /**
     * Add a permission for the given object
     *
     * @param clazz      Domain class
     * @param identifier Id from the given domain
     * @param sid        Security Identifier, could be a {@link PrincipalSid} or a {@link GrantedAuthoritySid}
     * @param permission The permissions based on {@link BasePermission}, one or multiple
     */
    public <T> void addPermission(Class<T> clazz, Serializable identifier, Sid sid, Permission permission, Permission... permissions);

    /**
     * Remove a permission from the given object
     *
     * @param clazz      Domain class
     * @param identifier Id from the given domain
     * @param sid        Security Identifier, could be a {@link PrincipalSid} or a {@link GrantedAuthoritySid}
     * @param permission The permission based on {@link BasePermission}
     */
    public <T> void delPermission(Class<T> clazz, Serializable identifier, Sid sid, Permission permission);

    /**
     * Remove all permission from the given object
     */
    public <T> void delAllPermissions(MutableAcl acl);

    /**
     * Check whether the given object has permission
     *
     * @param clazz      Domain class
     * @param identifier Id from the given domain
     * @param sid        Security Identifier, could be a {@link PrincipalSid} or a {@link GrantedAuthoritySid}
     * @param permission The permission based on {@link BasePermission}
     * @return true or false
     */
    public <T> boolean isPermissionGranted(Class<T> clazz, Serializable identifier, Sid sid, Permission permission);

    /*
     * Delete all from acl tables
     */
    void clearAllAclTables();

    public <T> MutableAcl getAclById(Class<T> clazz, Serializable identifier);
}
