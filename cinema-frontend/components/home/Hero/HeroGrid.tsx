import type {CinemaSchedule, Film, FilmEvent} from "@/types"
import {FilmCard} from "@/components/films/FilmCard";
import {EventCard} from "@/components/events/EventCard";
import "@/styles/globals.css"
import {Dialog} from "@/components/ui/dialog";
import {DialogFulInfo} from "@/components/home/dialog/DialogFulInfo";

interface HeroGridProps {
    films: CinemaSchedule[]
    featuredEvent: FilmEvent
}


export function HeroGrid({ films, featuredEvent }: HeroGridProps) {

    const allFilms:Film[] = []
    films.forEach((schedule: CinemaSchedule) => {
        schedule.filmsWithShowtimes.forEach(item => {
            allFilms.push(item.film);
        });
    });

    const sortedFilmByRanking = allFilms.sort((a,b)=> b.rating - a.rating).slice(0,2)

    return (
        <div >
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 max-w-7xl mx-auto gap-6 ">
            {sortedFilmByRanking.map((film) => (
                <div key={film.id} className="w-full max-w-sm mx-auto">
                    <FilmCard key={film.id} film={film} allSchedules = {films}/>
                </div>
            ))}
            <div className="w-full max-w-sm mx-auto">
                <EventCard event={featuredEvent}/>
            </div>
            </div>

        </div>
            )
            }