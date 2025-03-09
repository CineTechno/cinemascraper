interface HeroContentProps {
    title: string

}

export function HeroContent({ title }: HeroContentProps) {
    return (
            <h2 className="text-4xl sm:text-3xl font-bold text-white pb-6">{title}</h2>
    )
}