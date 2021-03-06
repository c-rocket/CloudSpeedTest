CREATE USER speedtest IDENTIFIED BY cloudspeedtest DEFAULT TABLESPACE users QUOTA UNLIMITED ON users PASSWORD EXPIRE;
CREATE ROLE speed_test_role;
  GRANT
CREATE SESSION,
  CREATE TABLE, CREATE SEQUENCE,
  CREATE VIEW TO speed_test_role;
  GRANT speed_test_role TO speedtest;
  ALTER PROFILE DEFAULT LIMIT FAILED_LOGIN_ATTEMPTS UNLIMITED PASSWORD_LIFE_TIME UNLIMITED;
  ALTER USER speedtest IDENTIFIED BY cloudspeedtest;