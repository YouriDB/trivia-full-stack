'use client';

import { redirect } from "next/navigation"

export default function Header() {
    function handleClick() {
        redirect('/dashboard');
    }

    return (
        <div className="z-50 w-full px-4 py-3 border border-0 border-light bg-indigo-700">
            <h1 onClick={handleClick} className="text-heading flex items-center text-5xl font-bold tracking-tight text-white w-fit cursor-pointer">Trivia Quiz</h1>
        </div>
        
        
    )
}