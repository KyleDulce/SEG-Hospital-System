Feature: Register Patient
  Scenario: Medical Staff Member registers a new patient into the hospital's information system
    Given staff is signed in
    When registerPatient is invoked
    Then patient registered
    And response is successful
