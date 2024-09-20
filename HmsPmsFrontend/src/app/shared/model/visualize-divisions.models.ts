
export interface GetAllDivisionResponse {
    divisions: Array<SimpleDivision>
}

export interface SimpleDivision {
    divisionId: string
    divisionName: string
}

export interface GetDivisionResponse {
    division: BaseDivisionInfo,
    beds: Array<SimpleBed>,
    chargeNurseName: string
}

export interface SimpleBed {
    roomNum: number,
    bedNum: number,
    occupied: boolean
}

export interface BaseDivisionInfo {
    identifier: string,
    name: string,
    chargeNurse: string,
    location: string,
    numOfBeds: number,
    teleExtension: number,
    status: string,
    bedIds: Array<string>,
    admitRequests: Array<AdmissionRequest>
}

export interface AdmissionRequest {
    divisionId: string,
    patientId: string,
    requestingChargeNurse: string,
    requestRationale: string,
    priority: number
}

export const emptyBaseDivisionInfo: BaseDivisionInfo = {
    identifier: '',
    name: '',
    chargeNurse: '',
    location: '',
    numOfBeds: 0,
    teleExtension: 0,
    status: '',
    bedIds: [],
    admitRequests: []
}

export const emptyGetDivisionResponse: GetDivisionResponse = {
    division: emptyBaseDivisionInfo,
    beds: [],
    chargeNurseName: ''
}