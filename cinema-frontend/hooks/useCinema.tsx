import {useEffect, useState} from "react";
import {CinemaSchedule} from "@/types";

interface getAllSchedulesReturn{
    schedules:CinemaSchedule[],
    isLoading:boolean
    error:string
}

export function getStaticProps(){
    try{

    }
}

export function UseGetAllSchedule(cinemaNames:string[]):getAllSchedulesReturn{
    const [schedules, setSchedules] = useState<CinemaSchedule[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const[error, setError] = useState<string>("")

    useEffect(() => {
        if (cinemaNames.length == 0) return
        const fetchSchedules = async () => {
            setIsLoading(true);
            try {
                const responses = await Promise.all(
                    cinemaNames.map(name=>fetch(`http://localhost:8080/api/cinemaschedule?cinema=${name}`))
                )
                const failedResponses = responses.find(response=> !response.ok)
                if(failedResponses){
                    throw new Error(`Błąd przy wczytywaniu danych ${failedResponses.status}`)
                }
                const data = await Promise.all(responses.map(res => res.json()))
                setSchedules(data)

            }catch(err){
                setError(err instanceof Error?err.message:"nieznany błąd")
            }finally {
                setIsLoading(false)
            }
        }
        fetchSchedules()
    },[cinemaNames]);
    return {schedules, isLoading, error}
}
