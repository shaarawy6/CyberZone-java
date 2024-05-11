const options = {
    background: {
        color: "transparent",
    },
    interactivity: {
        events: {
            onClick: {
                enable: true,
                mode: "push",
            },
            onHover: {
                enable: true,
                mode: "repulse",
            },
        },
        modes: {
            push: {
                quantity: 1,
            },
            repulse: {
                distance: 100,
            },
        },
    },
    particles: {
        color: {
            value: "#ec2f20", 
        },
        links: {
            enable: true,
            opacity: 0.8,
            distance: 200,
            color: "#ec2f20",
        },
        move: {
            enable: true,
            speed: { min: 1, max: 2 },
        },
        opacity: {
            value: { min: 0.7, max: 0.8 },
        },
        size: {
            value: { min: 1, max: 2 },
        },
    },
};

if (window.matchMedia("(max-width: 480px)").matches) {
    options.particles.size.value.min = 1;
    options.particles.size.value.max = 2;
    options.particles.links.opacity = 0.4; 
    options.particles.move.speed.min = 0.2;
    options.particles.move.speed.min = 0.3;
    options.particles.links.distance = 110;
    options.particles.links.color = "#ff0000";
    options.particles.color= "#ff0000";
}

tsParticles.load("tsparticles", options);
