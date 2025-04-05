
import {HeroBackground} from "@/components/home/Hero/HeroBackground";






export function Hero() {
    return (
        <section className="relative">

            <HeroBackground/>

                    <div className="relative py-2 px-0 md:px-6 lg:px-8 justify-center">
                        <h2 className="mx-4 md:text-2xl sm:text-sm font-bold text-white pb-2 text-center">Najważniejsze repertuary w jednym miejscu</h2>
                        {/*<HeroGrid*/}
                        {/*    films={schedules}*/}
                        {/*/>*/}

            </div>

        </section>
    )
}