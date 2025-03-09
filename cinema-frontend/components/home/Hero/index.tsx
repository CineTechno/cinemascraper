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
            <div className="flex gap-5">
                <div className="flex flex-col flex-1">
                    <div className="relative py-8 px-0 md:px-6 lg:px-8 flex-col">
                        <h2 className="text-4xl sm:text-3xl font-bold text-white pb-6">Filmy tygodnia</h2>
                        <HeroGrid
                            films={schedules}
                        />
                    </div>
                </div>
                <div className="flex flex-col flex-1 justify-left">
                    <div>
                        <p>W-warto Zobaczyc</p>
                    </div>

                    <div>
                        <span className="text-lg text-white">Tylko dobre kina w Warszawie</span>
                    </div>
                </div>
            </div>

        </section>
    )
}