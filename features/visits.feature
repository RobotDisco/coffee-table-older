Feature: Some bullshit
  As a bullshit
  I bullshit

Background:
  Given the server is running

Scenario: List visits
  When I list the visits
  Then I get a response code of 200

Scenario: Create visit
  When I create a visit with:
  Then I get a response code of 201
  And my response includes a "Location" header
