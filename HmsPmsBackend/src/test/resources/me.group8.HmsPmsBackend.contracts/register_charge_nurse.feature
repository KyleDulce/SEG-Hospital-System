Feature: Register Charge Nurse
  Scenario: Charge Nurse registers in the Hospital Management System
    Given the Charge Nurse has a valid employee number in the HRS
    When registerChargeNurse is invoked
    Then charge nurse registered
    And response is successful

  Scenario: Charge Nurse receives an error message when not found in the system
    Given the Charge Nurse's employee number is not found in the HRS
    When registerChargeNurse is invoked
    Then charge nurse not registered
    And response is not successful
