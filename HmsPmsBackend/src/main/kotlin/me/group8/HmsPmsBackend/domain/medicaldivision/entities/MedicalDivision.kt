package me.group8.HmsPmsBackend.domain.medicaldivision.entities

class MedicalDivision(
    val identifier: String,
    val name: String,
    val chargeNurseId: String,
    val location: String,
    val numBeds: Int,
    val teleExt: Int,
    var status: String,
    val bedIds: Array<String>
) {
    val admitReqs: MutableList<AdmitRequest> = ArrayList()
    var occupiedBeds = 0;

    fun isPatientInRequestList(patientId: String): Boolean {
        for(admitRequest: AdmitRequest in admitReqs) {
            if(admitRequest.patientId.equals(patientId)) {
                return true
            }
        }
        return false
    }

    fun addPatientToRequestlist(patientId: String, requestRational: String, priority: Int, requestingChargeNurseId: String, localDoctorID: String): Boolean {
        admitReqs.add(
            AdmitRequest(patientId, requestingChargeNurseId, priority, requestRational, localDoctorID)
        );
        return true
    }

    fun removePatientFromRequestlist(patientId: String) {
        for(x in 0 until admitReqs.size) {
            if(admitReqs[x].patientId.equals(patientId)) {
                admitReqs.removeAt(x)
                return
            }
        }
    }

    fun findRequestByPatientId(patientId: String): AdmitRequest? {
        for(admitRequest: AdmitRequest in admitReqs) {
            if(admitRequest.patientId.equals(patientId)) {
                return admitRequest
            }
        }
        return null;
    }

    fun isDivisionComplete(): Boolean {
        return status.equals("complete")
    }

    fun updateDivsionStatus(newStatus: String): Boolean {
        status = newStatus;
        return true
    }
}