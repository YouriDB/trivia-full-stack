import Header from "@/components/layout/header";
import Countdown from "@/components/ui/countdown";
import QuizSettings from "@/components/ui/quizSettings";
import StartButton from "@/components/ui/startButton";

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
        <div>
            <Header />
            <div className="flex h-screen items-center justify-center">
                <div className="flex flex-col">
                    <StartButton />
                    <QuizSettings />
                </div>
            </div>
            {children}
        </div>
      
  );
}