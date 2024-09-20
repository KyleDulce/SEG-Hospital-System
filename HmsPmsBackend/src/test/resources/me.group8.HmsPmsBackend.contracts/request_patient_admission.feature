Feature: Request Patient Admission
  Scenario: Charge Nurse requests patient admission to a division
    Given the Charge Nurse is signed in
    And the division is not full
    And patient is in the system
    When requestPatientAdmission is invoked
    Then patient goes on waiting list
    And response is successful

  Scenario: Charge Nurse receives an error message for an already admitted patient
    Given the Charge Nurse is signed in
    And the division is not full
    And the patient is already admitted to a ward
    When requestPatientAdmission is invoked
    Then patient does not go on waiting list
    And response is not successful
