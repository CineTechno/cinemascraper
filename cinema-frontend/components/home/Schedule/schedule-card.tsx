'use client'
import type {CinemaSchedule, FilmsWithShowtimes} from "@/types"
import { Card, CardContent } from "@/components/ui/card"
import Image from "next/image"
import {useState} from "react";
import Modal from "@/components/home/modal/Modal";
import {Star} from "lucide-react";

interface ScheduleCardProps {
    filmsWithShowTimes: FilmsWithShowtimes
    allCinemaSchedules:CinemaSchedule[]
    className?: string
}

interface matchingCinemaShowtimes {
    cinemaName:string,
    showtimes:FilmsWithShowtimes[]
}



export function ScheduleCard({filmsWithShowTimes, allCinemaSchedules}:ScheduleCardProps ) {
    const [isOpen, setIsOpen] = useState(false)
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    const { film, showtimes } = filmsWithShowTimes;

    const matchingCinemaShowtimes:matchingCinemaShowtimes[] = [];
    allCinemaSchedules.forEach(schedule => {
        const matchingFilms = schedule.filmsWithShowtimes.filter(
            filmsWithShowtimes => filmsWithShowtimes.film.id === film.id
        );

        if (matchingFilms.length > 0) {
            matchingCinemaShowtimes.push({
                cinemaName: schedule.cinemaName,
                showtimes: matchingFilms
            });
        }
    })


    return (
        <>
            <Card
                className="group cursor-pointer transition-transform duration-300 hover:scale-105 w-full h-full overflow-visible"
                onClick={() => setIsOpen(true)}
            >
                <CardContent className="relative w-full h-full">
                    <Image src={film.imgPath || "/placeholder.svg"} fill className="rounded-xl object-cover" alt={film.title} />
                    <div className="absolute inset-0 bg-gradient-to-t from-black/100 to-transparent rounded-lx " />
                    <div className="flex flex-row absolute gap-1 top-4 right-3">
                    <Star className="fill-yellow-600 stroke-1 translate-y-1"/><span className="text-white bg-gradient-to-t from-blue-950 to-blue-800 p-1 rounded-full">{filmsWithShowTimes.film.rating.toFixed(1)}</span>
                    </div>
                    <div className="absolute bottom-10 text-white">
                        <span className="font-bold text-sm bg-gradient-to-t from-blue-950 to-blue-800 p-2 rounded-xl">{film.title}</span>
                        <p className="text-xs md:opacity-0 md:group-hover:opacity-100 md:transition-opacity md:duration-300 line-clamp-4 pr-10 mt-3">
                            {film.description}
                        </p>
                    </div>
                </CardContent>
            </Card>

            <Modal isOpen={isOpen}
                   onOpenChange={setIsOpen}
                   matchingCinemaShowtimes={matchingCinemaShowtimes}
                   film = {film}
            />
        </>
    )
}