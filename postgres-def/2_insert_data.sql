
-- Division --
INSERT INTO medical_division (identifier, div_name, charge_nurse_id, div_location, num_beds, occupied_beds, tele_ext, div_status) VALUES
    ('CARD', 'Cardiology', 'RN-5052523', 'North wing', 5, 0,123, 'incomplete'),
    ('DERM', 'Dermatology', 'RN-9538572', 'South wing', 5,0, 124, 'incomplete'),
    ('ICU', 'Intensive Care', 'RN-4268265', 'North wing', 5,0, 125, 'incomplete'),
    ('FAMM', 'Family Medicine', 'RN-1358439', 'East wing', 5, 0,126, 'incomplete'),
    ('NEUR', 'Neurology', 'RN-8726365', 'West wing', 5, 0,127, 'incomplete');

-- Beds --
INSERT INTO bed (identifier, room_num, bed_num, is_available) VALUES
    ('7ef30938-fae5-41c3-b4d8-c9dcb2ad3b8f', 100, 1001, TRUE),
    ('d358d77a-a989-47c4-9503-db7419ac15fb', 100, 1002, TRUE),
    ('ec389b1a-174a-4638-b01e-4a87b41e7557', 101, 1003, TRUE),
    ('65f7cd1f-6e98-4433-a8d4-3ee8c72065e3', 101, 1004, TRUE),
    ('dcdf6421-afd0-4e9b-88ec-fa962735f5a9', 102, 1005, TRUE),

    ('901d9790-d2bd-42a4-ac15-ab1a428d66fb', 110, 1001, TRUE),
    ('6d25085b-1a64-473b-b34a-85eb85907b48', 111, 1002, TRUE),
    ('b62b07b8-34b3-4be2-95e4-f836bba9e715', 112, 1003, TRUE),
    ('ef04d564-d3e6-4ce8-83de-0647e81a32c0', 113, 1004, TRUE),
    ('aa1f82bc-d710-4014-8faa-a2848a6631ba', 114, 1005, TRUE),

    ('5a176790-1238-462c-9b02-c06a049a62eb', 150, 1001, TRUE),
    ('e3a6e9d3-1457-4951-b0e7-b1b66dd9e9ed', 150, 1002, TRUE),
    ('f8213519-6c99-4528-901e-3c46bfd8f799', 150, 1003, TRUE),
    ('371d9637-3318-436a-b040-792399f88f20', 150, 1004, TRUE),
    ('8bbb15be-ed94-4d53-97bf-c659d4f7acea', 150, 1005, TRUE),

    ('e8252c36-1191-4b6a-aad4-e1e2f607f4b3', 200, 1001, TRUE),
    ('e723cb9e-712a-4775-964e-bebd2df76e46', 201, 1002, TRUE),
    ('86c28aad-56be-494c-b78d-9e6bed641dab', 202, 1003, TRUE),
    ('a7110e7e-bff7-4361-b775-053aa045c114', 203, 1004, TRUE),
    ('0e890172-8234-4d8a-ac16-2643fe896d7f', 204, 1005, TRUE),

    ('e7687068-e37d-436e-b64c-7967ec9d1604', 220, 1001, TRUE),
    ('2f23b441-e5a7-42f8-a07a-5f7c4a88138f', 220, 1002, TRUE),
    ('4102d2fd-89b0-4ff3-b805-bc758f3a2ac9', 221, 1003, TRUE),
    ('afa3dbee-5f0c-44a5-8eb6-e17fd311e1f3', 221, 1004, TRUE),
    ('bc7d94f7-0e54-4c08-9ac4-96eb451456cf', 222, 1005, TRUE);

