Feature: client submits answers

    Scenario: Client succesfully submits all correct answers
        Given the opentdb api returns questions
        And the client requested questions from "/questions?amount=3"
        When the client submits correct answers to "/checkanswers"
        Then the response status should be 200
        And the response should contain a grading
        And the answers should be correct
        And the cached session should be deleted

    Scenario: Client succesfully submits all incorrect answers
        Given the opentdb api returns questions
        And the client requested questions from "/questions?amount=3"
        When the client submits incorrect answers to "/checkanswers"
        Then the response status should be 200
        And the response should contain a grading
        And the answers should be incorrect
        And the cached session should be deleted