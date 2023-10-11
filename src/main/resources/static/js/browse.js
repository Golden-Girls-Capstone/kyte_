document.addEventListener("DOMContentLoaded", function () {
    const modal = document.getElementById('myModal');
    const closeModalButton = document.getElementById('closeModalButton');
    const confirmButton = document.getElementById('submit-foster-btn');
    const profileCardsContainer = document.getElementById('profile-cards');
    const petIdInput = document.getElementById('petIdInput'); // Added this line
    const openModalButton = document.getElementById('openModalButton');


    // Function to open the modal and set the pet's API ID
    function openModal(petId) {
        petId = petIdInput
        // petIdInput.value = petId; // Set the value of the hidden input
        modal.style.display = 'block';
    }

    // Function to close the modal
    function closeModal() {
        modal.style.display = 'none';
    }


    // Event delegation to handle click events for dynamically generated "Foster" buttons
    profileCardsContainer.addEventListener('click', function(event) {
        if (event.target.classList.contains('openModalButton')) {
            const petId = event.target.getAttribute('data-pet-id');
            openModal(petId);
        }
    });

    // Event listeners for opening and closing the modal
    for (const card of profileCardsContainer.querySelectorAll('.openModalButton')) {
        card.addEventListener('click', function() {
            const petId = card.getAttribute('data-pet-id'); // Get the pet's API ID
            console.log('Clicked Foster button for pet ID:', petId); // Add this line
            openModal(petId); // Pass the API ID to the openModal function
        });
    }
    closeModalButton.addEventListener('click', closeModal);

    confirmButton.addEventListener('click', () => {
        // Add your code to handle the submission here
        alert('You confirmed!');
        closeModal();
    });

    // Function to render search results as profile cards
    function renderSearchResults(data) {
        profileCardsContainer.innerHTML = ''; // Clear the container

        for (const animalKey in data) {
            if (data.hasOwnProperty(animalKey)) {
                const animals = data[animalKey];
                for (let i = 0; i < animals.length; i++) {
                    const petData = animals[i];
                    const card = document.createElement('div');
                    card.classList.add('profile-card');

                    let imageUrl = '/img/default.jpg'; // Default image

                    if (petData.photos && petData.photos.length > 0) {
                        // Use the URL of the first photo from the API
                        imageUrl = petData.photos[0].medium || petData.photos[0].medium || petData.photos[0].small;
                    }
                    card.innerHTML = `
                    <div class="profile-image">
                            <img src="${imageUrl}" alt="Pet Image">
                    </div>
                        <input type="hidden"  id="petIdInput" name="petId" th:value="${petData.id}">
                        <h2 class="pet-name">${petData.name}</h2>
                    <div class="pet-status">${petData.status}</div>
                    <div class="profile-actions">
                        <form method="post" action="/browse">
                            <button type="submit" name="button" th:value="foster" class="openModalButton">Foster</button>
                        </form>
                        <form method="post" action="/browse">
                            <button type="submit" name="button" th:value="save" class="save-btn">Save</button>
                        </form>
                    </div>
                    `;
                    profileCardsContainer.appendChild(card);
                }
            }
        }
    }

    // Function to handle the form submission
    document.getElementById('search-form').addEventListener("submit", function(e) {
        e.preventDefault(); // Prevent the default form submission
        fetchBySearch();
    });

    // Function to fetch and render search results
    function fetchBySearch() {
        const category = document.getElementById('category').value;
        const zipcode = document.getElementById('zipcode').value;
        const age = document.getElementById('age').value;
        const size = document.getElementById('size').value;

        fetch('/api/token', {
            method: 'GET',
            headers: {
                'Accept': 'text/plain',
            }
        })
            .then(response => response.text())
            .then(token => {
                const bearerToken = `Bearer ${token}`;
                // Include age and size in the API request
                const apiUrl = `/api/data/search?category=${category}&zipcode=${zipcode}&age=${age}&size=${size}`;

                return fetch(apiUrl, {
                    method: 'GET',
                    headers: {
                        'Authorization': bearerToken,
                        'Content-Type': 'application/json'
                    },
                });
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                // Render the search results as profile cards
                renderSearchResults(data);
            })
            .catch(error => {
                console.error('Fetch error:', error);
            });
    }

    // Initial data fetch and render (if needed)
    // fetchDataAndRender(); // If you have an initial data fetch
});
