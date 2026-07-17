'use client';

import { useState } from "react";
import Spinner from "./spinner";
import { getQuestions } from "@/lib/triviaFetchHelper";
import { redirect } from "next/navigation";
import QuizSettings from "./quizSettings";

export default function StartButton() {

    const [isLoading, setLoading] = useState<boolean>(false);

    const [amount, setAmount] = useState<number>(10);
    const [type, setType] = useState<string>("");
    const [category, setCategory] = useState<string>("");
    const [difficulty, setDifficulty] = useState<string>("");
    
    function quizSettingsChanged(label: string, value: string) {
        switch(label) {
            case "Amount":
                setAmount(parseInt(value));
                break;
            case "Type":
                setType(value);
                break;
            case "Category":
                setCategory(value)
                break;
            case "Difficulty":
                setDifficulty(value);
                break;
        }
    }
    

    async function handleStart() {
        setLoading(true);
        let questions = await getQuestions(amount, type, difficulty, category);
        
        sessionStorage.setItem("clientQuestions", JSON.stringify(questions));

        setLoading(false);
        redirect("/quiz")
    }
    
    return (
        <>
            {isLoading && 
                <>
                    <Spinner />
                    <button disabled={isLoading} 
                        className="rounded-xl bg-indigo-700 px-9 py-6 font-semibold text-white/50 shadow-md transition text-3xl">
                        Starting Quiz...
                    </button>
                </>
            }
            {
                !isLoading &&
                <button disabled={isLoading}
                    onClick={handleStart} 
                    className="rounded-xl bg-indigo-600 px-9 py-6 font-semibold text-white shadow-md transition hover:bg-indigo-700 hover:shadow-lg text-3xl cursor-pointer">
                    Start Quiz
                </button>
            }
            <QuizSettings dropdownValueChanged={quizSettingsChanged}/>
        </>
        
    )
}