import { decodeHtml } from "@/lib/decodeHtml";

export default function QuestionCard({
  question,
  answers,
  onAnswerSelect,
}: {
  question: string;
  answers: string[];
  onAnswerSelect?: (question: string, answer: string) => void;
}) {
  return (
    <div className="mx-auto max-w-2xl rounded-2xl bg-white p-8 shadow-lg">
      <h2 className="mb-6 text-2xl font-bold text-slate-800">
        {decodeHtml(question)}
      </h2>

      <div className="flex flex-col gap-3">
        {answers.slice(0, 4).map((answer, index) => (
          <button
            key={index}
            onClick={() => onAnswerSelect?.(question, answer)}
            className="rounded-xl border border-slate-200 bg-white px-5 py-4 text-left font-medium text-slate-700 transition hover:border-indigo-500 hover:bg-indigo-50"
          >
            {answer}
          </button>
        ))}
      </div>
    </div>
  );
}