
'use client'
import "../styles/globals.css";
import {Hero} from "@/components/home/Hero";
import {cinemaSchedule, cinemaSchedules, filmEvent, films} from "@/types";
import {DateNavigation} from "@/components/home/CinemaSchedule/date-navigation";
import { format } from "date-fns";
import {CinemaSchedule} from "@/components/home/CinemaSchedule";
import {Card} from "@/components/ui/card";
import {useState} from "react";

export default function Home() {
    const[selectedDate, setSelectedDate] = useState(new Date)
  return (
      <>
    <Hero films={films} featuredEvent={filmEvent} selectedDate={selectedDate}></Hero>
        <div className="flex-col">

          {cinemaSchedules.map(schedule => (
              <CinemaSchedule key={schedule.id}
                              allCinemaSchedules={cinemaSchedules}
                              currentCinemaSchedule={schedule}
                              selectedDate={selectedDate}
                              onDateChange={setSelectedDate}
              ></CinemaSchedule>
          ))
          }
        </div>
      </>
  )
}
