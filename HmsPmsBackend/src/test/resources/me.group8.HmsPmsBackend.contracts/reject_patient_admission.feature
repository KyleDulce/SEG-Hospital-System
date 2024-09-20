Feature: Reject Patient Admission
  Scenario: Charge Nurse rejects a patient's admission from the request list
    Given the Charge Nurse is signed in
    And the division is not full
    And patient is in the system
    And patient is on the request list
    When rejectPatientAdmission is invoked
    Then patient removed from request list

  Scenario: Charge Nurse rejects a patient not on request list
    Given the Charge Nurse is signed in
    And the division is not full
    And patient is in the system
    And patient is not on the request list
    When rejectPatientAdmission is invoked
    Then nothing happens
