-- https://www.baeldung.com/spring-security-acl
INSERT INTO user (id, email, name, password_hash, password_salt)
VALUES (1, 'root', 'root', '$2a$10$HKpobMcLT/xoOSGvzq67sujpfW.SyK5xMBzxe7B.j8pLVDwDVvCm.', -- 12345
        '1702e389-a2a0-48bc-98b6-b620cd3625d3'),
       (2,
        'adm@a.ru',
        'Admin',
        '$2a$10$HKpobMcLT/xoOSGvzq67sujpfW.SyK5xMBzxe7B.j8pLVDwDVvCm.',
        '1702e389-a2a0-48bc-98b6-b620cd3625d3'),
       (3,
        'usr@a.ru',
        'User',
        '$2a$10$HKpobMcLT/xoOSGvzq67sujpfW.SyK5xMBzxe7B.j8pLVDwDVvCm.',
        '1702e389-a2a0-48bc-98b6-b620cd3625d3');

INSERT INTO user_roles (user_id, role)
VALUES (1, 'ROLE_UBER'),
       (1, 'ROLE_ADMIN'),
       (2, 'ROLE_ADMIN'),
       (2, 'ROLE_USER'),
       (3, 'ROLE_USER');

-- ACL
INSERT INTO acl_sid (id, principal, sid)
VALUES (1, 0, 'ROLE_UBER'),
       (2, 0, 'ROLE_ADMIN'),
       (3, 0, 'ROLE_USER');

INSERT INTO acl_class (id, class)
VALUES (1, 'task14.acl.domain.User');

-- for users in table user
INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting)
VALUES (1, 1, 1, NULL, 1, 0), --root
       (2, 1, 2, NULL, 1, 0), --admin
       (3, 1, 3, NULL, 1, 0); --user

INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)
    -- only read and write for root
VALUES (1, 1, 0, 1, 1, 1, 1, 1),
       (2, 1, 1, 1, 2, 1, 1, 1),
    --
       (3, 2, 0, 1, 1, 1, 1, 1),
       (4, 2, 1, 1, 8, 1, 1, 1),
       (5, 2, 2, 1, 16, 1, 1, 1),
       (6, 2, 3, 2, 1, 1, 1, 1),
       (7, 2, 4, 2, 2, 1, 1, 1),
    --
       (8, 3, 0, 2, 1, 1, 1, 1),
       (9, 3, 1, 2, 8, 1, 1, 1),
       (10, 3, 2, 2, 16, 1, 1, 1),
       (11, 3, 3, 3, 1, 1, 1, 1),
       (12, 3, 4, 3, 2, 1, 1, 1);