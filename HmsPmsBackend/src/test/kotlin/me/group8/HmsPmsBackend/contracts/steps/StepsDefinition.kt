package me.group8.HmsPmsBackend.contracts.steps

import io.cucumber.java8.En
import io.cucumber.java8.Scenario
import me.group8.HmsPmsBackend.application.dtos.queries.*

import me.group8.HmsPmsBackend.application.usecases.*
import me.group8.HmsPmsBackend.application.usecases.impl.*
import me.group8.HmsPmsBackend.domain.medicaldivision.entities.*
import me.group8.HmsPmsBackend.domain.medicaldivision.facades.MedicalDivisionFacade
import me.group8.HmsPmsBackend.domain.medicaldivision.facades.impl.MedicalDivisionFacadeImpl
import me.group8.HmsPmsBackend.domain.medication.entities.*
import me.group8.HmsPmsBackend.domain.medication.facades.MedicationFacade
import me.group8.HmsPmsBackend.domain.medication.facades.impl.MedicationFacadeImpl
import me.group8.HmsPmsBackend.domain.medication.factories.MedicationFactory
import me.group8.HmsPmsBackend.domain.patient.entities.*
import me.group8.HmsPmsBackend.domain.patient.facades.PatientFacade
import me.group8.HmsPmsBackend.domain.patient.facades.impl.PatientFacadeImpl
import me.group8.HmsPmsBackend.domain.patient.factories.*
import me.group8.HmsPmsBackend.domain.staff.entities.*
import me.group8.HmsPmsBackend.domain.staff.facade.StaffFacade
import me.group8.HmsPmsBackend.domain.staff.facade.impl.StaffFacadeImpl
import me.group8.HmsPmsBackend.domain.staff.factories.StaffFactory
import me.group8.HmsPmsBackend.teststubs.repositories.*
import me.group8.HmsPmsBackend.teststubs.services.*

import org.junit.jupiter.api.Assertions.*

@Suppress("unused")
class StepsDefinition: En {

    // Use Cases
    private lateinit var admitPatient: AdmitPatient
    private lateinit var admitPatientFromRequestList: AdmitPatientFromRequestList
    private lateinit var dischargePatient: DischargePatient
    private lateinit var prescribeMedication: PrescribeMedication
    private lateinit var registerPatient: RegisterPatient
    private lateinit var registerStaff: RegisterStaff
    private lateinit var requestPatientAdmission: RequestPatientAdmission
    private lateinit var updatePatientFile: UpdatePatientFile

    // Facades
    private lateinit var medicalDivisionFacade: MedicalDivisionFacade
    private lateinit var medicationFacade: MedicationFacade
    private lateinit var patientFacade: PatientFacade
    private lateinit var staffFacade: StaffFacade

    // Repositories
    private lateinit var bedRepository: BedRepositoryStub
    private lateinit var medicalDivisionRepository: MedicalDivisionRepositoryStub
    private lateinit var medicationRepository: MedicationRepositoryStub
    private lateinit var admissionRecordRepository: AdmissionRecordRepositoryStub
    private lateinit var patientRepository: PatientRepositoryStub
    private lateinit var staffRepository: StaffRepositoryStub

    // Factories
    private lateinit var medicationFactory: MedicationFactory
    private lateinit var admissionRecordFactory: AdmissionRecordFactory
    private lateinit var patientFactory: PatientFactory
    private lateinit var staffFactory: StaffFactory

    // Services
    private lateinit var staffNotificationService: StaffNotificationServiceStub
    private lateinit var extNotificationService: ExtNotificationServiceStub
    private lateinit var hrService: HRServiceStub

    // Other
    private lateinit var domainEventEmitter: DomainEventEmitterStub

    // Entities
    // Medical Division
    private var admitRequest: AdmitRequest? = null
    private var bed: Bed? = null
    private var medicalDivision: MedicalDivision? = null

    // Medication
    private var medication: Medication? = null
    private var medicationAdministration: MedicationAdministration? = null

