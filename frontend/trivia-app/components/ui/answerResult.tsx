import { decodeHtml } from "@/lib/decodeHtml";

export default function AnswerResult({
  question,
  correct,
  onNextClick
}: {
  question: string;
  correct: Boolean;
  onNextClick?: () => void;
}) {
  return (
    <div className={"mx-auto max-w-2xl rounded-2xl bg-white p-8 shadow-lg border-10 " + (correct ? "border-green-500" : "border-red-500")}>
      <h2 className="mb-6 text-2xl font-bold text-slate-800">
        {decodeHtml(question)}
      </h2>

      <div className="flex flex-col gap-3">
          <button
            onClick={() => onNextClick?.()}
            className="rounded-xl border border-slate-200 bg-white px-5 py-4 text-left font-medium text-slate-700 transition hover:border-indigo-500 hover:bg-indigo-50"
          >
            Next
          </button>
      </div>
    </div>
  );
}