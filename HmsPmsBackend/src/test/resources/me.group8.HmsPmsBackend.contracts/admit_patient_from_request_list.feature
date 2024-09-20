Feature: Admit Patient from Request List
  Scenario: Charge Nurse admits a patient from the request list
    Given the Charge Nurse is signed in
    And the requested bed is available
    And the division is not full
    And patient is in the system
    And patient is on the request list
    When admitPatientFromRequestList is invoked
    Then patient is admitted to room in division
    And response is successful

  Scenario: Charge Nurse admits a patient not on request list
    Given the Charge Nurse is signed in
    And the requested bed is available
    And the division is not full
    And patient is in the system
    And patient is not on the request list
    When admitPatientFromRequestList is invoked
    Then patient is not admitted
    And response is not successful

  Scenario: Charge Nurse admits patient into full division
    Given the Charge Nurse is signed in
    And the requested bed is available
    And the division is full
    And patient is in the system
    And patient is on the request list
    When admitPatientFromRequestList is invoked
    Then patient is not admitted
    And response is not successful
