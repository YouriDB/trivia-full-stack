"use client";

import AnswerCard from "@/components/ui/answerCard";
import Spinner from "@/components/ui/spinner";
import { checkAnswers } from "@/lib/triviaFetchHelper";
import { SessionEndResponse } from "@/models/SessionEndResponse";
import { redirect } from "next/navigation";
import { useEffect, useState } from "react";

export default function ResultsPage() {
    const [sessionEndResponse, setSessionEndResponse] = useState<SessionEndResponse>();
    const [index, setIndex] = useState<number>(0);
    const [isFetching, setIsFetching] = useState<boolean>(false);

    useEffect(() => {
        const clientAnswersJson = sessionStorage.getItem("clientAnswers");

        // Fetch results
        if (clientAnswersJson && !isFetching) {
            setIsFetching(true);   

            checkAnswers(JSON.parse(clientAnswersJson))
            .then((sessionEndResponse) => {
                setSessionEndResponse(sessionEndResponse);
            })
            .catch((err) => {
                console.error("Failed to check answers", err);
            });
             
        }
    }, []);

    function handleNextClicked(): void {
        if (!sessionEndResponse) {
            return;
        }

        if (index + 1 < sessionEndResponse.questions.length) {
            // Still answers left increase index
            setIndex(index + 1);
        } else {
            // Done with answering, submit
            redirect("/dashboard");
        }
        
    }

    function aggregateAnswers(correctAnswer: string, incorrectAnswers: string[]): string[] {
        return [correctAnswer, ...incorrectAnswers];
    }

    return (
        <>
            {!sessionEndResponse && 
                <Spinner />
            }
            {sessionEndResponse &&
                
                <AnswerCard 
                    question={sessionEndResponse.questions[index].question}
                    answers={aggregateAnswers(sessionEndResponse.questions[index].correctAnswer, sessionEndResponse.questions[index].incorrectAnswers)}
                    grading={sessionEndResponse.grading.get(sessionEndResponse.questions[index].question) ?? false}
                    correctAnswer={sessionEndResponse.questions[index].correctAnswer}
                    onNextClick={handleNextClicked}
                />
            }
        </>
    );
}