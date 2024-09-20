export interface Admission {
    recordId: String;
    patientId: String;
    localDoctor: String;
    divisionId: String;
    roomNum: Number;
    bedNumber: Number;
    privInsuranceNum: Number;
    discharged: boolean;
}

export interface AdmitPatientRequest {
    privateInsuranceNumber: number,
    patientId: string,
    divisionId: string,
    roomNum: number,
    bedNum: number,
    localDoctorId: string
}

export interface AdmitPatientDialogOpenData {
    patientId: string;
    isFromRequestList: boolean
}

export enum AdmitPatientDialogResult {
    CANCEL,
    GO_REQUEST,
    RELOAD
}

export interface AdmissionInfoResponse {
    recordId: string,
    patientId: string,
    localDoctorId: string,
    divisionId: string,
    privateInsuranceNumber: number,
    dischargeDate: number,
    roomNum: number,
    bedNum: number
}

export const emptyAdmissionInfoResponse: AdmissionInfoResponse = {
    recordId: '',
    patientId: '',
    localDoctorId: '',
    divisionId: '',
    privateInsuranceNumber: -1,
    dischargeDate: -1,
    roomNum: -1,
    bedNum: -1
}

export interface DischargeRequest {
    patientId: string,
    reasonForDischarge: string,
    divisionId: string
}

export interface RequestAdmissionDialogOpenData {
    patientId: string;
}

export enum RequestAdmissionDialogResult {
    CANCEL,
    GO_REQUEST,
    RELOAD
}

export interface RequestAdmissionRequest {
    patientId: string,
    divisionId: string,
    priority: number,
    localDoctorId: string,
    requestRationale: string,
    requestingChargeNurseId: String}

export interface AdmitRequestFull {
    patientId: string;
    patientFullName: string;
    rationale: string;
    priority: number;
}

export interface RejectPatientAdmissionRequest {
    divisionId: string,
    patientId: string

}
