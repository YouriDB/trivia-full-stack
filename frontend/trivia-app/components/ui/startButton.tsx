'use client';

import { useState } from "react";
import Spinner from "./spinner";
import { getQuestions } from "@/lib/triviaFetchHelper";
import { redirect } from "next/navigation";

export default function StartButton() {

    const [isLoading, setLoading] = useState<boolean>(false);
    

    async function handleStart() {
        setLoading(true);
        let questions = await getQuestions();
        
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
            
        </>
        
    )
}