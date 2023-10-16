document.addEventListener('DOMContentLoaded', function() {
    window.addEventListener('scroll', function() {
        console.log('Scrolling detected');
        let navbar = document.getElementById('myNavbar');
        if (window.scrollY > 50) {
            navbar.classList.add('shadow');
        } else {
            navbar.classList.remove('shadow');
        }
    });
});

// Get all elements with the class "icon" inside the connections
const iconElements = document.querySelectorAll('.connection .icon');

// Add a click event listener to each icon element
iconElements.forEach((icon) => {
    icon.addEventListener('click', function (event) {
        // Prevent the default link behavior
        event.preventDefault();

        // Get the link from the parent anchor element
        const link = this.parentElement.getAttribute('href');

        // Redirect to the link
        window.location.href = link;
    });
});
