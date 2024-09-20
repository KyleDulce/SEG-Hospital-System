import { HttpHeaders } from "@angular/common/http";

export interface LogInRequest {
    employeeId: String;
    password: String;
}

export interface LogInResponse {
    token: String;
    username: String;
    userType: StaffType;
}

export interface HttpOptions {
    headers?:
        | HttpHeaders
        | {
            [header: string]: string | string[];
        }
        | undefined;
    withCredentials?: boolean | undefined;
    observe: 'response';
    responseType: 'json';
}

export interface RegisterRequest {
    firstName: String;
    lastName: String;
    dateOfBirth: String;
    phoneNumber: Number;
    email: String;
    streetNumber: Number;
    unit: Number;
    streetName: String;
    zipCode: String;
    city :String;
    country: String;
    province:String;
    staffId: String;
    password: String;
    retypedPass: String;
    teleExt: Number;
    modPerm: Boolean;
    staffType: String;
    bipperExtension: Number | null;
    divName: String | null;
}

export interface RegisterResponse {
    token: String;
    firstName: String;
    lastName: String;
    dateOfBirth: String;
    phoneNumber: Number;
    email: String;
    streetNumber: Number;
    unit: Number;
    streetName: String;
    zipCode: String;
    city :String;
    country: String;
    province:String;
    staffId: String;
    password: String;
    retypedPass: String;
    teleExt: Number;
    modPerm: Boolean;
    staffType: String;
    bipperExtension: Number;
    divName: String;
}

export type StaffType = "Doctor" | "ChargeNurse" | "Staff";