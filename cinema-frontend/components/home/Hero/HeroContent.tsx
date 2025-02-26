interface HeroContentProps {
    title: string

}

export function HeroContent({ title }: HeroContentProps) {
    return (
        <div className="grid lg:grid-cols-2 gap-2 items-center mb-12 max-w-7xl mx-auto">
            <h2 className="text-4xl sm:text-3xl font-bold text-white">{title}</h2>

        </div>
    )
}