
const form = document.querySelector("#form");

form.addEventListener("submit", function (e){

    form.querySelector('#usernameError').innerText = '';
    form.querySelector('#passwordError').innerText = '';

    let username = form.querySelector('#username').value;
    let password = form.querySelector('#password').value;

    if (username === '') {
        e.preventDefault();
        form.querySelector('#usernameError').innerText = 'Username is required';
    }

    if (password === '') {
        e.preventDefault();
        form.querySelector('#passwordError').innerText = 'Password is required';

    }

});
