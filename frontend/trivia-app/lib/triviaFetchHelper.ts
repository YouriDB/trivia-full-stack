
import { ClientQuestion } from "@/models/ClientQuestion";
import { SessionStartResponse } from "../models/SessionStartResponse"
import { ClientAnswer } from "@/models/ClientAnswer";
import { SessionEndResponse } from "@/models/SessionEndResponse";

const apiUrl = process.env.API_URL;

export async function getQuestions(): Promise<ClientQuestion[]> {
  return await fetch(`${apiUrl}/questions?amount=3`, {
    method: "get",
    headers: {
        "Content-Type": "application/json",
    }
  })
  .then((response) =>  {
    return response.json()
  })
  .then((data) => {
    var sessionStartResponse = data as SessionStartResponse;
    localStorage.setItem("sessionId", sessionStartResponse.sessionId);
    return sessionStartResponse.questions;
  })
  .catch((err) => {
    if (err.status === 400) {
        // Handle later
        throw new Error("Unexpected API status: " + err.body);
    } else {
        throw new Error("Unexpected API status: " + err.body);
    }
  }); 
}

export async function checkAnswers(ClientAnswers: ClientAnswer[]): Promise<Map<string, boolean>> {

    return await fetch(`${apiUrl}/checkanswers?sessionId=${localStorage.getItem("sessionId")}`, {
        method: "post",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(ClientAnswers)
    })
    .then((response) =>  {
        return response.json()
    })
    .then((data) => {
        var sessionStartResponse = data as SessionEndResponse;
        return new Map(Object.entries(sessionStartResponse.grading));
    })
    .catch((err) => {
        if (err.status === 400) {
            // Handle later
            throw new Error("Unexpected API status: " + err.body);
        } else {
            throw new Error("Unexpected API status: " + err.body);
        }
    }); 

}