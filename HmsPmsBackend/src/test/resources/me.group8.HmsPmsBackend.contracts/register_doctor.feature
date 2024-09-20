Feature: Register Doctor
  Scenario: Doctor registers in the Hospital Management System
    Given the Doctor has a valid employee number in the HRS
    When registerDoctor is invoked
    Then doctor registered
    And response is successful

  Scenario: Doctor receives an error message when not found in the system
    Given the Doctor's employee number is not found in the HRS
    When registerDoctor is invoked
    Then doctor not registered
    And response is not successful

