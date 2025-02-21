import "@/styles/globals.css"
import type { Metadata } from "next"
import { Inter } from "next/font/google"

const inter = Inter({ subsets: ["latin"] })

export const metadata: Metadata = {
    title: "Cinema App",
    description: "Handpicked films from the best cinemas in Warsaw",
}

export default function RootLayout({children}: {
    children: React.ReactNode
}) {
    return (
        <html lang="en">
        <body className={`${inter.className} min-h-screen bg-background`}>
        <header className="border-b bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/60">
            <div className="container flex h-16 items-center px-4">
                <h1 className="text-2xl font-bold tracking-tight">W-Warto Zobaczyć</h1>
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