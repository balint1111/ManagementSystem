INSERT INTO user (user_name, password, user_type) VALUES ('admin', '$2a$12$TOrPmX5joNpJDptmZX4wFOwy9tPpXpruZU7/6mSDmMu/ZgR0i8AfG', 'ADMIN'); -- jelszó: admin
INSERT INTO user (user_name, password, user_type) VALUES ('ToolManager', '$2a$12$QXeoELTERu2rkN84gihQ8OM.3crRN56Ub9PvNeom5T0/wgQw8sh5C', 'TOOL_MANAGER'); -- jelszó: password
INSERT INTO user (user_name, password, user_type) VALUES ('Operator', '$2a$12$QXeoELTERu2rkN84gihQ8OM.3crRN56Ub9PvNeom5T0/wgQw8sh5C', 'OPERATOR'); -- jelszó: password
INSERT INTO user (user_name, password, user_type) VALUES ('Repairman', '$2a$12$QXeoELTERu2rkN84gihQ8OM.3crRN56Ub9PvNeom5T0/wgQw8sh5C', 'REPAIRMAN'); -- jelszó: password
INSERT INTO EDUCATION ( NAME , DESCRIPTION ) VALUES ( 'test','asd');
INSERT INTO TOOL_CATEGORY ( CATEGORY , DESCRIPTION , MAINTENANCE_INTERVAL) VALUES ( 'testCategory' , 'description' , 'WEEK');
INSERT INTO REL_EDUCATION_TOOL_CATEGORY ( EDUCATION_ID , TOOL_CATEGORY_ID ) VALUES ( 1 , 1 );
INSERT INTO REL_EDUCATION_USER ( EDUCATION_ID , USER_ID ) VALUES ( 1 , 4 );
INSERT INTO TOOL ( NAME , IDENTIFIER ,  DESCRIPTION, TOOL_CATEGORY_ID) VALUES ( 'testTool' , 'tool-0001' , 'testTool', 1);
INSERT INTO ISSUE ( TOOL_ID , date_time, ESTIMATED_TIME ,  TITLE, STATUS ) VALUES ( 1 , '2022-01-01T00:00:00Z' , 8, 'TESZT FELADAT', 'NEW');
INSERT INTO ISSUE_LOG ( ISSUE_ID , DESCRIPTION) VALUES ( 1 , 'NEW STATUS');