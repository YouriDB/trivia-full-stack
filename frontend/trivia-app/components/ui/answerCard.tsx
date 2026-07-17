import { decodeHtml } from "@/lib/decodeHtml";

export default function AnswerCard({
  question,
  grading,
  answers,
  correctAnswer,
  lastQuestion,
  onNextClick
}: {
  question: string;
  grading: boolean;
  answers: string[];
  correctAnswer: string;
  lastQuestion: boolean;
  onNextClick?: () => void;
}) {
  return (
    <div className={"mx-auto max-w-2xl rounded-2xl bg-white p-8 shadow-lg border-10 " + (grading ? "border-green-500" : "border-red-500")}>
      <h2 className="mb-6 text-2xl font-bold text-slate-800">
        {decodeHtml(question)}
      </h2>

      <div className="flex flex-col gap-3">
          {answers.slice(0, 4).map((answer, index) => (
            <button
              key={index}
              className={"rounded-xl border-2 bg-gray-200 px-5 py-4 text-left font-medium text-slate-700 transition disabled " + (answer == correctAnswer ? "border-green-500" : "border-red-500")}
            >
              {decodeHtml(answer)}
            </button>
          ))}
          <button
            key="4"
            onClick={() => onNextClick?.()}
            className="rounded-xl border border-slate-200 bg-white px-5 py-4 text-left font-medium text-slate-700 transition hover:border-indigo-500 hover:bg-indigo-50 cursor-pointer"
          >
            {(!lastQuestion) ? "Next" : "End Quiz"}
          </button>
      </div>
    </div>
  );
}