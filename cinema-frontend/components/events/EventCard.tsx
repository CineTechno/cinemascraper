"use client"
import { useState } from "react"
import type { FilmEvent } from "@/types"
import { Card, CardContent } from "@/components/ui/card"
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog"
import Image from "next/image"

interface EventCardProps {
    event: FilmEvent
}

export function EventCard({ event }: EventCardProps) {
    const [isOpen, setIsOpen] = useState(false)

    return (
        <>
            <Card
                className="group cursor-pointer transition-transform duration-300 hover:scale-105 w-full"
                onClick={() => setIsOpen(true)}
            >
                <CardContent className="p-0">
                    <div className="relative w-full aspect-[2/3]">
                        <Image
                            src={event.link || "/placeholder.svg"}
                            alt={event.title}
                            fill
                            className="object-cover rounded-lg"
                        />
                        <div className="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent rounded-lg" />
                        <div className="absolute bottom-0 p-4 text-white">
                            <h3 className="font-bold text-xl mb-2">{event.title}</h3>
                            <p className="opacity-0 group-hover:opacity-100 transition-opacity duration-300">
                                {event.description}
                            </p>
                        </div>
                    </div>
                </CardContent>
            </Card>

            <Dialog open={isOpen} onOpenChange={setIsOpen}>
                <DialogContent className="max-w-3xl">
                    <DialogHeader>
                        <DialogTitle>{event.title}</DialogTitle>
                    </DialogHeader>
                    <div className="grid gap-4 py-4">
                        <div className="relative aspect-video">
                            <Image
                                src={event.link || "/placeholder.svg"}
                                alt={event.title}
                                fill
                                sizes="100vw"
                                className="object-cover rounded-lg"
                            />
                        </div>
                        <div className="grid gap-2">
                            <p className="text-lg">{event.description}</p>
                            <div className="text-sm text-muted-foreground">
                                <p>Date: {event.dateAndTime}</p>
                                <p>Location: {event.organiser}</p>
                            </div>
                        </div>
                    </div>
                </DialogContent>
            </Dialog>
        </>
    )

}