-- Division-Bed  --
INSERT INTO medical_division_bed (med_identifier, bed_identifier) VALUES
    ('CARD', '7ef30938-fae5-41c3-b4d8-c9dcb2ad3b8f'),
    ('CARD', 'd358d77a-a989-47c4-9503-db7419ac15fb'),
    ('CARD', 'ec389b1a-174a-4638-b01e-4a87b41e7557'),
    ('CARD', '65f7cd1f-6e98-4433-a8d4-3ee8c72065e3'),
    ('CARD', 'dcdf6421-afd0-4e9b-88ec-fa962735f5a9'),

    ('DERM', '901d9790-d2bd-42a4-ac15-ab1a428d66fb'),
    ('DERM', '6d25085b-1a64-473b-b34a-85eb85907b48'),
    ('DERM', 'b62b07b8-34b3-4be2-95e4-f836bba9e715'),
    ('DERM', 'ef04d564-d3e6-4ce8-83de-0647e81a32c0'),
    ('DERM', 'aa1f82bc-d710-4014-8faa-a2848a6631ba'),

    ('ICU', '5a176790-1238-462c-9b02-c06a049a62eb'),
    ('ICU', 'e3a6e9d3-1457-4951-b0e7-b1b66dd9e9ed'),
    ('ICU', 'f8213519-6c99-4528-901e-3c46bfd8f799'),
    ('ICU', '371d9637-3318-436a-b040-792399f88f20'),
    ('ICU', '8bbb15be-ed94-4d53-97bf-c659d4f7acea'),

    ('FAMM', 'e8252c36-1191-4b6a-aad4-e1e2f607f4b3'),
    ('FAMM', 'e723cb9e-712a-4775-964e-bebd2df76e46'),
    ('FAMM', '86c28aad-56be-494c-b78d-9e6bed641dab'),
    ('FAMM', 'a7110e7e-bff7-4361-b775-053aa045c114'),
    ('FAMM', '0e890172-8234-4d8a-ac16-2643fe896d7f'),

    ('NEUR', 'e7687068-e37d-436e-b64c-7967ec9d1604'),
    ('NEUR', '2f23b441-e5a7-42f8-a07a-5f7c4a88138f'),
    ('NEUR', '4102d2fd-89b0-4ff3-b805-bc758f3a2ac9'),
    ('NEUR', 'afa3dbee-5f0c-44a5-8eb6-e17fd311e1f3'),
    ('NEUR', 'bc7d94f7-0e54-4c08-9ac4-96eb451456cf');

-- HR Employees --
INSERT INTO hr_employees (emp_id) VALUES 
    ('RN-5052523'),
    ('RN-9538572'),
    ('RN-4268265'),
    ('RN-1358439'),
    ('RN-8726365'),
    ('D-1231815'),
    ('D-4851611'),
    ('D-4861522'),
    ('S-4513132'),
    ('S-5464512'),
    ('S-4121214');

