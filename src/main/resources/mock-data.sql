INSERT INTO ERS_USER_ROLES("NAME")
VALUES ('MANAGER'), ('FINANCE MANAGER'), ('ADMIN'), ('EMPLOYEE'), ('LEAD');

INSERT INTO ERS_USERS (GIVEN_NAME, SURNAME, EMAIL, USERNAME, PASSWORD, ROLE_ID)
VALUES ('WINNIE', 'THEPOOH', 'WINNIE.THEPOOH@REVATURE.COM', 'WINNIE', 'P4$$W0RD', 'aa000894-b3f8-46f0-93f1-ce4077ce3cfc');--EMPLOYEE

INSERT INTO ERS_USERS (GIVEN_NAME, SURNAME, EMAIL, USERNAME, PASSWORD, ROLE_ID)
VALUES ('ROB', 'JACKSON', 'ROB@REVATURE.COM', 'ROB', 'P4$$WORD','129ebdb1-e21c-4bc1-a43d-f08d9047a0c2'),--MANAGER
       ('TRAVIS', 'JACKSON', 'TRAVIS@REVATURE.COM', 'TRAVIS', 'P4$$WORD','19f2a53a-e2dc-4c89-8f8d-7abb0c7c35ac'),--ADMIN
       ('JANET', 'JACKSON', 'JANET@REVATURE.COM', 'JANET', 'P4$$WORD','1437be51-abdd-4145-8175-ef1cbada50e8'),--FINANCE MANAGER
       ('LEE', 'JACKSON', 'LEE@REVATURE.COM', 'LEE', 'P4$$WORD', '10842923-2c18-4cf3-b379-d8c86e2a7066'), --LEAD
 	   ('PENNY', 'JACKSON', 'PENNY@REVATURE.COM', 'PENNY', 'P4$$WORD','aa000894-b3f8-46f0-93f1-ce4077ce3cfc'),--EMPLOYEE
	   ('MORGAN', 'FREEMAN', 'MORGAN.FREEMAN@REVATURE.COM', 'MORGAN', 'P4$$W0RD','19f2a53a-e2dc-4c89-8f8d-7abb0c7c35ac'),--ADMIN
       ('MICHEAL', 'JACKSON', 'MICHEAL@REVATURE.COM', 'MICHEAL', 'P4$$W0RD', '10842923-2c18-4cf3-b379-d8c86e2a7066'),--LEAD
       ('DOOEY', 'DOGOODER', 'DOOEY@REVATURE.COM', 'DOOEY', 'P4$$W0RD', '129ebdb1-e21c-4bc1-a43d-f08d9047a0c2');--MANAGER


 SELECT *
FROM ERS_USERS;

SELECT *
FROM ERS_REIMBURSEMENTS;

SELECT *
FROM ers_user_roles eur;

SELECT *
FROM ers_reimbursement_statuses ers;

SELECT *
FROM ers_reimbursement_types ert;

INSERT INTO ers_reimbursement_statuses (STATUS_ID, STATUS)
VALUES ('129ebdb1-e21c-4bc1-a43d-f08d9047a0c2', 'APPROVED'),--MANAGER
		('19f2a53a-e2dc-4c89-8f8d-7abb0c7c35ac', 'APPROVED TO PROGRESS'),--ADMIN
		('1437be51-abdd-4145-8175-ef1cbada50e8', 'APPROVED AND READY FOR PROCESS'),--FINANCE MANAGER
		('aa000894-b3f8-46f0-93f1-ce4077ce3cfc', 'PENDING'),--EMPLOYEE
		('10842923-2c18-4cf3-b379-d8c86e2a7066','DENIED');--LEAD
INSERT INTO ERS_REIMBURSEMENT_TYPES (TYPE)
VALUES ('LODGING'),
		('TRAVEL'),
		('FOOD'),
		('OTHER');

INSERT INTO ERS_REIMBURSEMENTS (AMOUNT, AUTHOR_ID, RESOLVER_ID, STATUS_ID, TYPE_ID, DESCRIPTION)
VALUES ('10', 'e0cc2882-9e34-476b-9e81-41becdc09b3b','e0cc2882-9e34-476b-9e81-41becdc09b3b', '129ebdb1-e21c-4bc1-a43d-f08d9047a0c2', 'fa97c160-44ba-4b10-a338-128a0369f72f', 'TRAVEL'),--TRAVIS-ADMIN, APPROVED, TRAVEL
		('20', '2bef510f-55cf-4838-bd7e-54c792d6f464',  '2bef510f-55cf-4838-bd7e-54c792d6f464', '19f2a53a-e2dc-4c89-8f8d-7abb0c7c35ac', 'a506258a-075d-4512-9761-c724460128fe', 'LODGING'),--ROB-MANAGER, APPROVED TO PROGRESS, LODGING-
		('40', '4d2e77f4-9f4e-4422-b16a-75cc3abf5e38', '4d2e77f4-9f4e-4422-b16a-75cc3abf5e38', '1437be51-abdd-4145-8175-ef1cbada50e8', 'cdea4807-8df0-40ac-b1f1-cc0782410a07', 'FOOD'),--JANET-FINANCE MANAGER, APPROVED AND READY FOR PROCESS, FOOD
		('25', 'fb2deb06-9839-42bc-9b3b-34a0ce284730', 'fb2deb06-9839-42bc-9b3b-34a0ce284730', 'aa000894-b3f8-46f0-93f1-ce4077ce3cfc', 'da19c7cc-002c-4129-8ed4-99d782d6022b', 'OTHER'),--WINNIE-EMPLOYEE,PENDING, OTHER
		('50', '7ad8caec-2e24-4298-a56d-a69bf39baeb2', '7ad8caec-2e24-4298-a56d-a69bf39baeb2', '10842923-2c18-4cf3-b379-d8c86e2a7066', 'da19c7cc-002c-4129-8ed4-99d782d6022b', 'OTHER');--MICHEAL-LEAD, DENIED, OTHER
