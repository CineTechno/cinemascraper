export async function fetchAllCinemaSchedules(cinemaNames:string[]){
    try {
        const responses = await Promise.all(
            cinemaNames.map(name =>
                fetch(`http://localhost:8080/api/cinemaschedule?cinema=${encodeURIComponent(name)}`)))

        const failedResponse = responses.find(response => !response.ok)

        if (failedResponse){
            throw new Error(`Błąd wczytywania danych:${failedResponse.status}`)
        }
        const data = await Promise.all(responses.map(response =>response.json()))
        return data
    }catch(e){
        console.error("Błąd ściągania danych:", e)
        return []
    }
}