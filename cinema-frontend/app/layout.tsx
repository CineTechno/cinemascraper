
import type { Metadata } from "next"
import "../styles/globals.css";

export const metadata: Metadata = {
    title: "Cinema App",
    description: "Handpicked films from the best cinemas in Warsaw",
}

export default function RootLayout({children}: {
    children: React.ReactNode
}) {
    return (
        <html lang="en">
        <body className= "min-h-screen bg-background flex flex-col">
        <header className="border-b bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/60">
            <div className="max-w-7xl mx-auto h-16 px-4 md:px-6 lg:px-8 flex items-center">
                <span className=" inline-block text-3xl text-blue-800 font-bold tracking-tight">W-war</span>
                <span className="inline-block text-3xl font-bold tracking-tight">to zobaczyć</span>
                <span className="text-lg font-medium tracking-tight ml-auto">
        Tylko dobre kina w Warszawie
    </span>
            </div>
        </header>

        <main className="flex-1">
            {children}
        </main>

        <footer className="border-t mt-auto">
            <div className="container mx-auto p-4 text-center">
                <p className="text-sm text-muted-foreground">© 2024 W-warto zobaczyć</p>
            </div>
        </footer>
        </body>
        </html>
    )
}