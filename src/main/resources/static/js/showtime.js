document.addEventListener('DOMContentLoaded', () => {
    const dateButtons = document.querySelectorAll('.date-button');
    dateButtons.forEach(button => {
        button.addEventListener('click', () => {
            dateButtons.forEach(btn => btn.classList.remove('active'));
            button.classList.add('active');
            // Add logic to fetch and display sessions for the selected date
        });
    });

    const timeButtons = document.querySelectorAll('.time');
    timeButtons.forEach(button => {
        button.addEventListener('click', () => {
            // Add logic to handle showing session details or delete action
            console.log(`Session time clicked: ${button.textContent}`);
        });
    });
});
