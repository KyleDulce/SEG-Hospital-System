package me.group8.HmsPmsBackend.application.controller

import me.group8.HmsPmsBackend.application.controller.payload.*
import me.group8.HmsPmsBackend.application.dtos.queries.*
import me.group8.HmsPmsBackend.application.security.jwt.JwtUtils
import me.group8.HmsPmsBackend.application.usecases.*
import me.group8.HmsPmsBackend.domain.medication.entities.Medication
import me.group8.HmsPmsBackend.domain.patient.entities.Address
import me.group8.HmsPmsBackend.domain.patient.entities.AdmissionRecord
import me.group8.HmsPmsBackend.domain.patient.entities.Infection
import me.group8.HmsPmsBackend.domain.patient.entities.NextOfKin
import me.group8.HmsPmsBackend.domain.patient.entities.Patient
import me.group8.HmsPmsBackend.utils.StaffType
import me.group8.HmsPmsBackend.utils.TypeUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@CrossOrigin(origins = ["http://localhost","http://localhost:80","http://localhost:4200"])
class RestApiController(
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils,
    private val consultPatientFile: ConsultPatientFile,
    private val registerStaff: RegisterStaff,
    private val visualizeDivision: VisualizeDivision,
    private val staffLogIn: StaffLogIn,
    private val admitPatient: AdmitPatient,
    private val requestPatientAdmission: RequestPatientAdmission,
    private val updatePatientFile: UpdatePatientFile,
    private val prescribeMedication: PrescribeMedication,
    private val admitPatientFromRequestList: AdmitPatientFromRequestList,
    private val dischargePatient: DischargePatient,
    private val registerPatient: RegisterPatient,
    private val infection: me.group8.HmsPmsBackend.application.usecases.Infection,
) {

    @PostMapping("/login")
    fun postLogIn(@RequestBody loginRequest: LogInRequest): ResponseEntity<LogInResponse> {
        val authentication: Authentication = authenticationManager
            .authenticate(
                UsernamePasswordAuthenticationToken(
                    loginRequest.employeeId,
                    loginRequest.password
                )
            )
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils.generateJwtToken(authentication)
        val userDetails = authentication.principal as UserDetails
        val staffType = staffLogIn.getStaffType(loginRequest.employeeId)

        if (staffType == null) {
            System.err.println("Staff without type!")
            return ResponseEntity.internalServerError().build()
        }

        return ResponseEntity.ok(
            LogInResponse(
                jwt,
                userDetails.username,
                staffType.toString()
            )
        )
    }

    @PostMapping("/signout")
    fun postSignOut(): ResponseEntity<Void> {
        SecurityContextHolder.clearContext()
        return ResponseEntity.ok().build()
        }


    /**
     * if the current user is a charge nurse, get the division they are assigned,
     * otherwise 401
     */
    @GetMapping("chargeNurse/division")
    fun getChargeNurseDivision(): ResponseEntity<ChargeNurseDivisionResponse> {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as Jwt
        val employeeId = userDetails.subject
        val staffType = staffLogIn.getStaffType(employeeId);

        if (staffType != StaffType.CHARGE_NURSE) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .build()
        }
        val divisionId = admitPatient.getNurseDivisionId(employeeId) ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(ChargeNurseDivisionResponse(divisionId));
    }

    @PostMapping("admitPatient")
    fun postAdmitPatient(@RequestBody admitPatientRequest: AdmitPatientRequest): ResponseEntity<Void> {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as Jwt
        val employeeId = userDetails.subject
        val divisionId = admitPatient.getNurseDivisionId(employeeId)

        if(divisionId != admitPatientRequest.divisionId) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .build()
        }

        val result = admitPatient.admitPatient(AdmitPatientCreateDto(
            divisionId,
            admitPatientRequest.patientId,
            admitPatientRequest.roomNum,
            admitPatientRequest.bedNum,
            admitPatientRequest.privateInsuranceNumber,
            admitPatientRequest.localDoctorId
        ))

        if(!result) {
            return ResponseEntity.badRequest().build()
        } else {
            return ResponseEntity.ok().build()
        }
    }

    @PostMapping("/requestAdmission")
    fun postAdmitPatient(@RequestBody requestAdmission: RequestAdmissionRequest): ResponseEntity<Void> {
        val requestAddmissionInfo = RequestPatientAdmissionCreateDto(
                divisionId = requestAdmission.divisionId,
                patientId = requestAdmission.patientId,
                priority = requestAdmission.priority,
                localDoctorID = requestAdmission.localDoctorId,
                requestRationale = requestAdmission.requestRationale,
                requestingChargeNurse = requestAdmission.requestingChargeNurseId)

        val request = requestPatientAdmission.requestPatientAdmission(requestAddmissionInfo);

        if(request){
            return ResponseEntity.ok().build()
        }
        else{
            return ResponseEntity.badRequest().build()
        }
    }

    @GetMapping("/consultPatientFile/{patientId}")
    fun getConsultPatientFile(@PathVariable("patientId") patientId: String): ResponseEntity<ConsultPatientFileResponse> {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as Jwt
        val employeeId = userDetails.subject

        var patientInfo = consultPatientFile.getPatientFile(patientId);
        var admissionRecords: Array<AdmissionRecord> = consultPatientFile.getAllPatientAdmission(patientId)
        var prescriptions: Array<Medication> = consultPatientFile.getAllPatientPrescriptions(patientId)
        var infections: Array<Infection> = consultPatientFile.getAllPatientInfections(patientId)

        if (patientInfo == null){
            return ResponseEntity.notFound().build()
        }

        consultPatientFile.logAccess(employeeId, patientId)

        return ResponseEntity.ok(
            ConsultPatientFileResponse(patientInfo, admissionRecords, prescriptions, infections)
        )
    }

    @PutMapping("/consultPatientFile/{patientId}")
    fun updatePatient(@PathVariable("patientId") patientId: String, @RequestBody updateInfo: UpdatePatientInfoRequest): ResponseEntity<String>{

        val patientInfo = PatientCreateDto(
                govInsuranceNum = updateInfo.govInsuranceNum,
                firstName = updateInfo.firstName,
                lastName = updateInfo.lastName,
                address = AddressCreateDto(
                        updateInfo.patientStreetNum,
                        updateInfo.patientStreetName,
                        updateInfo.patientAptNum,
                        updateInfo.patientPostalCode,
                        updateInfo.patientCity,
                        updateInfo.patientProvince,
                        updateInfo.patientCountry,
                ),
                telephone = updateInfo.telephone,
                dateOfBirth = TypeUtils.parseDate(updateInfo.dateOfBirth),
                gender = updateInfo.gender,
                maritalStatus = updateInfo.martialStatus,
                doctorId = updateInfo.extDoctorId,
                nextOfKin = NextOfKinCreateDto(
                        firstName = updateInfo.nextOfKinFirstName,
                        lastName = updateInfo.nextOfKinLastName,
                        relationship = updateInfo.relationship,
                        address = AddressCreateDto(
                                updateInfo.nOKStreetNum,
                                updateInfo.nOKStreetName,
                                updateInfo.nOKAptNum,
                                updateInfo.nOKPostalCode,
                                updateInfo.nOKCity,
                                updateInfo.nOKProvince,
                                updateInfo.nOKCountry,
                        ),
                        telephone = updateInfo.nextOfKinTelephone,
        ))
        val update = updatePatientFile.updatePatientFile(updateInfo.patientId,updateInfo.employeeId,patientInfo);
        if (update) {
            return ResponseEntity.ok("Patient updated successfully")
        } else {
            return ResponseEntity.badRequest().body("Failed to update patient")
        }
    }

    @PostMapping("/infection")
    fun addInfection(@RequestBody request: InfectionRequest): ResponseEntity<String> {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as Jwt
        val employeeId = userDetails.subject

        val result = infection.addInfection(InfectionDto(
                request.patientId,
                TypeUtils.parseDate(request.startDate),
                TypeUtils.parseDate(request.endDate),
                request.name
        ), employeeId);

        if(result) {
            return ResponseEntity.ok().build()
        } else {
            return ResponseEntity.badRequest().build()
        }
    }

    @PutMapping("/infection/{infectionId}")
    fun updateInfection(@PathVariable("infectionId") infectionId: String, @RequestBody request: InfectionRequest): ResponseEntity<String> {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as Jwt
        val employeeId = userDetails.subject

        val result = infection.updateInfection(infectionId, InfectionDto(
                request.patientId,
                TypeUtils.parseDate(request.startDate),
                TypeUtils.parseDate(request.endDate),
                request.name
        ), employeeId);

        if(result) {
            return ResponseEntity.ok().build()
        } else {
            return ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequest): ResponseEntity<String> {

        val staffInfo = StaffMemberCreateDto(
                firstName = registerRequest.firstName,
                lastName = registerRequest.lastName,
                address = AddressCreateDto(streetName = registerRequest.streetName,
                streetNum = registerRequest.streetNumber, aptNumber = registerRequest.unit,
                postalCode = registerRequest.zipCode, city = registerRequest.city, province = registerRequest.province,
                country = registerRequest.country),
                telephone = registerRequest.phoneNumber,
                telephoneExt = registerRequest.teleExt as Int,
                dateOfBirth = TypeUtils.parseDate(registerRequest.dateOfBirth), //registerRequest.dateOfBirth as Date,
                employeeId = registerRequest.staffId,
                userName = registerRequest.email,
                modifyPermission = registerRequest.modPerm,
                password = registerRequest.password)
        if(registerRequest.staffType.equals("nurse")) {
            val nurse = ChargeNurseCreateDto(staffInfo = staffInfo, bipperExtension = registerRequest?.bipperExtension!!)
            if(registerStaff.registerChargeNurse(nurse)){
                return ResponseEntity.ok("Registration successful")
            }
            else{
                return ResponseEntity.badRequest().build();
            }

        }
        else if(registerRequest.staffType.equals("doctor")) {
            val doctor = DoctorCreateDto(
                    staffInfo = staffInfo,
                    divisionName = registerRequest?.divName!!)
            if(registerStaff.registerDoctor(doctor)){
                return ResponseEntity.ok("Registration successful")
            }
            else{
                return ResponseEntity.badRequest().build();
            }

        }
        else{
            if(registerStaff.registerGenericStaff(staffInfo)){
                return ResponseEntity.ok("Registration successful")
            }
            else{
                return ResponseEntity.badRequest().build();
            }
        }

    }


    @GetMapping("division/{id}")
    fun getDivisionById(@PathVariable id: String): ResponseEntity<GetDivisionResponse> {
        val division = visualizeDivision.getDivision(id)

        if(division == null) {
            return ResponseEntity.notFound().build()
        }

        val beds = visualizeDivision.getBeds(division.bedIds)
        val chargeNurseName = visualizeDivision.getChargeNurseName(division.chargeNurse)?: "Name not found!"

        return ResponseEntity.ok(GetDivisionResponse(division, beds, chargeNurseName))
    }

    @GetMapping("division")
    fun getDivision(): ResponseEntity<GetDivisionAllResponse> {
        return ResponseEntity.ok(GetDivisionAllResponse(
            visualizeDivision.getDivisions()
        ))
    }

    @GetMapping("admission/info/{id}")
    fun getAdmissionInfo(@PathVariable id: String): ResponseEntity<AdmissionInfoResponse> {
        val admission = dischargePatient.getAdmissionInfo(id);

        if(admission == null) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(AdmissionInfoResponse(
            admission.recordId,
            admission.patientId,
            admission.localDoctor,
            admission.divisionId,
            admission.privInsuranceNum as Long,
            Date().time,
            admission.roomNum,
            admission.bedNumber
        ))
    }

    @PostMapping("admission/discharge")
    fun postDischargePatient(@RequestBody dischargeRequest: DischargeRequest): ResponseEntity<Void> {
        val result = dischargePatient.dischargePatient(
            DischargePatientCreateDto(
                dischargeRequest.patientId,
                dischargeRequest.reasonForDischarge,
                dischargeRequest.divisionId))
        if(result) {
            return ResponseEntity.ok().build()
        } else {
            return ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("prescribe")
    fun postPrescribe(@RequestBody request: PrescribeMedicationRequest): ResponseEntity<String> {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as Jwt
        val employeeId = userDetails.subject
        val staffType = staffLogIn.getStaffType(employeeId);
        val inCare = prescribeMedication.isInCare(request.patientId, employeeId)

        if (staffType != StaffType.DOCTOR || !inCare) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("patient not in your care")
        }

        val result = prescribeMedication.prescribeMedication(request.patientId, MedicationCreateDto(
            request.drugNumber,
            request.drugName,
            request.unitsByDay,
            request.numAdminPerDay,
            Date(request.startDate),
            Date(request.endDate),
            request.methodOfAdmin
        ), employeeId);

        if(result) {
            return ResponseEntity.ok().build()
        } else {
            return ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("requestList/admitPatient")
    fun postAdmitPatientFromRequestList(@RequestBody admitPatientRequest: AdmitPatientRequest): ResponseEntity<Void> {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as Jwt
        val employeeId = userDetails.subject
        val divisionId = admitPatient.getNurseDivisionId(employeeId)

        if(divisionId != admitPatientRequest.divisionId) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .build()
        }

        val result = admitPatientFromRequestList.admitPatientFromRequestList(AdmitPatientCreateDto(
            divisionId,
            admitPatientRequest.patientId,
            admitPatientRequest.roomNum,
            admitPatientRequest.bedNum,
            admitPatientRequest.privateInsuranceNumber,
            admitPatientRequest.localDoctorId
        ))

        if(!result) {
            return ResponseEntity.badRequest().build()
        } else {
            return ResponseEntity.ok().build()
        }
    }

    @PostMapping("requestList/rejectPatient")
    fun postRejectPatientFromRequestList(@RequestBody rejectPatientRequest: RejectPatientAdmissionRequest): ResponseEntity<Void> {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as Jwt
        val employeeId = userDetails.subject
        val divisionId = admitPatient.getNurseDivisionId(employeeId)

        if(divisionId != rejectPatientRequest.divisionId) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .build()
        }

        admitPatientFromRequestList.rejectPatientAdmission(rejectPatientRequest.divisionId, rejectPatientRequest.patientId)

        return ResponseEntity.ok().build()
    }

    @PostMapping("patient/register")
    fun postRegisterPatient(@RequestBody registerPatientRequest: RegisterPatientRequest): ResponseEntity<Void> {

        val patientInfo = PatientCreateDto(
            firstName = registerPatientRequest.firstName,
            lastName = registerPatientRequest.lastName,
            gender = registerPatientRequest.gender,
            dateOfBirth = TypeUtils.parseDate(registerPatientRequest.dateOfBirth),
            maritalStatus = registerPatientRequest.maritalStatus,
            telephone = registerPatientRequest.telephone,
            doctorId = registerPatientRequest.extDoctorID.toString(),
            address = AddressCreateDto(
                streetNum = registerPatientRequest.address.streetNum as Int,
                streetName = registerPatientRequest.address.streetName,
                aptNumber = registerPatientRequest.address.aptNumber as Int,
                postalCode = registerPatientRequest.address.postalCode,
                city = registerPatientRequest.address.city,
                province = registerPatientRequest.address.province,
                country = registerPatientRequest.address.country
            ),
            nextOfKin = NextOfKinCreateDto(
                firstName = registerPatientRequest.nextOfKin.firstName,
                lastName = registerPatientRequest.nextOfKin.lastName,
                address = AddressCreateDto(
                    streetNum = registerPatientRequest.nextOfKin.address.streetNum as Int,
                    streetName = registerPatientRequest.nextOfKin.address.streetName,
                    aptNumber = registerPatientRequest.nextOfKin.address.aptNumber as Int,
                    postalCode = registerPatientRequest.nextOfKin.address.postalCode,
                    city = registerPatientRequest.nextOfKin.address.city,
                    province = registerPatientRequest.nextOfKin.address.province,
                    country = registerPatientRequest.nextOfKin.address.country
                ),
                telephone = registerPatientRequest.nextOfKin.telephone,
                relationship = registerPatientRequest.nextOfKin.relationshipToPatient
            ),
            govInsuranceNum = registerPatientRequest.govInsurNum,
        )
        if(registerPatient.registerPatient(patientInfo)){
            return ResponseEntity.ok().build()
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
}