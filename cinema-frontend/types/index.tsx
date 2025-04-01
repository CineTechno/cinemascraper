export interface Film {
    id?: number,
    title: string,
    description: string,
    director: string,
    year: string,
    imgPath: string,
    rating: number
    link: string
}

export interface FilmEvent {
    id?: number;
    title: string;
    dateAndTime: string;
    organiser: string;
    description: string;
    link: string;
}

export interface CinemaSchedule {
    id:number,
    cinemaName: string,
    filmsWithShowtimes:FilmsWithShowtimes[]
}

export interface FilmsWithShowtimes{
    film:Film,
    showtimes:string[]
}


