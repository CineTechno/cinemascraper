import {Dialog, DialogContent, DialogHeader, DialogTitle} from "@/components/ui/dialog";
import Image from "next/image";

export function DialogFulInfo ({isOpen, onOpenChange, matchingCinemaShowtimes}:{isOpen:boolean, onOpenChange: ()=>void, matchingCinemaShowtimes})

return(
<Dialog open={isOpen} onOpenChange={setIsOpen}>
    <DialogContent className="max-w-3xl">
        <DialogHeader>
            <DialogTitle>{film.title}</DialogTitle>
        </DialogHeader>
        <div className="grid gap-4 py-4">
            <div className="aspect-video relative">
                <Image src={film.imgPath || "/placeholder.svg"} alt={film.title} fill className="object-cover rounded-lg" />
            </div>
            <div className="grid gap-2">
                <p className="text-lg">{film.description}</p>
                <div className="text-sm text-muted-foreground">
                    <p>Director: {film.director}</p>
                    <p>Year: {film.year}</p>
                </div>
                <div className="mt-4">
                    <h4 className="font-semibold mb-2">Showtimes:</h4>
                    <div className="grid gap-2">
                        {matchingCinemaShowtimes.map((showtime, index) => (
                            <div key={index} className="flex justify-between text-sm">
                                <span>{showtime.cinemaName}</span>
                                <span>{showtime.dateShowTime}</span>
                            </div>
                        ))}
                    </div>
                </div>
            </div>
        </div>
    </DialogContent>
</Dialog>
)