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

