"use client"
export default function CinemaBackground() {
    return (
        <div className="absolute inset-0 w-full h-full -z-10 bg-black">
            {/* Main projector light effect */}
            <div
                className="absolute inset-0 bg-gradient-to-r from-transparent via-blue-500/10 to-transparent animate-cinema-light"
                style={{
                    backgroundSize: '200% 200%',
                    animation: 'cinemaLight 8s ease-in-out infinite',
                }}
            />
            {/* Secondary ambient glow */}
            <div
                className="absolute inset-0 bg-gradient-to-b from-blue-900/20 via-blue-800/5 to-transparent"
            />
            {/* Additional atmospheric effect */}
            <div className="absolute inset-0 bg-black/40" />

            <style jsx global>{`
        @keyframes cinemaLight {
          0% {
            background-position: -50% 50%;
          }
          50% {
            background-position: 150% 50%;
          }
          100% {
            background-position: -50% 50%;
          }
        }
      `}</style>
        </div>
    );
}