-- Existing Addresses --
INSERT INTO address_record (id, street_num, street_name, apt_number, postal_code, city, province, country) VALUES
    ('f1994f15-e591-4f92-acfa-27ddd01ed776', 1, 'someStree', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('95b10a52-a55a-4ae5-91bc-184bb8e28a49', 2, 'someStree', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('e35d6b9d-37cf-4df5-b110-3f27e7862cd2', 3, 'someStree', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('57117786-dd72-4d61-b4b4-efe0399bdbaa', 4, 'someStree', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('9d41c91e-1288-488c-88c5-2ec858a72fc2', 5, 'someStree', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('16e6d50e-0c60-4c5c-837f-20fc7e6c4771', 6, 'someStree', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('2fa8eb06-73f4-4c6d-9ce9-77828cb56e28', 7, 'someStree', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('778f964e-eeba-4fe2-8375-05ebef5c624f', 8, 'someStree', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('649bedcb-038e-4c67-bb61-e48b12a8b099', 9, 'someStree', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('2183f528-aeb9-47b1-a818-3029441de15d', 10, 'someStree', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('a63bfb3d-7b76-4fad-b036-2ca94ae0de74', 11, 'someStree', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('4e64619f-3fa0-489e-ac48-b4abeeb5efdc', 12, 'someStree', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('d5e61ae6-1648-42bf-b23d-0ffd25d7a89d', 13, 'someStree', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('acbaeda8-bd5a-48ab-85c9-b728a0991f3f', 14, 'someStree', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('bb4828d4-14b6-4b8d-a1c3-de3d0c3b79a9', 15, 'someStree', 0, '123456', 'someCity', 'someprovince', 'somecountry');

-- Existing Employees --
INSERT INTO staff (employee_id, first_name, last_name, address_id, telephone, date_of_birth, user_name, staff_password, tele_extension, modify_permission) VALUES
    ('RN-5052523', 'Nurse1', 'name', 'f1994f15-e591-4f92-acfa-27ddd01ed776', 1358658625, '2000-05-03', 'nurse1', 'password1', 111, TRUE),
    ('RN-9538572', 'Nurse2', 'name', '95b10a52-a55a-4ae5-91bc-184bb8e28a49', 1358658626, '2000-05-04', 'nurse2', 'password2', 112, TRUE),
    ('RN-4268265', 'Nurse3', 'name', 'e35d6b9d-37cf-4df5-b110-3f27e7862cd2', 1358658627, '2000-05-05', 'nurse3', 'password3', 113, TRUE),
    ('RN-1358439', 'Nurse4', 'name', '57117786-dd72-4d61-b4b4-efe0399bdbaa', 1358658628, '2000-05-06', 'nurse4', 'password4', 114, TRUE),
    ('RN-8726365', 'Nurse5', 'name', '9d41c91e-1288-488c-88c5-2ec858a72fc2', 1358658629, '2000-05-07', 'nurse5', 'password5', 115, TRUE),
    ('D-1231815', 'Doctor1', 'name', '2183f528-aeb9-47b1-a818-3029441de15d', 1358658630, '2000-05-08', 'doctor1', 'password6', 116, TRUE),
    ('D-4851611', 'Doctor2', 'name', 'a63bfb3d-7b76-4fad-b036-2ca94ae0de74', 1358658631, '2000-05-09', 'doctor2', 'password7', 117, TRUE),
    ('D-4861522', 'Doctor3', 'name', '4e64619f-3fa0-489e-ac48-b4abeeb5efdc', 1358658632, '2000-05-10', 'doctor3', 'password8', 118, TRUE),
    ('S-4513132', 'Staff1', 'name', 'd5e61ae6-1648-42bf-b23d-0ffd25d7a89d', 1358658633, '2000-05-11', 'staff1', 'password9', 119, TRUE),
    ('S-5464512', 'Staff2', 'name', 'acbaeda8-bd5a-48ab-85c9-b728a0991f3f', 1358658634, '2000-05-12', 'staff2', 'password10', 120, TRUE),
    ('S-4121214', 'Staff3', 'name', 'bb4828d4-14b6-4b8d-a1c3-de3d0c3b79a9', 1358658635, '2000-05-13', 'staff3', 'password11', 121, TRUE);

-- Existing Doctors --
INSERT INTO doctor (employee_id, division_name) VALUES 
    ('D-1231815', 'Cardiology'),
    ('D-4851611', 'Dermatology'),
    ('D-4861522', 'Intensive Care');

-- Existing Charge Nurses --
INSERT INTO charge_nurse(employee_id, bipper_extension) VALUES 
    ('RN-5052523', 1),
    ('RN-9538572', 2),
    ('RN-4268265', 3),
    ('RN-1358439', 4),
    ('RN-8726365', 5);

-- Existing Patients --
INSERT INTO patient(patient_id, gov_insurance_num, first_name, last_name, address_id, telephone, date_of_birth, gender,
   martial_status, ext_doctor_id) VALUES
    ('2c6ea163-f569-48ea-85b8-36606d0e03bf', 123456, 'fname', 'lname', '16e6d50e-0c60-4c5c-837f-20fc7e6c4771', 1234567890, '2000-05-08', 'male', 'Single', 'abcddd'),
    ('081d1286-5342-4987-baf0-ca0cce83b3c5', 222456, 'fname2', 'lname2', '2fa8eb06-73f4-4c6d-9ce9-77828cb56e28', 2224567890, '2000-05-08', 'female', 'Widowed', '222abcddd');

-- Existing Next Of Kin --
INSERT INTO patient_next_of_kin(patient_id, first_name, last_name, relationship, address_id, telephone) VALUES
    ('2c6ea163-f569-48ea-85b8-36606d0e03bf', 'nokfname', 'noklname', 'friend', '778f964e-eeba-4fe2-8375-05ebef5c624f', 1234567890),
    ('081d1286-5342-4987-baf0-ca0cce83b3c5', 'nokfname2', 'noklname2', 'spouse', '649bedcb-038e-4c67-bb61-e48b12a8b099', 1234567890);

-- Existing Admissions --
INSERT INTO admission_record (record_id, patient_id, local_doctor_id, division_id, room_number, bed_number, priv_insurance_num, is_discharged) VALUES
    ('2c741307-a023-4ba2-8b7f-2aa4d8046488', '2c6ea163-f569-48ea-85b8-36606d0e03bf', 'D-1231815', 'CARD', 100, 1001, 1234567890, TRUE),
    ('ee3e2c73-6b0f-4ba9-a4f5-e50ef0234dbd', '2c6ea163-f569-48ea-85b8-36606d0e03bf', 'D-1231815', 'ICU', 150, 1002, 1234567890, TRUE),
    ('ade75dd8-582c-47b7-9999-bb487ee1c52c', '081d1286-5342-4987-baf0-ca0cce83b3c5', 'D-1231815', 'FAMM', 200, 1001, 1234567890, TRUE),
    ('2cf42d23-a97f-4526-9f89-cb1f275d3c6d', '081d1286-5342-4987-baf0-ca0cce83b3c5', 'D-1231815', 'DERM', 112, 1003, 1234567890, TRUE);

-- Existing Patients --
INSERT INTO admit_request (patient_id, requesting_charge_nurse_id, admit_priority, rationale,local_doctor_id) VALUES 
    ('081d1286-5342-4987-baf0-ca0cce83b3c5', 'RN-9538572', 5, 'Broke their brain', 'D-1231815');

-- Existing Admit Req Medical Division Link --
INSERT INTO medical_division_admit_req (med_identifier, pat_identifier) VALUES 
    ('NEUR', '081d1286-5342-4987-baf0-ca0cce83b3c5');

-- Existing Medication --
INSERT INTO medication (medication_num, patient_id, med_name, unit_by_day, admin_by_day, method_of_admin, med_start_date, med_end_date) VALUES 
    (123123, '2c6ea163-f569-48ea-85b8-36606d0e03bf', 'super good meds', 5, 4, 'injection', '2020-05-08', '2020-06-08'),
    (246123, '081d1286-5342-4987-baf0-ca0cce83b3c5', 'super good meds sequel', 6, 7, 'oral', '2021-05-08', '2021-06-08');

-- Existing Locations --
INSERT INTO patient_location (id, street_num, street_name, apt_number, postal_code, city, province, country) VALUES
    ('f1994f15-e591-4f92-acfa-27ddd01ed776', 1, 'mainStreet', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('95b10a52-a55a-4ae5-91bc-184bb8e28a49', 2, 'mainStreet', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('e35d6b9d-37cf-4df5-b110-3f27e7862cd2', 3, 'mainStreet', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('57117786-dd72-4d61-b4b4-efe0399bdbaa', 4, 'mainStreet', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('9d41c91e-1288-488c-88c5-2ec858a72fc2', 5, 'mainStreet', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('16e6d50e-0c60-4c5c-837f-20fc7e6c4771', 6, 'mainStreet', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('2fa8eb06-73f4-4c6d-9ce9-77828cb56e28', 7, 'mainStreet', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('778f964e-eeba-4fe2-8375-05ebef5c624f', 8, 'mainStreet', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('649bedcb-038e-4c67-bb61-e48b12a8b099', 9, 'mainStreet', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('2183f528-aeb9-47b1-a818-3029441de15d', 10, 'mainStreet', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('a63bfb3d-7b76-4fad-b036-2ca94ae0de74', 11, 'mainStreet', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('4e64619f-3fa0-489e-ac48-b4abeeb5efdc', 12, 'mainStreet', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('d5e61ae6-1648-42bf-b23d-0ffd25d7a89d', 13, 'mainStreet', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('acbaeda8-bd5a-48ab-85c9-b728a0991f3f', 14, 'mainStreet', 0, '123456', 'someCity', 'someprovince', 'somecountry'),
    ('bb4828d4-14b6-4b8d-a1c3-de3d0c3b79a9', 15, 'mainStreet', 0, '123456', 'someCity', 'someprovince', 'somecountry');
	
-- Existing Patient Locations Link --
INSERT INTO location_tracking (patient_id, location_id) VALUES
    ('2c6ea163-f569-48ea-85b8-36606d0e03bf', 'f1994f15-e591-4f92-acfa-27ddd01ed776'),
	('2c6ea163-f569-48ea-85b8-36606d0e03bf', '95b10a52-a55a-4ae5-91bc-184bb8e28a49'),
	('2c6ea163-f569-48ea-85b8-36606d0e03bf', 'e35d6b9d-37cf-4df5-b110-3f27e7862cd2'),
	('2c6ea163-f569-48ea-85b8-36606d0e03bf', '57117786-dd72-4d61-b4b4-efe0399bdbaa'),
	('2c6ea163-f569-48ea-85b8-36606d0e03bf', '9d41c91e-1288-488c-88c5-2ec858a72fc2'),
	('2c6ea163-f569-48ea-85b8-36606d0e03bf', '16e6d50e-0c60-4c5c-837f-20fc7e6c4771'),
    ('081d1286-5342-4987-baf0-ca0cce83b3c5', '2fa8eb06-73f4-4c6d-9ce9-77828cb56e28'),
	('081d1286-5342-4987-baf0-ca0cce83b3c5', '778f964e-eeba-4fe2-8375-05ebef5c624f'),
	('081d1286-5342-4987-baf0-ca0cce83b3c5', '649bedcb-038e-4c67-bb61-e48b12a8b099'),
	('081d1286-5342-4987-baf0-ca0cce83b3c5', '2183f528-aeb9-47b1-a818-3029441de15d'),
	('081d1286-5342-4987-baf0-ca0cce83b3c5', 'a63bfb3d-7b76-4fad-b036-2ca94ae0de74'),
	('081d1286-5342-4987-baf0-ca0cce83b3c5', '4e64619f-3fa0-489e-ac48-b4abeeb5efdc'),
	('081d1286-5342-4987-baf0-ca0cce83b3c5', 'd5e61ae6-1648-42bf-b23d-0ffd25d7a89d'),
	('081d1286-5342-4987-baf0-ca0cce83b3c5', 'acbaeda8-bd5a-48ab-85c9-b728a0991f3f'),
	('081d1286-5342-4987-baf0-ca0cce83b3c5', 'bb4828d4-14b6-4b8d-a1c3-de3d0c3b79a9');

-- Existing Infection --	
INSERT INTO infection (id, patient_id, infection_start_date, infection_end_date, name) VALUES
	('f1994f15-e591-4f92-acfa-27ddd01ed776', '2c6ea163-f569-48ea-85b8-36606d0e03bf', '2020-05-08', '2020-06-08', 'COVID-19');