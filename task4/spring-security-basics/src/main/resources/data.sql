INSERT INTO users (username, password, enabled)
VALUES ('user','{bcrypt}$2a$12$nrtKzcmzJIx7awQHeABKT.GNxeOBzCJhDyLANWdXoalKQayGO0zKa',1)
ON CONFLICT DO NOTHING;

INSERT INTO authorities (username, authority)
VALUES ('user', 'ROLE_USER')
ON CONFLICT DO NOTHING;