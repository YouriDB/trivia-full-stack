"use client";

import AnswerResult from "@/components/ui/answerResult";
import Spinner from "@/components/ui/spinner";
import { checkAnswers } from "@/lib/triviaFetchHelper";
import { ClientQuestion } from "@/models/ClientQuestion";
import { redirect } from "next/navigation";
import { useEffect, useState } from "react";

export default function ResultsPage() {
    const [clientQuestions, setClientQuestions] = useState<ClientQuestion[]>([]);
    const [grading, setGrading] = useState<Map<string, boolean>>();
    const [index, setIndex] = useState<number>(0);
    const [isFetching, setIsFetching] = useState<boolean>(false);

    useEffect(() => {
        const clientQuestionsJson = sessionStorage.getItem("clientQuestions");
        const clientAnswersJson = sessionStorage.getItem("clientAnswers");

        if (clientQuestionsJson) {
            setClientQuestions(JSON.parse(clientQuestionsJson));
        }

        // Fetch results
        if (clientAnswersJson && !isFetching) {
            setIsFetching(true);   

            checkAnswers(JSON.parse(clientAnswersJson))
            .then((grading) => {
                setGrading(grading);
            })
            .catch((err) => {
                console.error("Failed to check answers", err);
            });
             
        }
    }, []);

    function handleNextClicked(): void {
        if (!grading) {
            return;
        }

        if (index + 1 < clientQuestions.length) {
            // Still answers left increase index
            setIndex(index + 1);
        } else {
            // Done with answering, submit
            redirect("/dashboard");
        }
        
    }

    return (
        <>
            {!grading && 
                <Spinner />
            }
            {grading && clientQuestions[index] && 
                
                <AnswerResult 
                    question={clientQuestions[index].question}
                    correct={grading.get(clientQuestions[index].question) ?? false}
                    onNextClick={handleNextClicked}
                />
            }
        </>
    );
}