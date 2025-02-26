
import "../styles/globals.css";
import {Hero} from "@/components/home/Hero";
import {cinemaSchedule, cinemaSchedules, filmEvent, films} from "@/types";
import {DateNavigation} from "@/components/home/CinemaSchedule/date-navigation";
import { format } from "date-fns";
import {CinemaSchedule} from "@/components/home/CinemaSchedule";
import {Card} from "@/components/ui/card";

export default function Home() {
  return (
      <>
    <Hero films={films} featuredEvent={filmEvent}></Hero>
        <div className="flex-col">

          {cinemaSchedules.map(schedule => (
              <CinemaSchedule key={schedule.id} cinemaSchedule={schedule} selectedDate={new Date()}></CinemaSchedule>
          ))
          }
        </div>
      </>
  )
}
