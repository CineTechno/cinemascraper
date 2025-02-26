import {CinemaSchedule, cinemaSchedules} from "@/types";
import {useState} from "react";
import {format} from "date-fns"
import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card";
import {Carousel, CarouselContent, CarouselItem} from "@/components/ui/carousel";
import {ScheduleCard} from "@/components/home/CinemaSchedule/schedule-card";
import {DateNavigation} from "@/components/home/CinemaSchedule/date-navigation";


interface CinemaScheduleProps {
    cinemaSchedule: CinemaSchedule;
    selectedDate: Date;
}

export function CinemaSchedule({ cinemaSchedule:{id, cinemaName, filmsWithShowtimes}, selectedDate }: CinemaScheduleProps) {


    return(
        <div className="p-10">
            <CardHeader className="flex flex-row gap-3">
                <CardTitle className= "translate-y-[1px] px-0" >{cinemaName}</CardTitle>
                <DateNavigation date={new Date}></DateNavigation>

            </CardHeader>
            <CardContent>
                <Carousel>
                    <CarouselContent>
                        {filmsWithShowtimes.map((filmsWithShowtimes) => (
                            <CarouselItem key={filmsWithShowtimes.film.title} className= "min-h-80 min-w-60" >
                                <ScheduleCard key={id} filmsWithShowTimes={filmsWithShowtimes}></ScheduleCard>

                            </CarouselItem>
                        ))}

                    </CarouselContent>
                </Carousel>
            </CardContent>
        </div>
    )
}