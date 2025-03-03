
import "../styles/globals.css";
import {Hero} from "@/components/home/Hero";
import {CinemaSchedule, cinemaSchedules, filmEvent, films} from "@/types";
import {CinemaSchedule} from "@/components/home/CinemaSchedule";
import {useState} from "react";
import {GetStaticProps} from "next";
import {fetchAllCinemaSchedules} from "@/lib/cinema-data";


export const getStaticProps: GetStaticProps = async () => {

    const cinemaNames = ['Kinoteka', 'Muranow', 'Atlantic', 'Iluzjon'];

    const schedules = await fetchAllCinemaSchedules(cinemaNames);

    return {
        props: {
            schedules:schedules,
        },
        revalidate: 3600,
    };
};


export default function Home({schedules}:{schedules:CinemaSchedule[]}) {
    const[selectedDate, setSelectedDate] = useState(new Date)
  return (
      <>
    <Hero films={films}
          featuredEvent={filmEvent}
          selectedDate={selectedDate}>

    </Hero>
        <div className="flex-col">

          {schedules.map(schedule => (
              <CinemaSchedule key={schedule.id}
                              allCinemaSchedules={schedules}
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
