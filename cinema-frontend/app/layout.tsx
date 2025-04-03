
import type { Metadata } from "next"
import "../styles/globals.css";
import Image from "next/image";

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
        <header>
            <div className=" lg:mx-2 sm:mx-6 h-16 px-4 md:px-6 lg:px-8 flex items-center justify-center">
                <div className="relative right-3 bottom-1">
                    <Image src={"/Logo.png"} alt={"Logo"} width={30} height={30} className="fill-blue-800"/>
                </div>
                <span className=" inline-block text-4xl text-blue-800 font-bold tracking-tight">W-wa</span>
                <span className="inline-block text-4xl font-bold tracking-tight">rto zobaczyć</span>
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