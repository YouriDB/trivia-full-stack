import { Question } from "./Question"

export type SessionEndResponse = {
    questions: Question[]
    grading: Map<string, boolean>
}