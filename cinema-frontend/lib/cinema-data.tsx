export async function fetchAllCinemaSchedules(cinemaNames:string[]){
    try {
        const responses = await Promise.all(
            cinemaNames.map(name =>
                fetch(`http://superb-expression.railway.internal/api/cinemaschedule?cinema=${encodeURIComponent(name)}`)))

        const failedResponse = responses.find(response => !response.ok)

        if (failedResponse){
            throw new Error(`Błąd wczytywania danych:${failedResponse.status}`)
        }
        return await Promise.all(responses.map(response => response.json()))
    }catch(e){
        console.error("Błąd ściągania danych:", e)
        return []
    }
}