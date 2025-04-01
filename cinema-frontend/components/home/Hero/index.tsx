import {HeroGrid} from "@/components/home/Hero/HeroGrid";
import {HeroBackground} from "@/components/home/Hero/HeroBackground";
import {HeroContent} from "@/components/home/Hero/HeroContent";
import {CinemaSchedule, Film, FilmEvent} from "@/types";


interface HeroProps {
    schedules: CinemaSchedule[]
    featuredEvent: FilmEvent
    selectedDate:Date
}


export function Hero({ schedules, featuredEvent}: HeroProps) {
    return (
        <section className="relative">

            <HeroBackground/>

                    <div className="relative py-2 px-0 md:px-6 lg:px-8 justify-center">
                        <h2 className="text-xl font-bold text-white pb-2 text-center">Najważniejsze repertuary w jednym miejscu</h2>
                        {/*<HeroGrid*/}
                        {/*    films={schedules}*/}
                        {/*/>*/}

            </div>

        </section>
    )
}