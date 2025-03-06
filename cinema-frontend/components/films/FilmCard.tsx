"use client"
import { useState } from "react"
import type {Film, CinemaSchedule, FilmsWithShowtimes} from "@/types"
import { Card, CardContent } from "@/components/ui/card"
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog"
import Image from "next/image"
import {mathAbs} from "embla-carousel/components/utils";
import {DialogFulInfo} from "@/components/home/dialog/DialogFulInfo";




export function FilmCard({film, allSchedules}: { film: Film, allSchedules: CinemaSchedule[] }) {
    const [isOpen, setIsOpen] = useState(false)

    const matchingCinemaShowtimes = [];
    allSchedules.forEach(schedule => {
        const matchingFilms = schedule.filmsWithShowtimes.filter(
            filmsWithShowtimes => filmsWithShowtimes.film.id === film.id
        );

        // Only add cinemas that have this film
        if (matchingFilms.length > 0) {
            matchingCinemaShowtimes.push({
                cinemaName: schedule.cinemaName,
                showtimes: matchingFilms
            });
        }
    });

    return (
        <>
            <Card
                className="group cursor-pointer transition-transform duration-300 hover:scale-105 overflow-hidden }"
                onClick={() => setIsOpen(true)}
            >
                <CardContent className="relative aspect-[2/3] w-full ">
                        <Image src={film.imgPath || "/placeholder.svg"}
                               alt={film.title}
                               fill
                               className="object-cover "/>
                    <div className="absolute top-0 p-4 text-white opacity-0 group-hover:opacity-100 transition-opacity duration-300">
                        {film.director} <br/>
                        {film.year}
                    </div>
                        <div className="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent"/>
                        <div className="absolute bottom-0 p-4 text-white transition-transform translate-y-24 group-hover:-translate-y-12 duration-300">
                            <h3 className="font-bold text-xl mb-2">{film.title}</h3>
                            <p className="  max-h-32 overflow-hidden mask-gradient opacity-0 group-hover:opacity-100 transition-opacity duration-300">{film.description}</p>
                        </div>
                </CardContent>
            </Card>
            <Dialog open={isOpen} onOpenChange={setIsOpen}>
                <DialogContent>
                    <DialogHeader>
                        <DialogTitle>{film.title}</DialogTitle>
                    </DialogHeader>
                    <div className="grid gap-4 py-4">
                        <div className="aspect-video relative">
                            <Image src={film.imgPath || "/placeholder.svg"} alt={film.title} fill className="object-cover rounded-lg" />
                        </div>
                        <div className="grid gap-2">
                            <p className="text-sm line-clamp-4">{film.description}</p>
                            <div className="text-sm text-muted-foreground">
                                <p>Director: {film.director}</p>
                                <p>Year: {film.year}</p>
                            </div>
                            <div className="mt-4">
                                <h4 className="font-semibold mb-2">Showtimes:</h4>
                                <div className="grid gap-2">
                                    {matchingCinemaShowtimes.map((showtime, index) => (
                                        <div key={index} className="flex justify-between text-sm">
                                            <span>{showtime.cinemaName}</span>
                                            <span>{showtime.dateShowTime}</span>
                                        </div>
                                    ))}
                                </div>
                            </div>
                        </div>
                    </div>
                </DialogContent>
            </Dialog>

        </>
    )
}

