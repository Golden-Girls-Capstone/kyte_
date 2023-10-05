//
// // const clientId = 'PETFINDER_APPID';
// // const clientSecret = 'PET_SECRET';
// //
// // // Create a base64-encoded string of your client ID and client secret
// // const credentials = btoa(`${clientId}:${clientSecret}`);
// //
// // // Define the request parameters
// // const tokenUrl = 'https://api.petfinder.com/v2/oauth2/token';
// // const params = new URLSearchParams({
// //     'grant_type': 'client_credentials'
// // });
// //
// // // Make the request to obtain the access token
// // fetch(tokenUrl, {
// //     method: 'POST',
// //     headers: {
// //         'Authorization': `Basic ${credentials}`,
// //         'Content-Type': 'application/x-www-form-urlencoded',
// //     },
// //     body: params,
// // })
// //     .then(response => response.json())
// //     .then(data => {
// //         const accessToken = data.access_token;
// //         // Log the access token to the console
// //         console.log('Access Token:', accessToken);
// //     })
// //     .catch(error => {
// //         console.error('Error obtaining access token:', error);
// //     });
//

$(() => {
    const accessToken = PET_TOKEN;
    const apiKey = PETFINDER_APPID;

    // Function to fetch data from the Petfinder API and populate profile cards
    function fetchDataAndPopulateCards() {
        const apiUrl = 'https://api.petfinder.com/v2/animals';

        // Customize the parameters as needed
        const params = {
            type: 'dog', // Example: Fetch dogs
            location: 'New York', // Example: Specify location
        };

        // Construct the URL with query parameters
        const queryString = new URLSearchParams(params).toString();
        const apiUrlWithParams = `${apiUrl}?${queryString}`;

        // Fetch data from the API
        fetch(apiUrlWithParams, {
            headers: {
                'Authorization': `Bearer ${accessToken}`,
                'x-api-key': apiKey,
            }
        })
            .then(response => response.json())
            .then(data => {
                // Process the fetched data and populate profile cards
                const pets = data.animals;
                const container = document.querySelector('.cards-container');

                pets.forEach(pet => {
                    // Create a profile card for each pet and append it to the container
                    container.appendChild(createProfileCard(pet));
                });
            })
            .catch(error => {
                console.error('Error fetching animal data:', error);
            });
    }

    // Call the function to fetch and populate profile cards when the page loads
    window.addEventListener('load', fetchDataAndPopulateCards);

    // Function to create a profile card for a pet
    function createProfileCard(pet) {
        // Create a new profile card element
        const card = document.createElement('div');
        card.classList.add('profile');

        // Determine the image URL
        let imageUrl = '/img/default.jpg'; // Default image

        if (pet.photos && pet.photos.length > 0) {
            // Use the URL of the first photo from the API
            imageUrl = pet.photos[0].medium || pet.photos[0].medium || pet.photos[0].small;
        }

        // Populate the card with pet data
        card.innerHTML = `
            <div class="profile-image">
                <img src="${imageUrl}" alt="Pet Image">
            </div>
            <h2 class="pet-name">${pet.name || 'Unknown Name'}</h2>
            <div class="pet-status">${pet.status || 'Status Unknown'}</div>
            <!-- Add more card content here as needed -->
            <div class="profile-actions">
                <form th:action="@{/browse}" th:method="post">
                    <button class="foster-btn">Foster</button>
                </form>

                <form th:action="@{/browse}" th:method="post">
                    <button class="save-btn">Save</button>
                </form>
            </div>
        `;

        return card;
    }

    // Function to fetch and send data to your backend
    function fetchDataAndSendDataToBackend() {
        const apiUrl = 'https://api.petfinder.com/v2/animals';

        //  the parameters
        const params = {
            type: 'dog', //  Fetch dogs
            location: 'New York', //  Specify location
        };

        // Construct the URL with query parameters
        const queryString = new URLSearchParams(params).toString();
        const apiUrlWithParams = `${apiUrl}?${queryString}`;

        // Fetch data from the API
        fetch(apiUrlWithParams, {
            headers: {
                'Authorization': `Bearer ${accessToken}`,
                'x-api-key': apiKey,
            }
        })
            .then(response => response.json())
            .then(data => {
                // Send the entire data.animals array to your backend
                sendDataToBackend(data.animals);
            })
            .catch(error => {
                console.error('Error fetching animal data:', error);
            });
    }

    // Calling the function to fetch and send data to your backend when the page loads
    window.addEventListener('load', fetchDataAndSendDataToBackend);

    // Function to send data to your backend
    function sendDataToBackend(petsData) {
        // Customize the backend endpoint URL
        const backendUrl = '/api/data'; // Replace with your actual backend endpoint

        // Make a POST request to your backend with the pet data
        fetch(backendUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ pets: petsData }), // Send the pets data as JSON
        })
            .then(response => {
                if (response.ok) {
                    console.log('Data sent to backend successfully.');
                } else {
                    console.error('Error sending data to backend:', response.statusText);
                }
            })
            .catch(error => {
                console.error('Error sending data to backend:', error);
            });
    }
});
