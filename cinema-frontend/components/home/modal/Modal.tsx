import { createPortal } from "react-dom";
import type {CinemaSchedule, Film, FilmsWithShowtimes} from "@/types";
import Image from "next/image";
import React from "react";
import {format} from "date-fns";

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

    if (isOpen) {
        document.body.style.overflow = "hidden"; // ✅ Disable scrolling
    } else {
        document.body.style.overflow = "auto"; // ✅ Re-enable scrolling when modal closes
    }

    if (!isOpen) return null;

    return createPortal(
        <div
            className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 p-4 z-50"
            onClick={() => onOpenChange(false)}
        >
            <div
                className="bg-gradient-to-t from-blue-950 to-blue-800 max-w-[30vw] max-h-[80vm] p-6 rounded-2xl shadow-lg relative text-white overflow-hidden"
                onClick={(e) => e.stopPropagation()} // Prevent modal from closing when clicking inside
            >
                {/* Header */}
                <div className="flex justify-between">
                    <div className="text-lg font-bold">{film.title}</div>
                    <button onClick={() => onOpenChange(false)} className="text-xl font-medium">
                        ×
                    </button>
                </div>

                {/* Gradient Line */}
                <div className="h-px w-full bg-gradient-to-r from-blue-950 via-purple-500 to-purple-800 my-4" />

                {/* Content Section */}
                <div className="relative flex top-4 w-full justify-between">
                    {/* Film Image */}
                    <div className="relative h-36 w-36 overflow-hidden rounded-xl">
                        <Image src={film.imgPath} alt={film.title} fill className="object-cover" />
                    </div>

                    {/* Film Details and Showtimes */}
                    <div className="flex flex-col gap-2">
                        {/* Film Info */}
                        <ul className="text-xs">
                            <li>
                                <span className="font-medium">Reżyser:</span> {film.director}
                            </li>
                            <li>
                                <span className="font-medium">Rok:</span> {film.year}
                            </li>
                            <li>
                                <span className="font-medium">Rating:</span> {film.rating}
                            </li>
                        </ul>

                        {/* Cinema and Showtimes */}
                        <ul className="text-xs">
                            {matchingCinemaShowtimes.map((cinema, index) => (
                                <li key={index} className="mt-4">
                                    <span className="font-medium">{cinema.cinemaName}</span>
                                    <ul className="">
                                        {cinema.showtimes.flatMap((filmsWithShowtimes, sindex) =>
                                            filmsWithShowtimes.showtimes.map((time, tindex) => {
                                                const dateObj = new Date(time);
                                                const date = format(dateObj, "dd-MM");
                                                const hour = format(dateObj, "HH:mm");

                                                return (
                                                    <li key={`${sindex}-${tindex}`}>
                                                        <span className="pr-2">{date} </span> <span>{hour}</span>
                                                    </li>
                                                );
                                            })
                                        )}
                                    </ul>
                                </li>
                            ))}
                        </ul>
                    </div>
                </div>

                {/* Film Description */}
                <div className="relative h-[50%] text-xs mt-5 overflow-y-auto scrollbar line-clamp-4">
                    <p className="p-4">{film.description}</p>
                </div>
            </div>
        </div>,
        document.body
    );
};

export default Modal;