import { LocationResponse } from './patient-file.model';

export interface LocationRequest {
  streetNum: number;
  streetName: string;
  aptNumber: number;
  postalCode: string;
  city: string;
  province: string;
  country: string;
}

export interface LocationTrackingRequest {
  startDate: string;
  endDate: string;
}

export interface LocationOpenDialogOptions {
  patientId: string;
  patientLocation?: LocationResponse;
}