    // Patient
    private var patient: Patient? = null
    private var admissionRecord: AdmissionRecord? = null

    // Staff
    private var staff: GenericStaff? = null
    private var chargeNurse: ChargeNurse? = null
    private var doctor: Doctor? = null

    // Other dynamic vars
    private val registeredId: String = "Some Random Reg Id"
    private val actingDoctorId: String = "Some Random Doctor Id"
    private var oldPatientInfo: Patient? = null
    private var requestedPatientInfoChange: PatientCreateDto? = null
    private var result: Boolean? = null

    init {

        Before { _: Scenario ->
            // Vars
            admitRequest = null
            bed = null
            medicalDivision = null

            medication = null
            medicationAdministration = null

            patient = null
            admissionRecord = null

            staff = null
            chargeNurse = null
            doctor = null

            oldPatientInfo = null
            requestedPatientInfoChange = null

            // Dependencies
            // Other
            domainEventEmitter = DomainEventEmitterStub()

            // Services
            staffNotificationService = StaffNotificationServiceStub()
            extNotificationService = ExtNotificationServiceStub()
            hrService = HRServiceStub()

            // Factories
            medicationFactory = MedicationFactory()
            admissionRecordFactory = AdmissionRecordFactory()
            patientFactory = PatientFactory()
            staffFactory = StaffFactory()

            // Repository
            bedRepository = BedRepositoryStub()
            medicalDivisionRepository = MedicalDivisionRepositoryStub()
            medicationRepository = MedicationRepositoryStub()
            admissionRecordRepository = AdmissionRecordRepositoryStub()
            patientRepository = PatientRepositoryStub()
            staffRepository = StaffRepositoryStub()

            // Facades
            medicalDivisionFacade = MedicalDivisionFacadeImpl(
                medicalDivisionRepository,
                bedRepository)
            medicationFacade = MedicationFacadeImpl(
                medicationRepository,
                medicationFactory
            )
            patientFacade = PatientFacadeImpl(
                patientFactory,
                patientRepository,
                admissionRecordFactory,
                admissionRecordRepository,
                extNotificationService)
            staffFacade = StaffFacadeImpl(
                staffRepository,
                staffFactory,
                domainEventEmitter,
                staffNotificationService,
                hrService
            )

            // Use Cases
            admitPatient = AdmitPatientImpl(
                medicalDivisionFacade,
                patientFacade
            )
            admitPatientFromRequestList = AdmitPatientFromRequestListImpl(
                medicalDivisionFacade,
                staffFacade,
                admitPatient
            )
            dischargePatient = DischargePatientImpl(
                patientFacade,
                medicalDivisionFacade
            )
            prescribeMedication = PrescribeMedicationImpl(
                medicationFacade,
                patientFacade
            )
            registerPatient = RegisterPatientImpl(patientFacade)
            registerStaff = RegisterStaffImpl(staffFacade)
            requestPatientAdmission = RequestPatientAdmissionImpl(medicalDivisionFacade, patientFacade)
            updatePatientFile = UpdatePatientFileImpl(patientFacade, staffFacade)
        }

        // ===== GIVEN =====

        Given("the Charge Nurse is signed in") {
            chargeNurse = StepUtils.createChargeNurse()
            staff = chargeNurse
            assertNotNull(chargeNurse)
            staffRepository.saveNurse(chargeNurse!!)
        }

        Given("doctor is signed in") {
            doctor = StepUtils.createDoctor()
            staff = doctor
            assertNotNull(doctor)
            staffRepository.saveDoctor(doctor!!)
        }

        Given("staff is signed in") {
            staff = StepUtils.createGenericStaff()
            assertNotNull(staff)
            staffRepository.saveStaff(staff!!)
        }

        Given("staff has permission") {
            staff = StepUtils.createGenericStaff()
            assertNotNull(staff)
            staffRepository.saveStaff(staff!!)
        }

        Given("staff does not have permission") {
            staff = StepUtils.createGenericStaff(false)
            assertNotNull(staff)
            staffRepository.saveStaff(staff!!)
        }

        Given("the Charge Nurse has a valid employee number in the HRS") {
            hrService.employees.add(registeredId)
        }

        Given("the Doctor has a valid employee number in the HRS") {
            hrService.employees.add(registeredId)
        }

        Given("the Generic Staff member has a valid employee number in the HRS") {
            hrService.employees.add(registeredId)
        }

        Given("the Charge Nurse's employee number is not found in the HRS") {
            // nothing happens
        }

        Given("the Doctor's employee number is not found in the HRS") {
            // nothing happens
        }

        Given("the Generic Staff member's employee number is not found in the HRS") {
            // nothing happens
        }

        Given("the requested bed is available") {
            bed = StepUtils.createBed()
            assertNotNull(bed)
            StepUtils.saveBedToRepo(bed!!, bedRepository)
        }

        Given("the requested bed is not available") {
            bed = StepUtils.createBed(false)
            assertNotNull(bed)
            StepUtils.saveBedToRepo(bed!!, bedRepository)
        }

        Given("the division is not full") {
            if(bed == null) {
                bed = StepUtils.createBed(true)
            }
            medicalDivision = StepUtils.createDivision(false, bed)
            medicalDivisionRepository.divMap[medicalDivision!!.identifier] = medicalDivision!!
            assertNotNull(medicalDivision)
        }

        Given("the division is full") {
            if(bed == null) {
                bed = StepUtils.createBed(false)
            }
            medicalDivision = StepUtils.createDivision(true, bed)
            medicalDivisionRepository.divMap[medicalDivision!!.identifier] = medicalDivision!!

            assertNotNull(medicalDivision)
        }

        Given("patient is on the request list") {
            admitRequest = StepUtils.createAdmitRequest(patient!!.patientId, chargeNurse!!.employeeId)
            assertNotNull(admitRequest)
            medicalDivision?.admitReqs?.add(admitRequest!!)
        }

        Given("patient is not on the request list") {
            // Do nothing
        }

        Given("the patient is admitted") {
            patient = StepUtils.createPatient()
            patientRepository.save(patient!!)
            admissionRecord = StepUtils.createAdmission(false, patient!!.patientId)
            admissionRecordRepository.save(admissionRecord!!)
            bed = StepUtils.createBed(false)
            bedRepository.bedMap[bed!!.identifier] = bed!!
            medicalDivision = StepUtils.createDivision(bed = bed)
            medicalDivisionRepository.divMap[medicalDivision!!.identifier] = medicalDivision!!
            assertNotNull(patient)
            assertNotNull(admissionRecord)
            assertNotNull(bed)
            assertNotNull(medicalDivision)
        }

        Given("the patient is not admitted") {
            patient = StepUtils.createPatient()
            patientRepository.save(patient!!)
            bed = StepUtils.createBed(false)
            bedRepository.bedMap[bed!!.identifier] = bed!!
            medicalDivision = StepUtils.createDivision(bed = bed)
            medicalDivisionRepository.divMap[medicalDivision!!.identifier] = medicalDivision!!
            assertNotNull(patient)
            assertNotNull(bed)
            assertNotNull(medicalDivision)
        }

        Given("the patient is already admitted to a ward") {
            patient = StepUtils.createPatient()
            patientRepository.save(patient!!)
            admissionRecord = StepUtils.createAdmission(false, patient!!.patientId)
            admissionRecordRepository.save(admissionRecord!!)
            assertNotNull(patient)
            assertNotNull(admissionRecord)
        }

        Given("the patient is under care of the doctor") {
            patient = StepUtils.createPatient()
            patientRepository.save(patient!!)
            admissionRecord = StepUtils.createAdmission(false, patient!!.patientId, actingDoctorId)
            admissionRecordRepository.save(admissionRecord!!)
            assertNotNull(patient)
            assertNotNull(admissionRecord)
        }

        Given("the patient is not under care of the doctor") {
            patient = StepUtils.createPatient()
            patientRepository.save(patient!!)
            admissionRecord = StepUtils.createAdmission(false, patient!!.patientId, "Bad doctor")
            admissionRecordRepository.save(admissionRecord!!)
            assertNotNull(patient)
            assertNotNull(admissionRecord)
        }

        Given("patient is in the system") {
            patient = StepUtils.createPatient()
            patientRepository.save(patient!!)
            assertNotNull(patient)
        }

        // ===== When =====
        When("admitPatient is invoked") {
            assertNotNull(medicalDivision)
            assertNotNull(patient)
            assertNotNull(bed)

            val admitPatientCreateDto = AdmitPatientCreateDto(
                medicalDivision!!.identifier,
                patient!!.patientId,
                bed!!.roomNum,
                bed!!.bedNum,
                5,
                "Some id"
            )

            result = admitPatient.admitPatient(admitPatientCreateDto)
        }

        When("admitPatientFromRequestList is invoked") {
            assertNotNull(medicalDivision)
            assertNotNull(patient)
            assertNotNull(bed)

            val admitPatientCreateDto = AdmitPatientCreateDto(
                medicalDivision!!.identifier,
                patient!!.patientId,
                bed!!.roomNum,
                bed!!.bedNum,
                5,
                "Some id"
            )

            result = admitPatientFromRequestList.admitPatientFromRequestList(admitPatientCreateDto)
        }

        When("dischargePatient is invoked") {
            assertNotNull(patient)
            assertNotNull(medicalDivision)

            val dischargePatientCreateDto = DischargePatientCreateDto(
                patient!!.patientId,
                "Reason",
                medicalDivision!!.identifier
            )

            result = dischargePatient.dischargePatient(dischargePatientCreateDto)
        }

        When("prescribeMedication is invoked") {
            assertNotNull(patient)

            val medicationCreateDto = MedicationCreateDto(
                1,
                "Best",
                1,
                1,
                StepUtils.createDate(),
                StepUtils.createDate(),
                "destruction"
            )

            result = prescribeMedication.prescribeMedication(patient!!.patientId, medicationCreateDto, actingDoctorId)
        }

        When("registerChargeNurse is invoked") {
            val chargeNurseCreateDto = ChargeNurseCreateDto(
                StepUtils.createStaffMemberCreateDto(registeredId),
                2
            )

            result = registerStaff.registerChargeNurse(chargeNurseCreateDto)
        }

        When("registerDoctor is invoked") {
            val doctorCreateDto = DoctorCreateDto(
                StepUtils.createStaffMemberCreateDto(registeredId),
                "some division"
            )

            result = registerStaff.registerDoctor(doctorCreateDto)
        }

        When("registerGenericStaff is invoked") {
            val staffMemberCreateDto = StepUtils.createStaffMemberCreateDto(registeredId)

            result = registerStaff.registerGenericStaff(staffMemberCreateDto)
        }

        When("registerPatient is invoked") {
            val patientCreateDto = StepUtils.createPatientDto()

            result = registerPatient.registerPatient(patientCreateDto)
        }

        When("rejectPatientAdmission is invoked") {
            assertNotNull(medicalDivision)
            assertNotNull(patient)

            admitPatientFromRequestList.rejectPatientAdmission(medicalDivision!!.identifier, patient!!.patientId)
        }

        When("requestPatientAdmission is invoked") {
            assertNotNull(medicalDivision)
            assertNotNull(patient)
            assertNotNull(chargeNurse)

            val requestPatientAdmitPatientCreateDto = RequestPatientAdmissionCreateDto(
                medicalDivision!!.identifier,
                patient!!.patientId,
                chargeNurse!!.employeeId,
                "Yes",
                5
            )

            result = requestPatientAdmission.requestPatientAdmission(requestPatientAdmitPatientCreateDto)
        }

        When("updatePatientFile is invoked") {
            assertNotNull(patient)
            assertNotNull(staff)

            requestedPatientInfoChange = PatientCreateDto(
                "new name",
                "other new name",
                StepUtils.createAddressDto(),
                10,
                StepUtils.createDate(),
                "new gender",
                "new status",
                "new doctor",
                StepUtils.createNextOfKinDto(),
                100
            )
            oldPatientInfo = StepUtils.clonePatient(patient!!)

            result = updatePatientFile.updatePatientFile(patient!!.patientId, staff!!.employeeId, requestedPatientInfoChange!!)
        }

        // ===== Then =====
        Then("patient is admitted to room in division") {
            assertNotNull(bed)
            assertNotNull(admissionRecordRepository)

            assertFalse(bed!!.isAvailable)
            assertEquals(1, admissionRecordRepository.recordIdAdmissionMap.size)
        }

        Then("patient is not admitted") {
            assertNotNull(admissionRecordRepository)

            assertEquals(0, admissionRecordRepository.recordIdAdmissionMap.size)
        }

        Then("bed is available") {
            assertNotNull(bed)
            assertTrue(bed!!.isAvailable)
        }

        Then("discharge info is generated") {
            assertNotNull(admissionRecord)

            assertTrue(admissionRecord!!.isDischarged)
        }

        Then("the prescription is recorded") {
            assertEquals(1, medicationRepository.meds.size)
        }

        Then("the prescription is not recorded") {
            assertEquals(0, medicationRepository.meds.size)
        }

        Then("charge nurse registered") {
            assertEquals(1, staffRepository.staffMap.size)
            assertTrue(staffRepository.staffMap[registeredId] is ChargeNurse)
        }

        Then("doctor registered") {
            assertEquals(1, staffRepository.staffMap.size)
            assertTrue(staffRepository.staffMap[registeredId] is Doctor)
        }

        Then("staff registered") {
            assertEquals(1, staffRepository.staffMap.size)
            assertFalse(staffRepository.staffMap[registeredId] is ChargeNurse)
            assertFalse(staffRepository.staffMap[registeredId] is Doctor)
        }

        Then("staff not registered") {
            assertEquals(0, staffRepository.staffMap.size)
        }

        Then("doctor not registered") {
            assertEquals(0, staffRepository.staffMap.size)
        }

        Then("charge nurse not registered") {
            assertEquals(0, staffRepository.staffMap.size)
        }

        Then("patient registered") {
            assertEquals(1, patientRepository.patientMap.size)
        }

        Then("patient not registered") {
            assertEquals(0, patientRepository.patientMap.size)
        }

        Then("patient removed from request list") {
            assertNotNull(medicalDivision)
            assertEquals(0, medicalDivision!!.admitReqs.size)
        }

        Then("patient goes on waiting list") {
            assertNotNull(medicalDivision)
            assertEquals(1, medicalDivision!!.admitReqs.size)
            assertEquals(patient!!.patientId, medicalDivision!!.admitReqs[0].patientId)
        }

        Then("patient does not go on waiting list") {
            assertNotNull(medicalDivision)
            assertEquals(0, medicalDivision!!.admitReqs.size)
        }

        Then("patient is updated") {
            assertNotNull(patientRepository)
            assertNotNull(patient)
            assertNotNull(requestedPatientInfoChange)

            assertEquals(1, patientRepository.patientMap.size)
            val npatient = patientRepository.patientMap[patient!!.patientId]

            StepUtils.assertPatientEqualToDto(npatient!!, requestedPatientInfoChange!!)
        }

        Then("patient is not updated") {
            assertNotNull(patientRepository)
            assertNotNull(patient)
            assertNotNull(requestedPatientInfoChange)

            assertEquals(1, patientRepository.patientMap.size)
            val npatient = patientRepository.patientMap[patient!!.patientId]

            StepUtils.assertPatientsEqual(npatient!!, patient!!)
        }

        Then("response is successful") {
            assertNotNull(result)
            assertTrue(result!!)
        }

        Then("response is not successful") {
            assertNotNull(result)
            assertFalse(result!!)
        }

        Then("nothing happens") {
            // Nothing check, verify no exceptions
        }
    }
}