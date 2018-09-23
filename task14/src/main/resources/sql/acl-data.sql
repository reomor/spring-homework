-- https://www.baeldung.com/spring-security-acl
INSERT INTO user (id, email, name, password_hash, password_salt)
VALUES (1,
        'adm@a.ru',
        'Admin',
        '$2a$10$HKpobMcLT/xoOSGvzq67sujpfW.SyK5xMBzxe7B.j8pLVDwDVvCm.',
        '1702e389-a2a0-48bc-98b6-b620cd3625d3'),
       (2,
        'usr@a.ru',
        'User',
        '$2a$10$HKpobMcLT/xoOSGvzq67sujpfW.SyK5xMBzxe7B.j8pLVDwDVvCm.',
        '1702e389-a2a0-48bc-98b6-b620cd3625d3');

INSERT INTO user_roles (user_id, role)
VALUES (1, 'ROLE_ADMIN'),
       (1, 'ROLE_USER'),
       (2, 'ROLE_USER');

-- ACL
INSERT INTO acl_sid (id, principal, sid)
VALUES -- (1, 1, 'root'),
       (1, 0, 'ROLE_UBER'),
       (2, 0, 'ROLE_ADMIN');

INSERT INTO acl_class (id, class)
VALUES (1, 'User');


INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting)
VALUES (1, 1, 1, NULL, 1, 0),
       (2, 1, 2, NULL, 1, 0);

INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)
VALUES (1, 1, 1, 1, 1, 1, 1, 1),
       (2, 1, 2, 1, 2, 0, 1, 1),
       (3, 2, 1, 2, 1, 1, 1, 1),
       (4, 2, 2, 2, 2, 1, 1, 1);
/*
       (2, 1, 2, 1, 2, 1, 1, 1),
       (3, 1, 3, 3, 1, 1, 1, 1),
       (4, 2, 1, 2, 1, 1, 1, 1),
       (5, 2, 2, 3, 1, 1, 1, 1),
       (6, 3, 1, 3, 1, 1, 1, 1),
       (7, 3, 2, 3, 2, 1, 1, 1);
*/