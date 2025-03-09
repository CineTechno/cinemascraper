import type {CinemaSchedule, Film, FilmEvent, FilmsWithShowtimes} from "@/types"
import {FilmCard} from "@/components/films/FilmCard";
import {EventCard} from "@/components/events/EventCard";
import "@/styles/globals.css"
import {Dialog} from "@/components/ui/dialog";
import {DialogFulInfo} from "@/components/home/modal/DialogFulInfo";
import {Schedule} from "@/components/home/Schedule";
import {ScheduleCard} from "@/components/home/Schedule/schedule-card";

interface HeroGridProps {
    films: CinemaSchedule[]

}


export function HeroGrid({ films }: HeroGridProps) {


    const allFilms:FilmsWithShowtimes[] = []
    films.forEach((schedule: CinemaSchedule) => {
        schedule.filmsWithShowtimes.forEach(item => {
            allFilms.push(item);
        });
    });

    const sortedFilmByRanking = allFilms.sort((a, b)=> b.film.rating - a.film.rating).slice(0,2)

    return (
        <div className="flex flex-grow min-h-80 min-w-60 gap-4">
            {sortedFilmByRanking.map((film, index) => (
                <div key={index} className=" w-full">
                    <ScheduleCard filmsWithShowTimes={film} allCinemaSchedules = {films}/>
                </div>
            ))}
            </div>
            )
            }