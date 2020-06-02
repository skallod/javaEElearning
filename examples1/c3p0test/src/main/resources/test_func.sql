create or replace procedure test1 as
    begin
        dbms_lock.sleep(60);
        insert into DATA_TYPE values (14,'UT','UT');
    end;
    /
commit;
