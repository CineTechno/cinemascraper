'use client'
import { Calendar } from "@/components/ui/calendar"
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover"
import { Button } from "@/components/ui/button"
import {CalendarIcon, ChevronLeft, ChevronRight} from "lucide-react"
import { format } from "date-fns"
import { cn } from "@/lib/utils"
import { addDays, subDays } from 'date-fns'
import { pl } from "date-fns/locale"
import {useDateContext} from "@/context/DateContext";

export const DateNavigation = () => {
    const {selectedDate, setSelectedDate} = useDateContext()
    const handlePrevDay = () => {
        const today = new Date();

        if (selectedDate.getTime() >= today.getTime()) {
            setSelectedDate(subDays(selectedDate, 1));
        }
    };
    const handleNextDay = () => {
        const todayPlus7 = new Date();
        todayPlus7.setDate(todayPlus7.getDate() + 5);

        if (selectedDate.getTime() <= todayPlus7.getTime()) {
            setSelectedDate(addDays(selectedDate, 1));
        }
    };

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
                            !selectedDate && "text-muted-foreground"
                        )}
                    >
                        <CalendarIcon></CalendarIcon>
                        {selectedDate ? format(selectedDate, "dd/MM EEEE", {locale:pl}) : <span >Pick a selectedDate</span>}
                    </Button>
                </PopoverTrigger>
                <PopoverContent className="w-auto p-0 bg-white" align="end">
                    <Calendar
                        mode="single"
                        selected={selectedDate}
                        onSelect={(newDate) => newDate && setSelectedDate(newDate)}
                        initialFocus
                        fromDate={new Date()}
                        toDate={addDays(new Date(), 7)}
                        disableNavigation
                    />
                </PopoverContent>
            </Popover>

            <Button
                variant="ghost"
                size="sm"
                onClick={handleNextDay}
                className="px-0 py-0"
            >
                <div>
                    <ChevronRight/>
                </div>
            </Button>
        </div>
    );
}





