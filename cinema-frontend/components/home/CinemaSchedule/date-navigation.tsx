'use client'
import {useState} from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Carousel, CarouselContent, CarouselItem, CarouselNext, CarouselPrevious } from "@/components/ui/carousel"
import { Calendar } from "@/components/ui/calendar"
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover"
import { Button } from "@/components/ui/button"
import {CalendarIcon, ChevronLeft, ChevronRight} from "lucide-react"
import { format } from "date-fns"
import { cn } from "@/lib/utils"
import {CinemaSchedule} from "@/types";
import { addDays, subDays } from 'date-fns'

export const DateNavigation = ({ date, onDateChange }: {
    date: Date,
    onDateChange?: (date: Date) => void
}) => {
    const handlePrevDay = () => onDateChange?.(subDays(date, 1))
    const handleNextDay = () => onDateChange?.(addDays(date, 1))

    return (
        <div className="flex items-center h-2">
            <Button
                variant="ghost"
                size="sm"
                onClick={handlePrevDay}
                className="px-0"
            >
                <div>
                <ChevronLeft/>
                </div>
            </Button>

            <Popover>
                <PopoverTrigger asChild className="px-2">
                    <Button
                        variant="ghost"
                        className={cn(
                            " justify-start text-left font-normal",
                            !date && "text-muted-foreground"
                        )}
                    >
                        <CalendarIcon></CalendarIcon>
                        {date ? format(date, "P") : <span >Pick a date</span>}
                    </Button>
                </PopoverTrigger>
                <PopoverContent className="w-auto p-0" align="end">
                    <Calendar
                        mode="single"
                        selected={date}
                        onSelect={(newDate) => newDate && onDateChange?.(newDate)}
                        initialFocus
                    />
                </PopoverContent>
            </Popover>

            <Button
                variant="ghost"
                size="sm"
                onClick={handleNextDay}
                className="px-0 py-0"
            >
                <ChevronRight/>
            </Button>
        </div>
    );
}





