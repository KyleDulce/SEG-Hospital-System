Feature: Admit Patient
 Scenario: Charge Nurse is signed in and admits a patient in a division that is not full
  Given the Charge Nurse is signed in
  And the requested bed is available
  And the division is not full
  And patient is in the system
  When admitPatient is invoked
  Then patient is admitted to room in division
  And response is successful

 Scenario: Charge Nurse is signed in and admits a patient in a division that is full
  Given the Charge Nurse is signed in
  And the requested bed is available
  And the division is full
  And patient is in the system
  When admitPatient is invoked
  Then patient is not admitted
  And response is not successful

 Scenario: Charge Nurse is signed in and admits a patient in a bed that is not available
  Given the Charge Nurse is signed in
  And the requested bed is not available
  And the division is full
  And patient is in the system
  When admitPatient is invoked
  Then patient is not admitted
  And response is not successful
