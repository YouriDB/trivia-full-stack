# Trivia App

Dit is een trivia app. De trivia app bestaat uit twee onderdelen, een React framework Next.ts frontend en een Java Spring Boot backend. Beide zijn gehost op de respectieve platformen [Vercel Hosted frontend](https://trivia-full-stack.vercel.app/dashboard) en de [Render hosted backend](https://trivia-full-stack.onrender.com/questions?amount=5). **Weet dat de Render hosted backend tijd nodigt heeft om op te starten als hij niet in gebruik is geweest voor een lange tijd, dit is een limitatie van de gratis subscriptie van Render.**

# Frontend
De frontend is gemaakt met Reactor framework Next.ts. Next.ts is gekozen door de simplistische deployment op Vercel, een gratis hosting platform voor hobby projecten. Om de frontend te runnen heb je het volgende nodig:
 - npm 11+

In `/frontend/trivia-app/` kan je dan het volgende runnen om het framework te gebruiken `npm run dev`.
# Backend
De backend is gemaakt met Java Spring Boot. Het is gedeployed met docker compose en getest door cucumber.io met JUnit5. Daarnaast heeft de backend een MVC architectuur, waar we een controller laag, service laag, client/repository laag hebben. Daarnaast gebruikt de backend een Redis caching service om vraag queries tussen user sessies op te slaan. Om de backend te runnen heb je het volgende nodig:
 - Docker 29+

In `/backend/` kan je dan het volgende runnen om de docker containers te gebruiken `docker compose up --build`.
