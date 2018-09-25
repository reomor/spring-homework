package task14.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@Slf4j
public class AclManagerService implements AclManager {

    private final JdbcMutableAclService aclService;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AclManagerService(JdbcMutableAclService aclService, JdbcTemplate jdbcTemplate) {
        this.aclService = aclService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> void addPermission(Class<T> clazz, Serializable identifier, Sid sid, Permission permission, Permission ... permissions) {
        /*
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities();
        Sid s = new PrincipalSid(authentication);
        // aclService.
         aclManager.addPermission(User.class, 1, new GrantedAuthoritySid("ROLE_ADMIN"), BasePermission.READ);
        //*/
        ObjectIdentity identity = new ObjectIdentityImpl(clazz, identifier);
        MutableAcl acl = aclCreateIfNotExist(identity);
        List<Permission> permissionList = new ArrayList<>();
        permissionList.add(permission);
        permissionList.addAll(Arrays.asList(permissions));
        grantPermission(acl, sid, permissionList);
        aclService.updateAcl(acl);
    }

    public <T> void delPermission(Class<T> clazz, Serializable identifier, Sid sid, Permission permission) {
        ObjectIdentity identity = new ObjectIdentityImpl(clazz.getCanonicalName(), identifier);
        MutableAcl acl = (MutableAcl) aclService.readAclById(identity);
        List<AccessControlEntry> aclEntries = acl.getEntries();
        aclEntries.removeIf(accessControlEntry -> accessControlEntry.getSid().equals(sid) && accessControlEntry.getPermission().equals(permission));
        aclService.updateAcl(acl);
    }

    @Override
    public <T> boolean isPermissionGranted(Class<T> clazz, Serializable identifier, Sid sid, Permission permission) {
        ObjectIdentity identity = new ObjectIdentityImpl(clazz.getCanonicalName(), identifier);
        MutableAcl acl = (MutableAcl) aclService.readAclById(identity);
        return acl.isGranted(Collections.singletonList(permission), Collections.singletonList(sid), false);
    }

    @Override
    public void deleteAllGrantedAcl() {
        jdbcTemplate.update("delete from acl_entry");
        jdbcTemplate.update("delete from acl_object_identity");
        jdbcTemplate.update("delete from acl_sid");
        jdbcTemplate.update("delete from acl_class");
    }

    private MutableAcl aclCreateIfNotExist(ObjectIdentity identity) {
        MutableAcl acl;
        try {
            acl = (MutableAcl) aclService.readAclById(identity);
        } catch (NotFoundException e) {
            acl = aclService.createAcl(identity);
        }
        return acl;
    }

    private void grantPermission(MutableAcl acl, Sid sid, List<Permission> permissionList) {
        try {
            acl.isGranted(permissionList, Collections.singletonList(sid), false);
        } catch (NotFoundException e) {
            for (Permission permission : permissionList) {
                acl.insertAce(acl.getEntries().size(), permission, sid, true);
            }
        }
    }
}
