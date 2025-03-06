'use client'
import type {CinemaSchedule, FilmsWithShowtimes} from "@/types"
import { Card, CardContent } from "@/components/ui/card"
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog"
import Image from "next/image"
import {useState} from "react";
import {format} from "date-fns";

interface ScheduleCardProps {
    filmsWithShowTimes: FilmsWithShowtimes
    allCinemaSchedules:CinemaSchedule[]
    className?: string
}

export function ScheduleCard({filmsWithShowTimes, allCinemaSchedules, className}:ScheduleCardProps ) {
    const [isOpen, setIsOpen] = useState(false)
    const { film, showtimes } = filmsWithShowTimes;



    return (
        <>
            <Card
                className="group cursor-pointer transition-transform duration-300 hover:scale-105 w-full h-full overflow-hidden"
                onClick={() => setIsOpen(true)}
            >
                <CardContent className="relative w-full h-full">
                    <Image src={film.imgPath || "/placeholder.svg"} fill className="rounded-xl object-cover" alt={film.title} />
                    <div className="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent rounded-lx " />
                    <div className="absolute  h-20 top-5 p-0 text-xs text-white mask-gradient">
                        {showtimes.map((showtime,index) =>{
                            const newDate = new Date(showtime)
                            const formattedDate = format(newDate,"h:mm a")
                            return (
                                <div key={index} className="text-primary-foreground text-xs px-0 py-0 rounded-md mb-1">

                                    {" | " + formattedDate}
                                </div>
                            )
                        })}

                    </div>
                    <div className="absolute bottom-10 text-white">
                        <h3 className="font-bold text-sm mb-1">{film.title}</h3>
                        <p className="text-xs opacity-0 group-hover:opacity-100 transition-opacity duration-300 line-clamp-4 pr-10">
                            {film.description}
                        </p>
                    </div>
                </CardContent>
            </Card>

            <Dialog open={isOpen} onOpenChange={setIsOpen}>
                <DialogContent className="max-w-3xl">
                    <DialogHeader>
                        <DialogTitle>{film.title}</DialogTitle>
                    </DialogHeader>
                    <div className="grid gap-4 py-4">
                        <div className="aspect-video relative">
                            <Image src={film.imgPath || "/placeholder.svg"} alt={film.title} fill className="object-cover rounded-lg" />
                        </div>
                        <div className="grid gap-2">
                            <p className="text-lg">{film.description}</p>
                            <div className="text-sm text-muted-foreground">
                                <p>Director: {film.director}</p>
                                <p>Year: {film.year}</p>
                            </div>
                            <div className="mt-4">
                                <h4 className="font-semibold mb-2">Showtimes:</h4>
                                <div className="grid gap-2">
                                    {showtimes.map((showtime, index) => (
                                        <div key={index} className="flex justify-between text-sm">
                                            <span>{showtime}</span>
                                            <span>{showtime}</span>
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

