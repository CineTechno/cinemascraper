import type { NextConfig } from "next";

const nextConfig: NextConfig = {
    images: {
        remotePatterns: [
            {
                protocol: 'https',
                hostname: 'medstore.kinoteka.pl',
                pathname: '/**',
            },
            {
                protocol: 'https',
                hostname: 'kinomuranow.pl',
                pathname: '/**',
            },
            {
                protocol: 'https',
                hostname: 'www.iluzjon.fn.org.pl',
                pathname: '/**',
            },
            {
                protocol: 'https',
                hostname: "multimedia.novekino.pl",
                pathname: '/**',
            },
        ],
    },
};

export default nextConfig;
