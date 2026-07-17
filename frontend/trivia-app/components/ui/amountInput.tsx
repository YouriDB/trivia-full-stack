import { useState } from "react";

export default function AmountInput({
    quizSettingsChanged
}: {
    quizSettingsChanged: (label: string, value: string) => void;
}) {
    const [outOfRange, setOutOfRange] = useState<boolean>(false);

    return (
    <>
        <div className="flex flex-col gap-2">
            <label className="font-medium text-slate-500">Question amount</label>
            <div className="flex flex-row gap-2 items-center"> 
                <input 
                    className="rounded-xl border border-slate-300 px-4 py-3 shadow-sm"
                    onChange={(e) => {
                        if (parseInt(e.target.value) < 1 || parseInt(e.target.value) > 50) {
                            setOutOfRange(true);
                        } else {
                            quizSettingsChanged?.("Amount", e.target.value); 
                        }}} 
                    placeholder="10"
                    name="AmountInput"
                    type="number"
                />
                {outOfRange &&
                    <p className="text-red-300">The amount range must be between 1 to 50.</p>
                }
                
            </div>
        </div>
    </>
    );
}