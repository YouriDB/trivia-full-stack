Feature: client retrieves questions

    Scenario: Succesfully retrieve questions
        Given the opentdb api returns questions
        When the client requests questions from "/questions"
        Then the response status should be 200
        And the response should contain a session ID
        And the response should contain the retrieved questions
