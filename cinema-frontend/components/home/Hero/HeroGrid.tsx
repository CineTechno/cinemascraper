import type {CinemaSchedule, Film, FilmEvent, FilmsWithShowtimes} from "@/types"
import "@/styles/globals.css"
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
        <div className="flex min-h-80 gap-4">
            {sortedFilmByRanking.map((film, index) => (
                <div key={index} className="flex-grow">
                    <ScheduleCard filmsWithShowTimes={film} allCinemaSchedules = {films}/>
                </div>
            ))}
            </div>
            )
            }