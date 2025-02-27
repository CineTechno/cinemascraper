import {HeroGrid} from "@/components/home/Hero/HeroGrid";
import {HeroBackground} from "@/components/home/Hero/HeroBackground";
import {HeroContent} from "@/components/home/Hero/HeroContent";
import {Film, FilmEvent} from "@/types";


interface HeroProps {
    films: Film[]
    featuredEvent: FilmEvent
    selectedDate:Date
}


export function Hero({ films, featuredEvent, selectedDate }: HeroProps) {
    return (
        <section className="relative">

            <HeroBackground />
            <div className="relative z-10 py-16 px-4 md:px-6 lg:px-8">
                <HeroContent
                    title="Polecane Filmy i Wydarzenia"
                />
                <HeroGrid
                    films={films}
                    featuredEvent={featuredEvent}
                />
            </div>

        </section>
    )
}