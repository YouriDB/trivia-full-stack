import Header from "@/components/layout/header";

export default function ResultsLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
        <div>
            <Header />
            <div className="flex h-screen items-center justify-center">
                <div className="flex flex-col">
                    {children}
                </div>
            </div>
        </div>
      
  );
}