//modal functionality
document.addEventListener("DOMContentLoaded", function () {
    const modal = document.getElementById('myModal');
    const openModalButtons = document.querySelectorAll('.openModalButton');
    const closeModalButton = document.getElementById('closeModalButton');
    const confirmButton = document.getElementById('submit-foster-btn');

    // Function to open the modal
    function openModal() {
        modal.style.display = 'block';
    }

    // Function to close the modal2
    function closeModal() {
        modal.style.display = 'none';
    }

    // Event listeners for opening and closing the modal
    for (const button of openModalButtons) {
        button.addEventListener('click', openModal);
    }

    closeModalButton.addEventListener('click', closeModal);

    confirmButton.addEventListener('click', () => {
        // Add your code to handle the submission here
        alert('You confirmed!');
        closeModal();
    });
});

//fetching
fetch('/api/token', {
    method: 'GET',
    headers: {
        'Accept': 'text/plain',
    }
})
    .then(response => response.text())
    .then(token => {
        // Save the token into a const
        const bearerToken = `Bearer ${token}`;
        fetch('/api/data', {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer'  + bearerToken,
                'Content-Type': 'application/json'
            },
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                console.log(data)
                // response data
                const dataList = document.getElementById('data-list'); // Get the <ul> element
                for (animalKey in data) {
                    if(data.hasOwnProperty(animalKey)) {
                        const animals = data[animalKey];
                        for(let i = 1; i < 20; i++) {
                            if(animals[i]) {
                                let listItem = document.createElement('li');
                                listItem.textContent = `${animals[i].name}, ${animals[i].age}, ${animals[i].contact.address.postcode}`;
                                dataList.append(listItem);
                            }
                        }
                    }
                }
            })
            .catch(error => {
                console.error('Second Fetch error:', error);
            });
    })
    .catch(error => {
        // Handle any errors
        console.error('First Fetch error:', error);

    });