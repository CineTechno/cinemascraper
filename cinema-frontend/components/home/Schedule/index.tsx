'use client'
import {useDateContext} from "@/context/DateContext";
import {CinemaSchedule, cinemaSchedules, Film} from "@/types";
import {useState} from "react";
import {format} from "date-fns"
import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card";
import {Carousel, CarouselContent, CarouselItem} from "@/components/ui/carousel";
import {ScheduleCard} from "@/components/home/Schedule/schedule-card";
import {DateNavigation} from "@/components/home/Schedule/date-navigation";


interface CinemaScheduleProps {
    allCinemaSchedules: CinemaSchedule[];
    currentCinemaSchedule:CinemaSchedule;
}


export function Schedule({ allCinemaSchedules, currentCinemaSchedule}: CinemaScheduleProps) {

    const {id,cinemaName,filmsWithShowtimes} = currentCinemaSchedule;

    const{selectedDate,setSelectedDate} = useDateContext()
    const selectedDateToString = format(selectedDate, "yyyy-MM-dd")

    const filteredFilmsWithShowtimes = filmsWithShowtimes.map(showtime =>
        {
            const selectedShowtimes = showtime.showtimes.filter(showtime =>
            showtime.startsWith(selectedDateToString)
        )
            const selectedFilms:Film = showtime.film
            return {
                film:selectedFilms,
                showtimes:selectedShowtimes
            }
       }).filter(item => item.showtimes.length>0).sort((a,b)=>b.film.rating - a.film.rating)

    console.log(filteredFilmsWithShowtimes)

    return(
        <div className="p-7 pb-0">
            <CardHeader className="flex flex-row gap-3">
                <CardTitle className= "translate-y-[1px] px-0" >{cinemaName}</CardTitle>
                <DateNavigation></DateNavigation>

            </CardHeader>
            <CardContent>
                <Carousel>

                    <CarouselContent>
                        {filteredFilmsWithShowtimes.map((filmsWithShowtimes) => (

                            <CarouselItem key={filmsWithShowtimes.film.title} className= "min-h-80 min-w-60" >
                                <ScheduleCard key={id}
                                              filmsWithShowTimes={filmsWithShowtimes}
                                              allCinemaSchedules={allCinemaSchedules}>

                                </ScheduleCard>

                            </CarouselItem>
                        ))}

                    </CarouselContent>
                </Carousel>
            </CardContent>
        </div>
    )
}