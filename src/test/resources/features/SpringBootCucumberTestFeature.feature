Feature: Rest API functionalites

  Scenario: User is able to register and login
    Given That an agent is able to register
    When I login to my account
    Then JWT key is returned