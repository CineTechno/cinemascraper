import {useState} from "react";
import {films} from "@/types/index.ts"
import {Card, CardBody, CardHeader} from "@heroui/card";
import {Divider} from "@heroui/divider";



const Carousel = ({muranowFilms,date}) => {
    console.log(date)
    const filteredMovies:string[] = Object.entries(muranowFilms)
        .filter(([dates, ]) =>dates===date.format("DD-MM-YYYY"))
        .flatMap(([,films]) => films)

    const [currentIndex, setCurrentIndex] = useState(0);

    const prevSlide = () => {
        setCurrentIndex((prev) => (prev===0?0:prev-1));
    };

    const nextSlide = () => {
        setCurrentIndex((prev) => (prev >filteredMovies.length/1.2?prev:prev +1));
    };

    return (
        <div className="relative max-w-4xl mx-auto overflow-hidden"  >
            {/* Slides container */}
            <div
                className="flex transition-transform duration-500 min-w-full mx-10"
                style={{
                    transform: `translateX(-${currentIndex * 100/filteredMovies.length}%)`,
                    width: `${filteredMovies.length * 20}%`, // Dynamically calculate width
                }}
            >
                {filteredMovies.map((movie, index) => (
                    <Card
                        key={index}


                    >
                        <CardHeader>
                        {movie}
                        </CardHeader>
                        <Divider></Divider>
                        <CardBody>
                            Opis filmu
                        </CardBody>
                    </Card>
                ))
                }

            </div>

            {/* Navigation buttons */}
            <button
                onClick={prevSlide}
                className="absolute left-(-1) top-1/2 transform -translate-y-1/2 bg-gray-700 text-white p-2 rounded-full"
            >
                &#9664;
            </button>
            <button
                onClick={nextSlide}
                className="absolute right-0 top-1/2 transform -translate-y-1/2 bg-gray-700 text-white p-2 rounded-full"
            >
                &#9654;
            </button>

            {/* Dots for navigation */}

        </div>
    );
};

export default Carousel;