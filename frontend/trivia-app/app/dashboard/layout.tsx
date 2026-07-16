import Title from "@/components/layout/title";

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
        <div>
            <Title />
            {children}
        </div>
      
  );
}