Feature: Discharge Patient
  Scenario: Charge Nurse discharges a patient from the healthcare facility
    Given the Charge Nurse is signed in
    And the patient is admitted
    When dischargePatient is invoked
    Then bed is available
    And discharge info is generated
    And response is successful

  Scenario: Charge Nurse discharges patient not admitted
    Given the Charge Nurse is signed in
    And the patient is not admitted
    When dischargePatient is invoked
    Then nothing happens
    And response is not successful
