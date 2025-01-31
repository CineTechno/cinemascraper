import {useEffect} from "react";


export function useScraper(url: string, setState: (arg0: any) => any){


    useEffect(() => {
        fetch(url).then((response) => {
            if(!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json()
        }).then(data => setState(data))
            .catch((error)=> console.log("error fetching ", error))

    }, []);


}