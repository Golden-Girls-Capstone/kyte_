
document.addEventListener("DOMContentLoaded", (event) => {
    const password = document.getElementById("password-input");
    const passwordAlert = document.getElementById("password-alert");

    // const leng = document.getElementById("password-leng");
    // const bigLetter = document.getElementById("password-big-letter");
    // const num = document.getElementById("password-num");
    // const specialChar = document.getElementById("password-special-char");

    const requirements = document.querySelectorAll(".requirements");
    let lengBoolean, bigLetterBoolean, numBoolean, specialCharBoolean;
    const specialChars = "!@#%^&*-_=+;:.?";
    const numbers = "0123456789";

    requirements.forEach((element) => element.classList.add("wrong"));

    password.addEventListener("focus", () => {
        passwordAlert.classList.remove("d-none");
        if (!password.classList.contains("is-valid")) {
            password.classList.add("is-invalid");
        }
    });

    password.addEventListener("input", () => {
        let value = password.value;
        if (value.length < 8) {
            lengBoolean = false;
        } else if (value.length > 7) {
            lengBoolean = true;
        }

        if (value.toLowerCase() == value) {
            bigLetterBoolean = false;
        } else {
            bigLetterBoolean = true;
        }

        numBoolean = false;
        for (let i = 0; i < value.length; i++) {
            for (let j = 0; j < numbers.length; j++) {
                if (value[i] == numbers[j]) {
                    numBoolean = true;
                }
            }
        }

        specialCharBoolean = false;
        for (let i = 0; i < value.length; i++) {
            for (let j = 0; j < specialChars.length; j++) {
                if (value[i] == specialChars[j]) {
                    specialCharBoolean = true;
                }
            }
        }

        if (lengBoolean && bigLetterBoolean && numBoolean && specialCharBoolean) {
            password.classList.remove("is-invalid");
            password.classList.add("is-valid");


            passwordAlert.classList.remove("alert-warning");
            passwordAlert.classList.add("alert-success");
        } else {
            password.classList.remove("is-valid");
            password.classList.add("is-invalid");

            passwordAlert.classList.add("alert-warning");
            passwordAlert.classList.remove("alert-success");

        }
    });

    password.addEventListener("blur", () => {
        passwordAlert.classList.add("d-none");
    });
});


document.addEventListener("DOMContentLoaded", (event) => {

    const username = document.getElementById("username-input");
    const usernameAlert = document.getElementById("username-alert");
    const leng = document.getElementById("leng");

    const requirements = document.querySelectorAll(".requirements");
    let lengBoolean;

    requirements.forEach((element) => element.classList.add("wrong"));

    username.addEventListener("focus", () => {
        usernameAlert.classList.remove("d-none");
        if (!username.classList.contains("is-valid")) {
            username.classList.add("is-invalid");
        }
    });

    username.addEventListener("input", () => {
        let value = username.value;
        if (value.length < 3) {
            lengBoolean = false;
        } else if (value.length >= 4) {
            lengBoolean = true;
        }

        if (lengBoolean == true) {
            username.classList.remove("is-invalid");
            username.classList.add("is-valid");

            usernameAlert.classList.remove("alert-warning");
            usernameAlert.classList.add("alert-success");
        } else {
            username.classList.remove("is-valid");
            username.classList.add("is-invalid");

            usernameAlert.classList.add("alert-warning");
            usernameAlert.classList.remove("alert-success");

        }
    });

    username.addEventListener("blur", () => {
        usernameAlert.classList.add("d-none");
    });

});
