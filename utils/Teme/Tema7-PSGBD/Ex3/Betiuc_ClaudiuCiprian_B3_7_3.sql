--DROP TABLE DESTRUCTING_ATTEMPTS CASCADE CONSTRAINTS;
--CREATE TABLE DESTRUCTING_ATTEMPTS(ATTEMPTED_BY VARCHAR2(60), ATTEMPTED_AT TIMESTAMP);

-- ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~

CREATE OR REPLACE TRIGGER PROTECT_DB_DROP_TRUNCATE
BEFORE DROP OR TRUNCATE ON DATABASE  

DECLARE

v_userName VARCHAR2(60):=ora_login_user;
PRAGMA AUTONOMOUS_TRANSACTION;

BEGIN
    
    INSERT INTO DESTRUCTING_ATTEMPTS (ATTEMPTED_BY, ATTEMPTED_AT) VALUES (v_userName, CURRENT_TIMESTAMP);
    COMMIT;
    
    RAISE_APPLICATION_ERROR (
      num => -20001,
      msg => 'You can not DROP/TRUNCATE on this DB. Check the triggers.');
    
END PROTECT_DB_DROP_TRUNCATE;
/

CREATE OR REPLACE TRIGGER PROTECT_DB_DELETE
BEFORE ALTER ON DATABASE

DECLARE

v_userName VARCHAR2(60):=ora_login_user;
PRAGMA AUTONOMOUS_TRANSACTION;

BEGIN
    
    INSERT INTO DESTRUCTING_ATTEMPTS (ATTEMPTED_BY, ATTEMPTED_AT) VALUES (v_userName, CURRENT_TIMESTAMP);
    COMMIT;
    
    RAISE_APPLICATION_ERROR (
      num => -20002,
      msg => 'You can not DELETE on this DB. Check the triggers.');
    
END PROTECT_DB_DELETE;

-- ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~

-- ATTEMPTS TO DELETE DATA FROM THE DB:

DROP TABLE LOGTYPE_TABLE;
--TRUNCATE TABLE LOGTYPE_TABLE;