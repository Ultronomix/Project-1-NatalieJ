INSERT INTO ERS_USER_ROLES("NAME")
VALUES ('MANAGER'), ('FINANCE MANAGER'), ('ADMIN'), ('EMPLOYEE'), ('LEAD');

INSERT INTO ERS_USERS (GIVEN_NAME, SURNAME, EMAIL, USERNAME, PASSWORD, ROLE_ID)
VALUES ('ROBB', 'JACKSON', 'ROB@REVATURE.COM', 'ROBBJ', 'P4$$WORD','1375e1dd-d14c-46a6-a6b2-b4064d88ce88'),--MANAGER
       ('TRAVIS', 'JACKSON', 'TRAVIS@REVATURE.COM', 'TRAVISJ', 'P4$$WORD','c615662c-4689-4a70-b577-b66e7319f745'),--ADMIN
       ('JANET', 'JACKSON', 'JANET@REVATURE.COM', 'JANETJ', 'P4$$WORD','16a64dbe-7146-455d-a910-8a90ef24a907'),--FINANCE MANAGER
       ('LEES', 'JACKSON', 'LEE@REVATURE.COM', 'LEEJ', 'P4$$WORD', '520534cb-b9a5-4dc8-b938-ceb50264cf8b'), --LEAD
 	   ('PENNY', 'JACKSON', 'PENNY@REVATURE.COM', 'PENNYJ', 'P4$$WORD','d486a1dc-f533-4718-b47d-7d9052d834d0'),--EMPLOYEE
	   ('MORGAN', 'FREEMAN', 'MORGAN.FREEMAN@REVATURE.COM', 'MORGANF', 'P4$$W0RD','c615662c-4689-4a70-b577-b66e7319f745'),--ADMIN
       ('MICHEAL', 'JACKSON', 'MICHEAL@REVATURE.COM', 'MICHEALJ', 'P4$$W0RD', '520534cb-b9a5-4dc8-b938-ceb50264cf8b'),--LEAD
       ('DOOEY', 'DOGOODER', 'DOOEY@REVATURE.COM', 'DOOEYD', 'P4$$W0RD', '1375e1dd-d14c-46a6-a6b2-b4064d88ce88');--MANAGER

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
VALUES ('10', 'e0cc2882-9e34-476b-9e81-41becdc09b3b','4d2e77f4-9f4e-4422-b16a-75cc3abf5e38', '129ebdb1-e21c-4bc1-a43d-f08d9047a0c2', '4b638fb9-9a93-42b5-915f-554b7f20c36b', 'TRAVEL'),--TRAVIS-ADMIN, APPROVED, TRAVEL
		('20', '2bef510f-55cf-4838-bd7e-54c792d6f464',  '4d2e77f4-9f4e-4422-b16a-75cc3abf5e38', '19f2a53a-e2dc-4c89-8f8d-7abb0c7c35ac', '1cf727ac-6b15-4df2-9ea8-6c9a50ef4a96', 'LODGING'),--ROB-MANAGER, APPROVED TO PROGRESS, LODGING-
		('40', '4d2e77f4-9f4e-4422-b16a-75cc3abf5e38', '4d2e77f4-9f4e-4422-b16a-75cc3abf5e38', '1437be51-abdd-4145-8175-ef1cbada50e8', 'cd5fd8bd-aedd-43d9-85b0-efac0ab6078c', 'FOOD'),--JANET-FINANCE MANAGER, APPROVED AND READY FOR PROCESS, FOOD
		('25', 'fb2deb06-9839-42bc-9b3b-34a0ce284730', '4d2e77f4-9f4e-4422-b16a-75cc3abf5e38', 'aa000894-b3f8-46f0-93f1-ce4077ce3cfc', 'bac1df62-691d-49da-92dc-b70f2cdc0c78', 'OTHER'),--WINNIE-EMPLOYEE,PENDING, OTHER
		('50', '7ad8caec-2e24-4298-a56d-a69bf39baeb2', '4d2e77f4-9f4e-4422-b16a-75cc3abf5e38', '10842923-2c18-4cf3-b379-d8c86e2a7066', 'bac1df62-691d-49da-92dc-b70f2cdc0c78', 'OTHER');--MICHEAL-LEAD, DENIED, OTHER

INSERT INTO ERS_REIMBURSEMENTS (AMOUNT, AUTHOR_ID, RESOLVER_ID, STATUS_ID, TYPE_ID, DESCRIPTION)
VALUES ('25', '4bf6425a-c9a9-4ff3-a540-c930bd2947e7', 'ffa1b1e4-6dbe-4bbf-b72e-861312255d99', 'aa000894-b3f8-46f0-93f1-ce4077ce3cfc', '1cf727ac-6b15-4df2-9ea8-6c9a50ef4a96', 'LODGING');--PENNY-EMPLOYEE, PENDING, LODGING
