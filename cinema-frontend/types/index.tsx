export interface Film{
    id?:number;
    cinema:string;
    title:string;
    dateShowTime:string[];
    description:string;
    director:string;
    year:string;
    imgPath:string;
}

export interface Event{
    id?:number;
    title:string;
    dateAndTime:string;
    organiser:string;
    description:string;
    link:string;
}