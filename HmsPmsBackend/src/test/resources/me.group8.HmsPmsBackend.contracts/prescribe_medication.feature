Feature: Prescribe Medication
  Scenario: Doctor prescribes medication for a selected patient
    Given doctor is signed in
    And the patient is under care of the doctor
    When prescribeMedication is invoked
    Then the prescription is recorded
    And response is successful

  Scenario: Doctor prescribes medication for a patient not under their care
    Given doctor is signed in
    And the patient is not under care of the doctor
    When prescribeMedication is invoked
    And the prescription is not recorded
    And response is not successful
