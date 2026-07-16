Feature: client retrieves questions

    Scenario: Client succesfully retrieves questions
        Given the opentdb api returns questions
        When the client requests questions from "/questions?amount=3"
        Then the response status should be 200
        And the response should contain a session ID
        And the response should contain the retrieved questions
        And the questions should be cached

    Scenario: Client inputs invalid difficulty value
        Given the opentdb api returns questions
        When the client requests questions from "/questions?amount=3&difficulty=extreme"
        Then the response status should be 400

    Scenario: Client inputs too big of an amount value
        Given the opentdb api returns questions
        When the client requests questions from "/questions?amount=52"
        Then the response status should be 400
