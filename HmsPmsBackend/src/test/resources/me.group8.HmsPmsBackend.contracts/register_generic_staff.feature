Feature: Register Generic Staff
  Scenario: Generic Staff registers in the Hospital Management System
    Given the Generic Staff member has a valid employee number in the HRS
    When registerGenericStaff is invoked
    Then staff registered
    And response is successful

  Scenario: Generic Staff member receives an error message when not found in the system
    Given the Generic Staff member's employee number is not found in the HRS
    When registerGenericStaff is invoked
    Then staff not registered
    And response is not successful


