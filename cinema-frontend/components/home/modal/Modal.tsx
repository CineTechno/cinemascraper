'use client'
import { createPortal } from "react-dom";
import {Film, FilmsWithShowtimes} from "@/types";
import Image from "next/image";
import React from "react";
import {format} from "date-fns";
import {useDateContext} from "@/context/DateContext";

interface ModalProps {
    isOpen:boolean,
    onOpenChange: (open:boolean) => void,
    matchingCinemaShowtimes:matchingCinemaSchedules[],
    film:Film
}

interface matchingCinemaSchedules {
    cinemaName:string,
    showtimes:FilmsWithShowtimes[]
}

const Modal = ({  isOpen, onOpenChange, matchingCinemaShowtimes, film }:ModalProps) => {
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    const{selectedDate,setSelectedDate} = useDateContext()


    document.body.style.overflow = isOpen ? "hidden" : "auto";

    if (!isOpen) return null;

    const groupedShowtimesByDate = matchingCinemaShowtimes.map(filmShowtimes => {

        const groupedShowtimes = new Map()

        filmShowtimes.showtimes.forEach(showtimes=> {
            showtimes.showtimes.filter(time => time.startsWith(format(selectedDate, "yyyy-MM-dd"))).forEach(time => {
                const dateObj = new Date(time)
                const date = format(dateObj, "dd-MM")
                const hour = format (dateObj, "HH:mm")
                if(!groupedShowtimes.has(date)){
                    groupedShowtimes.set(date, [])
                }
                groupedShowtimes.get(date).push(hour)
            })

        })

        const showtimeArray = Array.from(groupedShowtimes, ([date,hours]) => {
            return{
                date,
                hours
            }
        })

        return (
            {cinema:filmShowtimes.cinemaName,
            film: filmShowtimes.showtimes[0].film,
            showtimes:showtimeArray}
        )
    })






    return createPortal(
        <div
            className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 p-4 z-50"
            onClick={() => onOpenChange(false)}
        >
            <div
                className="bg-gradient-to-t from-blue-950 to-blue-800 md:max-w-[40vw] md:max-h-[80vh] sm:max-w-[50vw] sm:max-h-[90vh] pt-2 px-4 pb-6 rounded-2xl shadow-lg text-white overflow-hidden"
                onClick={(e) => e.stopPropagation()}
            >
                {/* Header */}
                <div className="flex justify-between">
                    <div className="text-lg font-bold">
                       {film.title}
                    </div>
                    <button onClick={() => onOpenChange(false)} className="text-xl font-medium">
                        ×
                    </button>
                </div>

                {/* Gradient Line */}
                <div className="h-px w-full bg-gradient-to-r from-blue-950 via-purple-500 to-purple-800 my-2" />
                {/*entire section*/}
                <div className=" flex flex-col w-full gap-2">
                    {/* Content Section */}
                    <div className="flex gap-4 ">
                        {/* Film Image */}
                        <div className="relative w-1/2  overflow-hidden rounded-xl h-60">
                            <Image src={film.imgPath} alt={film.title} fill className="object-cover" />
                        </div>

                        {/* Film Details and Showtimes */}
                        <div className="flex flex-col gap-2 align-middle basis-30">
                            {/* Film Info */}
                            <ul className="text-xs">
                                <li>
                                    <span className="font-medium">Reżyser:</span> {film.director}
                                </li>
                                <li>
                                    <span className="font-medium">Rok:</span> {film.year}
                                </li>
                                <li>
                                    <span className="font-medium">Ocena:</span> {film.rating}
                                </li>
                            </ul>
                            <div className="m-0"><span className="text-xs font-medium">Data: </span><span className="text-xs"> {format(selectedDate,"dd-MM")}</span></div>

                            {/* Cinema and Showtimes */}
                            <ul className="text-xs">
                                {groupedShowtimesByDate.map((cinema, index) => (
                                    <li key={index} >
                                        <a href={film.link} className="font-medium">{cinema.showtimes.length>0?cinema.cinema:null}</a>
                                        <ul >
                                            {cinema.showtimes.map((dateGroup, dateIndex) => (
                                                <li key={dateIndex} className="mb-3">
                                                    <div className="flex flex-wrap gap-2 mt-1">
                                                        {dateGroup.hours.map((hour, hourIndex) => (
                                                            <span key={hourIndex} className="bg-blue-900 px-2 py-1 rounded-md text-xs">
                                        {hour}
                                    </span>
                                                        ))}
                                                    </div>
                                                </li>
                                            ))}
                                        </ul>
                                    </li>
                                ))}
                            </ul>
                        </div>
                    </div>

                    {/* Film Description */}
                    <div className="text-xs mt-3 overflow-y-auto scrollbar h-40">
                        <p className="p-2">{film.description}</p>
                    </div>
                </div>
            </div>
        </div>,
        document.body
    );
};

export default Modal;