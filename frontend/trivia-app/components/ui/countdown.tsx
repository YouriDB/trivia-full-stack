"use client"; 

import { useState, useRef, useEffect, ChangeEvent } from "react"; 

export default function Countdown({
    durationSet
}: {
    durationSet: number
}) {
    const [duration, setDuration] = useState<number | string>("");
    const [timeLeft, setTimeLeft] = useState<number>(0);
    const [isActive, setIsActive] = useState<boolean>(false);
    const timerRef = useRef<NodeJS.Timeout | null>(null);

    const handleSetDuration = (): void => {
    if (typeof duration === "number" && duration > 0) {
        setTimeLeft(duration);
        setIsActive(false);
        if (timerRef.current) {
        clearInterval(timerRef.current);
        }
    }
    };

    const handleStart = (): void => {
        if (timeLeft > 0) {
            setIsActive(true);
        }
    };

    const handleReset = (): void => {
        setIsActive(false);
        setTimeLeft(0);
        if (timerRef.current) {
            clearInterval(timerRef.current);
        }
    };

    useEffect(() => {
        if (isActive) {
            timerRef.current = setInterval(() => {
            setTimeLeft((prevTime) => {
                if (prevTime <= 1) {
                clearInterval(timerRef.current!);
                return 0;
                }
                return prevTime - 1;
            });
            }, 1000);
        }
        return () => {
            if (timerRef.current) {
            clearInterval(timerRef.current);
            }
        };
    }, [isActive]);

    const formatTime = (time: number): string => {
        const minutes = Math.floor(time / 60);
        const seconds = time % 60;
        return `${String(minutes).padStart(2, "0")}:${String(seconds).padStart(2, "0")}`;
    };

    const handleDurationChange = (e: ChangeEvent<HTMLInputElement>): void => {
        setDuration(Number(e.target.value) || "");
    };

     return (
    <div className="flex flex-col items-center justify-center">
        {/* Timer box container */}
        <div className="bg-white dark:bg-gray-800 shadow-lg rounded-lg p-8 w-full max-w-md">
            {/* Title of the countdown timer */}
            <h1 className="text-2xl font-bold mb-4 text-gray-800 dark:text-gray-200 text-center">
            Countdown Timer
            </h1>
            {/* Display the formatted time left */}
            <div className="text-6xl font-bold text-gray-800 dark:text-gray-200 mb-8 text-center">
            {formatTime(timeLeft)}
            </div>
        </div>
        </div>
    );
}

