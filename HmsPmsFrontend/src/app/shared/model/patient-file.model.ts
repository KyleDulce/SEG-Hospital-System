import { Admission } from './admission.model';

export interface Patient {
  patientId: String;
  govInsuranceNum: Number;
  firstName: String;
  lastName: String;
  address: Address;
  telephone: Number;
  dateOfBirth: Date;
  gender: String;
  martialStatus: String;
  extDoctorId: String;
  nextOfKin: NextOfKin;
}

export interface Address {
  streetNum: Number;
  streetName: String;
  aptNumber: Number;
  postalCode: String;
  city: String;
  province: String;
  country: String;
}

export interface NextOfKin {
  firstName: String;
  lastName: String;
  relationshipToPatient: String;
  telephone: Number;
  address: Address;
}

export interface Prescription {
  identifier: String;
  patienId: String;
  name: String;
  unitByDay: Number;
  adminByDay: Number;
  methodOfAdministration: String;
  startDate: Date;
  endDate: Date;
}

export interface Infection {
  identifier: string;
  patienId: String;
  name: String;
  startDate: Date;
  endDate: Date;
}

export interface PatientFile {
    patientInfo: Patient;
    admissionRecords: Admission[];
    prescriptions: Prescription[];
    infections: Infection[];
    infectionStatus: PatientInfectionStatus;
    locations: LocationResponse[];
}

export interface LocationResponse {
  location: PatientLocation;
  startDate: string;
  endDate: string;
}

export interface PatientLocation {
  locationId: string;
  streetNum: number;
  streetName: string;
  aptNumber: number;
  postalCode: string;
  city: string;
  province: string;
  country: string;

}

export interface RegisterPatientRequest {
  firstName: string;
  lastName: string;
  gender: string;
  dateOfBirth: string;
  maritalStatus: string;
  telephone: number;
  extDoctorID: number;
  govInsurNum: number;
  address: Address;
  nextOfKin: NextOfKin;
}

export interface DialogOpenOptions {
  patientId: string;
}

export interface PrescribeMedicationRequest {
  drugNumber: number;
  drugName: string;
  unitsByDay: number;
  numAdminPerDay: number;
  startDate: number;
  endDate: number;
  methodOfAdmin: string;
  patientId: string;
}

export enum DialogResult {
  CANCEL,
  RELOAD,
}

export interface UpdatePatientInfoRequest {
  employeeId: String;
  patientId: String;
  govInsuranceNum: Number;
  firstName: String;
  lastName: String;
  telephone: Number;
  dateOfBirth: String;
  gender: String;
  martialStatus: String;
  extDoctorId: String;
  patientStreetNum: Number;
  patientStreetName: String;
  patientAptNum: Number;
  patientPostalCode: String;
  patientCity: String;
  patientProvince: String;
  patientCountry: String;
  nextOfKinFirstName: String;
  nextOfKinLastName: String;
  relationship: String;
  nextOfKinTelephone: Number;
  nOKStreetNum: Number;
  nOKStreetName: String;
  nOKAptNum: Number;
  nOKPostalCode: String;
  nOKCity: String;
  nOKProvince: String;
  nOKCountry: String;
}

export interface DischargeDialogOpenData {
  patientId: string;
}

export enum DischargeDialogResult {
  CANCEL,
  RELOAD,
}

export enum PatientInfectionStatus {
    NOT_INFECTED,
    MAY_BE_INFECTED,
    INFECTED
}

export interface InfectionRequest {
  name: string;
  startDate: string;
  endDate: string;
  patientId: string;
}

export interface InfectionDialogOpenOptions {
  patientId: string;
  infection?: Infection;
}
