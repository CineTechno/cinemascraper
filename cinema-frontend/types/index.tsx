export interface Film {
    id?: number,
    title: string,
    description: string,
    director: string,
    year: string,
    imgPath: string,
    rating: number
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


export const films: Film[] = [
    {
        id: 1,
        cinema: "Muranow",
        title: "Green Border",
        dateShowTime: ["2024-02-21 19:00", "2024-02-21 21:30"],
        description: "A shocking story of refugees trying to cross the border between Belarus and Poland, while a Polish family decides to help those in need despite the consequences.",
        director: "Agnieszka Holland",
        year: "2023",
        imgPath: "/images/Amator.jpg"
    },
    {
        id: 2,
        cinema: "Kinoteka",
        title: "Perfect Days",
        dateShowTime: ["2024-02-22 18:00", "2024-02-23 20:15"],
        description: "Hirayama seems utterly content with his simple life as a cleaner of toilets in Tokyo. Outside of his very structured everyday routine, he enjoys his passion for music and books.",
        director: "Wim Wenders",
        year: "2023",
        imgPath: "/images/Anora.jpg"
    },
    {
        id: 3,
        cinema: "Atlantic",
        title: "The Zone of Interest",
        dateShowTime: ["2024-02-21 17:30", "2024-02-22 19:45"],
        description: "The commandant of Auschwitz, Rudolf Höss, and his wife Hedwig strive to build a dream life for their family in a house and garden next to the camp.",
        director: "Jonathan Glazer",
        year: "2023",
        imgPath: "/images/Babygirl.jpg"
    },
    {
        id: 4,
        cinema: "Iluzjon",
        title: "Poor Things",
        dateShowTime: ["2024-02-23 20:00", "2024-02-24 18:30"],
        description: "The incredible tale about the fantastical evolution of Bella Baxter, a young woman brought back to life by the brilliant and unorthodox scientist Dr. Godwin Baxter.",
        director: "Yorgos Lanthimos",
        year: "2023",
        imgPath: "/images/Amator.jpg"
    }
]

export const filmEvent: FilmEvent = {
    title: "Film History Lecture: Polish New Wave Cinema",
    dateAndTime: "2024-02-25 18:00",
    organiser: "CKF Wajda",
    description: "Join us for an engaging lecture exploring the revolutionary period of Polish cinema known as the Polish New Wave. The discussion will cover key directors including Andrzej Wajda, Roman Polański, and Jerzy Skolimowski, followed by a screening of 'Innocent Sorcerers' (1960).",
    link: "/images/Amator.jpg"
}

export const cinemaSchedules:CinemaSchedule[] =[{

    id: 1,
    cinemaName: "Kinoteka",
    filmsWithShowtimes: [
        {
            film: {
                id: 1,
                title: "Perfect Days",
                description: "Hirayama seems utterly content with his simple life as a cleaner of toilets in Tokyo. Outside of his very structured everyday routine, he enjoys his passion for music and books.",
                director: "Wim Wenders",
                year: "2023",
                imgPath: "/images/Climax.jpg",
                rating: 8.4
            },
            showtimes: [
                "2024-02-22T12:30:00",
                "2024-02-22T15:45:00",
                "2024-02-22T18:15:00",
                "2024-02-22T20:45:00"
            ]
        },
        {
            film: {
                id: 2,
                title: "The Zone of Interest",
                description: "The commandant of Auschwitz, Rudolf Höss, and his wife Hedwig strive to build a dream life for their family in a house and garden next to the camp.",
                director: "Jonathan Glazer",
                year: "2023",
                imgPath: "/images/Dalej-jazda.jpg",
                rating: 8.2
            },
            showtimes: [
                "2024-02-22T13:00:00",
                "2024-02-22T16:30:00",
                "2024-02-22T19:00:00"
            ]
        },
        {
            film: {
                id: 3,
                title: "Poor Things",
                description: "The incredible tale about the fantastical evolution of Bella Baxter, a young woman brought back to life by the brilliant and unorthodox scientist Dr. Godwin Baxter.",
                director: "Yorgos Lanthimos",
                year: "2023",
                imgPath: "/images/Dalej-jazda.jpg",
                rating: 8.6
            },
            showtimes: [
                "2024-02-22T14:15:00",
                "2024-02-22T17:45:00",
                "2024-02-22T21:00:00"
            ]
        }

    ]

},
    {
        id: 2,
        cinemaName: "Muranow",
        filmsWithShowtimes: [
            {
                film: {
                    id: 1,
                    title: "Moonlit Dreams",
                    description: "A poetic journey exploring love, art, and mystery under the gentle glow of the night.",
                    director: "Lina Rossi",
                    year: "2023",
                    imgPath: "/images/Dzikość-serca.jpg",
                    rating: 7.8
                },
                showtimes: [
                    "2024-03-05T11:00:00",
                    "2024-03-05T13:30:00",
                    "2024-03-05T16:00:00",
                    "2024-03-05T18:30:00"
                ]
            },
            {
                film: {
                    id: 2,
                    title: "Echoes of Tomorrow",
                    description: "A sci-fi adventure that challenges time and fate through unexpected twists.",
                    director: "Samuel Lee",
                    year: "2024",
                    imgPath: "/images/Dzikość-serca.jpg",
                    rating: 8.1
                },
                showtimes: [
                    "2024-03-05T12:15:00",
                    "2024-03-05T15:00:00",
                    "2024-03-05T17:45:00"
                ]
            },
            {
                film: {
                    id: 3,
                    title: "Crimson Shadows",
                    description: "A dark, suspenseful drama unraveling mysteries hidden within a troubled city.",
                    director: "Eva Martinez",
                    year: "2023",
                    imgPath: "/images/Dzikość-serca.jpg",
                    rating: 7.5
                },
                showtimes: [
                    "2024-03-05T14:00:00",
                    "2024-03-05T17:00:00",
                    "2024-03-05T20:00:00"
                ]
            },
            {
                film: {
                    id: 3,
                    title: "Crimson Shadows",
                    description: "A dark, suspenseful drama unraveling mysteries hidden within a troubled city.",
                    director: "Eva Martinez",
                    year: "2023",
                    imgPath: "/images/Dzikość-serca.jpg",
                    rating: 7.5
                },
                showtimes: [
                    "2024-03-05T14:00:00",
                    "2024-03-05T17:00:00",
                    "2024-03-05T20:00:00"
                ]
            },
            {
                film: {
                    id: 3,
                    title: "Crimson Shadows",
                    description: "A dark, suspenseful drama unraveling mysteries hidden within a troubled city.",
                    director: "Eva Martinez",
                    year: "2023",
                    imgPath: "/images/Dzikość-serca.jpg",
                    rating: 7.5
                },
                showtimes: [
                    "2024-03-05T14:00:00",
                    "2024-03-05T17:00:00",
                    "2024-03-05T20:00:00"
                ]
            },
            {
                film: {
                    id: 3,
                    title: "Crimson Shadows",
                    description: "A dark, suspenseful drama unraveling mysteries hidden within a troubled city.",
                    director: "Eva Martinez",
                    year: "2023",
                    imgPath: "/images/Dzikość-serca.jpg",
                    rating: 7.5
                },
                showtimes: [
                    "2024-03-05T14:00:00",
                    "2024-03-05T17:00:00",
                    "2024-03-05T20:00:00"
                ]
            }


        ]
    }]