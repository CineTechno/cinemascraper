import { useState } from "react"
import type { Film } from "@/types"
import { Card, CardContent } from "@/components/ui/card"
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog"
import Image from "next/image"



export function FilmCard(film:Film) {
    const [isOpen, setIsOpen] = useState(false)

    return (
        <>
            <Card
                className="group cursor-pointer transition-transform duration-300 hover:scale-105"
                onClick={() => setIsOpen(true)}
            >
                <CardContent className="p-0 relative aspect-[2/3]">
                    <Image src={film.imgPath || "/placeholder.svg"} alt={film.title} fill className="object-cover rounded-lg" />
                    <div className="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent rounded-lg" />
                    <div className="absolute bottom-0 p-4 text-white">
                        <h3 className="font-bold text-xl mb-2">{film.title}</h3>
                        <p className="opacity-0 group-hover:opacity-100 transition-opacity duration-300">{film.description}</p>
                    </div>
                </CardContent>
            </Card>

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
                                    {film.dateShowTime.map((showtime, index) => (
                                        <div key={index} className="flex justify-between text-sm">
                                            <span>{film.cinema}</span>
                                            <span>{film.dateShowTime}</span>
                                        </div>
                                    ))}
                                </div>
                            </div>
                        </div>
                    </div>
                </DialogContent>
            </Dialog>
        </>
    )
}

