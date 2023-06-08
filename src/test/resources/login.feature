Feature: Login
  Scenario: Post request to /login will result in jwt token
    Given I have a valid user object
    When I call the login endpoint
    Then I receive a status of 200
    And I get a JWT-token