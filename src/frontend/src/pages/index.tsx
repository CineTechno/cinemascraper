
import {Card, CardHeader, CardBody, CardFooter} from "@heroui/card";
import DefaultLayout from "@/layouts/default";
import {Divider} from "@heroui/divider";
import Carousel from "@/components/Carousel.tsx";
import {useScraper} from "@/hooks/useScraper.tsx";
import {useState} from "react";
import dayjs from "dayjs";
import {films} from "@/types/index.ts"
import {DatePicker} from "@heroui/date-picker";
import { DateValue } from "@hero-ui/react"


export default function IndexPage() {

    const [muranowFilms, setMuranowFilms] = useState<films>({})
    const [selectedDate, setSelectedDate] = useState<DateValue>(dayjs())
    useScraper("http://localhost:8080/muranow", setMuranowFilms);

    const handleDate = (date)=>{
        setSelectedDate(dayjs(date))
    }

  // @ts-ignore
    return (
    <DefaultLayout>
      <section className="flex flex-col items-center justify-center gap-4 py-8 md:py-10">
            <DatePicker onChange={handleDate} className="w-1/10"></DatePicker>
          <Carousel muranowFilms={muranowFilms} date = {selectedDate}></Carousel>
        <p>{selectedDate.format("DD-MM-YYYY")}</p>
      </section>
    </DefaultLayout>
  );
}
