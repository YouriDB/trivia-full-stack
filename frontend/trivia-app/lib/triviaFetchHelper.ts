
const apiUrl = process.env.API_URL;

export async function getQuestions() {
  const response = await fetch(`${apiUrl}/questions?amount=3`);

  const data = await response.json();

  return Response.json(data)
}