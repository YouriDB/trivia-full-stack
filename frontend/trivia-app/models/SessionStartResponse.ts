import { ClientQuestion } from "./ClientQuestion"

export type SessionStartResponse = {
    sessionId: string,
    questions: ClientQuestion[]
}