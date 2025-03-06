import { fetchAllCinemaSchedules } from "@/lib/cinema-data";
import ClientWrapper from "@/components/ClientWrapper";
import {DateProvider} from "@/context/DateContext";
import {Hero} from "@/components/home/Hero";
import {filmEvent, films} from "@/types";
import {Schedule} from "@/components/home/Schedule";

// Revalidation timing
export const revalidate = 3600; // Revalidate every hour

// Server Component - fetches data at build time
export default async function Page() {
    const cinemaNames = ['Kinoteka', 'Muranow', 'Atlantic', 'Iluzjon'];
    const schedules = await fetchAllCinemaSchedules(cinemaNames);

    // Pass data to client component
    return (
        <DateProvider>
            <Hero
                schedules={schedules}
                featuredEvent={filmEvent}
            />
            <div className="flex-col">
                {schedules.map(schedule => (
                    <Schedule
                        key={schedule.id}
                        allCinemaSchedules={schedules}
                        currentCinemaSchedule={schedule}
                    />
                ))}
            </div>
        </DateProvider>
    );
}
