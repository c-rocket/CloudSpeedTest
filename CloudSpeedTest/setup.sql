DROP TABLE speed_test;
CREATE TABLE speed_test
  (
    ID           NUMBER PRIMARY KEY,
    NAME_IDX     VARCHAR2(60),
    NAME_NOT_IDX VARCHAR2(60)
  );
DROP SEQUENCE speed_test_seq;
CREATE SEQUENCE speed_test_seq MINVALUE 1 MAXVALUE 999999999999999999999999999 START WITH 1 INCREMENT BY 1 CACHE 20;

CREATE INDEX speed_test_name ON speed_test(NAME_IDX)
      TABLESPACE users
      STORAGE (INITIAL 20K
      NEXT 20k
      PCTINCREASE 75);
      
DECLARE
   x NUMBER := speed_test_seq.nextval;
BEGIN
   FOR i IN 1..10000 LOOP
   	  INSERT INTO speed_test (ID,NAME_IDX,NAME_NOT_IDX) VALUES (speed_test_seq.nextval,'idxName'||to_char(x),'name'||to_char(x));
      x := speed_test_seq.nextval;
   END LOOP;
   COMMIT;
END;