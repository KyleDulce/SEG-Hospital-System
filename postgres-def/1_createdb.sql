CREATE TABLE IF NOT EXISTS address_record (
   id CHAR(36) NOT NULL,
   street_num INTEGER NOT NULL,
   street_name VARCHAR(50) NOT NULL,
   apt_number SMALLINT NOT NULL,
   postal_code VARCHAR(6) NOT NULL,
   city VARCHAR(50) NOT NULL,
   province VARCHAR(50) NOT NULL,
   country VARCHAR(25) NOT NULL,
   CONSTRAINT pk_patient_address PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS admission_record (
   record_id CHAR(36) NOT NULL,
   patient_id CHAR(36) NOT NULL,
   local_doctor_id VARCHAR(50) NOT NULL,
   division_id VARCHAR(4) NOT NULL,
   room_number SMALLINT NOT NULL,
   bed_number SMALLINT NOT NULL,
   priv_insurance_num BIGINT,
   is_discharged BOOLEAN NOT NULL,
   CONSTRAINT pk_admission_record PRIMARY KEY (record_id)
);

CREATE TABLE IF NOT EXISTS admit_request (
   patient_id CHAR(36) NOT NULL,
   requesting_charge_nurse_id VARCHAR(50) NOT NULL,
   admit_priority INTEGER NOT NULL,
   rationale VARCHAR(255) NOT NULL,
   local_doctor_id VARCHAR(50) NOT NULL,
   CONSTRAINT pk_admit_request PRIMARY KEY (patient_id)
);

CREATE TABLE IF NOT EXISTS bed (
   identifier CHAR(36) NOT NULL,
   room_num SMALLINT NOT NULL,
   bed_num SMALLINT NOT NULL,
   is_available BOOLEAN NOT NULL,
   CONSTRAINT pk_bed PRIMARY KEY (identifier)
);

CREATE TABLE IF NOT EXISTS charge_nurse (
   employee_id VARCHAR(50) NOT NULL,
   bipper_extension SMALLINT NOT NULL,
   CONSTRAINT pk_charge_nurse PRIMARY KEY (employee_id)
);

CREATE TABLE IF NOT EXISTS doctor (
   employee_id VARCHAR(50) NOT NULL,
   division_name VARCHAR(50) NOT NULL,
   CONSTRAINT pk_doctor PRIMARY KEY (employee_id)
);

CREATE TABLE IF NOT EXISTS hr_employees (
   emp_id VARCHAR(50) NOT NULL,
   CONSTRAINT pk_hr_employees PRIMARY KEY (emp_id)
);

CREATE TABLE IF NOT EXISTS access_log (
   id BIGSERIAL NOT NULL,
   log_time TIMESTAMP NOT NULL,
   staff_id VARCHAR(50) NOT NULL,
   patient_id CHAR(36) NOT NULL,
   CONSTRAINT pk_access_log PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS medical_division_admit_req (
   med_identifier VARCHAR(4) NOT NULL,
   pat_identifier CHAR(36) NOT NULL,
   CONSTRAINT pk_medical_division_admit_req PRIMARY KEY (pat_identifier)
);

CREATE TABLE IF NOT EXISTS medical_division_bed (
   med_identifier VARCHAR(4) NOT NULL,
   bed_identifier CHAR(36) NOT NULL,
   CONSTRAINT pk_medical_division_bed PRIMARY KEY (med_identifier, bed_identifier)
);

CREATE TABLE IF NOT EXISTS medical_division (
   identifier VARCHAR(4) NOT NULL,
   div_name VARCHAR(50) NOT NULL,
   charge_nurse_id VARCHAR(50) NOT NULL,
   div_location VARCHAR(255) NOT NULL,
   num_beds SMALLINT NOT NULL,
   occupied_beds SMALLINT NOT NULL,
   tele_ext SMALLINT NOT NULL,
   div_status VARCHAR(15) NOT NULL,

   CONSTRAINT pk_medical_division PRIMARY KEY (identifier)
);

CREATE TABLE IF NOT EXISTS medication_admin (
   medication_num BIGINT NOT NULL,
   patient_id CHAR(36) NOT NULL,
   med_start_date DATE NOT NULL,
   med_end_date DATE NOT NULL,
   num_units INTEGER NOT NULL,
   admin_time TIMESTAMP NOT NULL,
   CONSTRAINT pk_medication_admin PRIMARY KEY (medication_num, patient_id, med_start_date, med_end_date, admin_time)
);

CREATE TABLE IF NOT EXISTS medication (
   medication_num BIGINT NOT NULL,
   patient_id CHAR(36) NOT NULL,
   med_name VARCHAR(255) NOT NULL,
   unit_by_day SMALLINT NOT NULL,
   admin_by_day SMALLINT NOT NULL,
   method_of_admin VARCHAR(255) NOT NULL,
   med_start_date DATE NOT NULL,
   med_end_date DATE NOT NULL,
   CONSTRAINT pk_medication PRIMARY KEY (medication_num, patient_id, med_start_date, med_end_date)
);

CREATE TABLE IF NOT EXISTS patient_next_of_kin (
   patient_id CHAR(36) NOT NULL,
   first_name VARCHAR(50) NOT NULL,
   last_name VARCHAR(50) NOT NULL,
   relationship VARCHAR(100) NOT NULL,
   address_id CHAR(36) NOT NULL,
   telephone BIGINT NOT NULL,
   CONSTRAINT pk_patient_next_of_kin PRIMARY KEY (patient_id)
);

CREATE TABLE IF NOT EXISTS patient (
   patient_id CHAR(36) NOT NULL,
   gov_insurance_num BIGINT NOT NULL,
   first_name VARCHAR(50) NOT NULL,
   last_name VARCHAR(50) NOT NULL,
   address_id CHAR(36) NOT NULL,
   telephone BIGINT NOT NULL,
   date_of_birth DATE NOT NULL,
   gender VARCHAR(15) NOT NULL,
   martial_status VARCHAR(15) NOT NULL,
   ext_doctor_id VARCHAR(50) NOT NULL,
   CONSTRAINT pk_patient PRIMARY KEY (patient_id)
);

CREATE TABLE IF NOT EXISTS staff (
   employee_id VARCHAR(50) NOT NULL,
   first_name VARCHAR(50) NOT NULL,
   last_name VARCHAR(50) NOT NULL,
   address_id CHAR(36) NOT NULL,
   telephone BIGINT NOT NULL,
   date_of_birth DATE NOT NULL,
   user_name VARCHAR(80) NOT NULL,
   staff_password VARCHAR(100) NOT NULL,
   tele_extension INTEGER NOT NULL,
   modify_permission BOOLEAN NOT NULL,
   CONSTRAINT pk_staff PRIMARY KEY (employee_id)
);

CREATE TABLE IF NOT EXISTS patient_location (
   id CHAR(36) NOT NULL,
   street_num INTEGER NOT NULL,
   street_name VARCHAR(50) NOT NULL,
   apt_number SMALLINT NOT NULL,
   postal_code VARCHAR(6) NOT NULL,
   city VARCHAR(50) NOT NULL,
   province VARCHAR(50) NOT NULL,
   country VARCHAR(25) NOT NULL,
   CONSTRAINT pk_patient_location PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS location_tracking (
   patient_id CHAR(36) NOT NULL,
   location_id CHAR(36) NOT NULL,
   CONSTRAINT pk_location_tracking PRIMARY KEY (patient_id, location_id)
);

CREATE TABLE IF NOT EXISTS infection (
   id CHAR(36) NOT NULL,
   patient_id CHAR(36) NOT NULL,
   infection_start_date DATE NOT NULL,
   infection_end_date DATE,
   CONSTRAINT pk_infection PRIMARY KEY (id)
);

CREATE TYPE infection_status 
AS 
ENUM('NOT INFECTED', 'MAY BE INFECTED', 'INFECTED');