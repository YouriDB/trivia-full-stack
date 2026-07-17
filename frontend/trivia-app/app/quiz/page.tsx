"use client";

import QuestionCard from "@/components/ui/questionCard";
import { ClientAnswer } from "@/models/ClientAnswer";
import { ClientQuestion } from "@/models/ClientQuestion";
import { redirect } from "next/navigation";
import { useEffect, useState } from "react";

export default function QuizPage() {
    const [clientQuestions, setClientQuestions] = useState<ClientQuestion[]>([]);
    const [clientQuestionIndex, setClientQuestionIndex] = useState<number>(0);
    const [clientAnswers, setClientAnswers] = useState<ClientAnswer[]>([]);

    useEffect(() => {
        const clientQuestionsJson = sessionStorage.getItem("clientQuestions");
        if (clientQuestionsJson) {
            setClientQuestions(JSON.parse(clientQuestionsJson));
        }
    }, []);

    function handleAnswerSelected(question: string, answer: string): void {
        let clientAnswer: ClientAnswer = {question: question, answer: answer};
        const newClientAnswers = clientAnswers.concat(clientAnswer);
        setClientAnswers(newClientAnswers);

        console.log(newClientAnswers)
        if (clientQuestionIndex + 1 < clientQuestions.length) {
            // Still questions left increase index
            setClientQuestionIndex(clientQuestionIndex + 1);
        } else {
            // Done with answering, submit
            sessionStorage.setItem("clientAnswers", JSON.stringify(newClientAnswers));
            redirect("/results");
        }
        
    }

    return (
        <>
        {clientQuestions !== undefined && clientQuestions.length > 0 &&
            <QuestionCard
            key={clientQuestions[clientQuestionIndex].question}
            question={clientQuestions[clientQuestionIndex].question}
            answers={clientQuestions[clientQuestionIndex].answers}
            onAnswerSelect={handleAnswerSelected}
            />
        }
        </>
  );
}