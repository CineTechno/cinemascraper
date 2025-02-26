import type { Film, FilmEvent } from "@/types"
import {FilmCard} from "@/components/films/FilmCard";
import {EventCard} from "@/components/events/EventCard";
import "@/styles/globals.css"

interface HeroGridProps {
    films: Film[]
    featuredEvent: FilmEvent
}

export function HeroGrid({ films, featuredEvent }: HeroGridProps) {
    return (
        <div >
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 max-w-7xl mx-auto gap-6 ">
            {films.slice(0, 3).map((film) => (
                <div key={film.id} className="w-full max-w-sm mx-auto">
                    <FilmCard key={film.id} film={film}/>
                </div>
            ))}
            <div className="w-full max-w-sm mx-auto">
                <EventCard event={featuredEvent}/>
            </div>
            </div>
        </div>
            )
            }