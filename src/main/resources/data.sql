INSERT INTO user (user_name, password, user_type) VALUES ('admin', 'admin', 'ADMIN');
INSERT INTO user (user_name, password, user_type) VALUES ('ToolManager', 'password', 'TOOL_MANAGER');
INSERT INTO user (user_name, password, user_type) VALUES ('Operator', 'password', 'OPERATOR');
INSERT INTO user (user_name, password, user_type) VALUES ('Repairman', 'password', 'REPAIRMAN');
INSERT INTO EDUCATION ( NAME , DESCRIPTION ) VALUES ( 'test','asd');
INSERT INTO TOOL_CATEGORY ( CATEGORY , DESCRIPTION , MAINTENANCE_INTERVAL) VALUES ( 'testCategory' , 'description' , 'WEEK');
INSERT INTO REL_EDUCATION_TOOL_CATEGORY ( EDUCATION_ID , TOOL_CATEGORY_ID ) VALUES ( 1 , 1 );
INSERT INTO REL_EDUCATION_USER ( EDUCATION_ID , USER_ID ) VALUES ( 1 , 4 );