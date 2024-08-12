// Add event listeners for the "Yes" and "No" buttons
document.getElementById('confirmLogout').addEventListener('click', function() {
    // Redirect to the specified logout route
    window.location.href = '/home/'; // Adjust the path as needed
});

document.getElementById('cancelLogout').addEventListener('click', function() {
    // Redirect to the home or dashboard page
    window.location.href = '/home/'; // Adjust the path as needed
});
