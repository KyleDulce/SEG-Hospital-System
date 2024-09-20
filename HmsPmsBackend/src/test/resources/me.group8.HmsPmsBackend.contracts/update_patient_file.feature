Feature: Update Patient File
  Scenario: Medical Staff Member updates a patient's information
    Given staff has permission
    And patient is in the system
    When updatePatientFile is invoked
    Then patient is updated
    And response is successful

  Scenario: Medical Staff Member lacks necessary privileges for modifications
    Given staff does not have permission
    And patient is in the system
    When updatePatientFile is invoked
    Then patient is not updated
    And response is not successful
