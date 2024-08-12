document.addEventListener("DOMContentLoaded", function() {
    const shows = [
        { title: "Show Title 1", image: "show1.jpg" },
        { title: "Show Title 2", image: "show2.jpg" },
        { title: "Show Title 3", image: "show3.jpg" },
        { title: "Show Title 4", image: "show4.jpg" },
        { title: "Show Title 5", image: "show5.jpg" },
        { title: "Show Title 6", image: "show6.jpg" },
        { title: "Show Title 7", image: "show7.jpg" },
        { title: "Show Title 8", image: "show8.jpg" },
        { title: "Show Title 9", image: "show9.jpg" },
        { title: "Show Title 10", image: "show10.jpg" }
    ];

    const showcaseGrid = document.getElementById('showcase-grid');

    shows.forEach(show => {
        const card = document.createElement('div');
        card.classList.add('card');

        const img = document.createElement('img');
        img.src = show.image;
        img.alt = show.title;
        img.classList.add('card-img-top');

        const cardBody = document.createElement('div');
        cardBody.classList.add('card-body');

        const title = document.createElement('p');
        title.classList.add('card-text');
        title.textContent = show.title;

        cardBody.appendChild(title);
        card.appendChild(img);
        card.appendChild(cardBody);

        showcaseGrid.appendChild(card);
    });
});
