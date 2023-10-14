document.addEventListener("DOMContentLoaded", function () {
    const modal = document.querySelector('#myModal');
    const profileCardsContainer = document.getElementById('profile-cards');
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');


    // Function to open the modal and set the pet's API ID
    function openModal(petData, imageUrl) {
        console.log(petData);
        modal.innerHTML = `
        <div  class="modal-content modal-dialog modal-dialog-centered modal-dialog-scrollable" >

        <span id="closeModalButton" class="close">&times;</span>
        <h2 class="modal-name"></h2>
        <img class="modal-image" src="${imageUrl}">
        <p class="modal-pet-info-browse">Name: ${petData.name}</p>
        <p class="modal-pet-info-browse">Age: ${petData.age}</p>
        <p class="modal-pet-info-browse">Gender: ${petData.gender}</p>
        <p class="modal-pet-info-browse">Age: ${petData.status}</p>


            <input type="hidden" id="petId" name="petId" value="${petData.id}">
            <input type="hidden" id="petName" name="petName" value="${petData.name}">
            <input type="hidden" id="petType" name="petType" value="${petData.type}">
            <input type="hidden" id="petBreed" name="petBreed" value="${petData.breeds[0]}">
            <input type="hidden" id="petAge" name="petAge" value="${petData.age}">
            <input type="hidden" id="petSize" name="petSize" value="${petData.size}">
            <input type="hidden" id="petPhoto" name="petPhoto" value="${imageUrl}">
            <input type="hidden" id="petGender" name="petGender" value="${petData.gender}">
            <input type="hidden" id="petStatus" name="petStatus" value="${petData.status}">
            <label for="start">Start Date: </label>
            <input type="date" id="start" name="startDate" placeholder="MM/DD/YYYY">
            <label for="end" >End Date: </label>
            <input type="date" id="end" name="endDate" placeholder="MM/DD/YYYY">
            <input id="submit-foster-btn" type="submit" value="Submit" name="submitFoster">
    </div>
        `
    const closeModalButton = modal.querySelector('#closeModalButton');
    const confirmButton = modal.querySelector('#submit-foster-btn');
        modal.style.display = 'block';
        confirmButton.addEventListener('click', async function() {
            const firstUrl = '/browse/pet'; // ******enter endpoint******
            const petObject = {
                apiId: petData.id,
                name: petData.name,
                type: petData.type,
                breed: petData.breeds[0],
                age: petData.age,
                size: petData.size,
                photo: imageUrl,
                gender: petData.gender,
                status: petData.status === 'adoptable'
            }
            const petFetchOptions = {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': csrfToken
                },
                body: JSON.stringify(petObject)
            }

            let petFetchResponse = await fetch(firstUrl, petFetchOptions);
            let petReturnData = await petFetchResponse.json();
            console.log(petReturnData);
            let startDate = modal.querySelector('#start').value;
            let endDate = modal.querySelector('#end').value;
            const secondUrl = `/browse/foster/${petReturnData.id}/${startDate}/${endDate}`;

            let fosterPetObject = {
                startDate: startDate,
                endDate: endDate
            }
            let fosterPetOptions = {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': csrfToken
                }
            }
            let fosterFetchResponse = await fetch(secondUrl, fosterPetOptions);
            let fosterPetReturnData = await fosterFetchResponse.json();
            console.log(fosterPetReturnData);
            modal.style.display = 'none';


        })
    // closeModalButton.addEventListener('click', closeModal);
    // confirmButton.addEventListener('click', () => {
    //     // Add your code to handle the submission here
    //     alert('You confirmed!');
    //     closeModal();
    // });
    }

    // Function to close the modal
    function closeModal() {
        modal.style.display = 'none';
    }

    // Event listeners for opening and closing the modal
    // for (const card of profileCardsContainer.querySelectorAll('.openModalButton')) {
    //     card.addEventListener('click', function() {
    //         const petId = card.getAttribute('data-pet-id'); // Get the pet's API ID
    //         console.log('Clicked Foster button for pet ID:' + petId); // Add this line
    //         openModal(petId); // Pass the API ID to the openModal function
    //     });
    // }


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

                    let imageUrl = '/img/place-holder-pets.png'; // Default image

                    if (petData.photos && petData.photos.length > 0) {
                        // Use the URL of the first photo from the API
                        imageUrl = petData.photos[0].medium || '/img/place-holder-pets.png';
                    }
                    card.innerHTML = `
                    <div class="profile-image">
                            <img src="${imageUrl}" alt="Pet Image">
                    </div>
                        <h2 class="pet-name">${petData.name}</h2>
                    <div class="pet-status">${petData.status}</div>
                    <div class="profile-actions">
<!--                        <form method="post" action="/browse">-->
                            <button type="submit" name="fosterButton" class="openModalButton" data-pet-image="${imageUrl}">Foster</button>
<!--                        </form>-->
<!--                        <form method="post" action="/browse">-->
                            <button type="submit" name="button" value="save" class="save-btn">Save</button>
<!--                        </form>x-->
                    </div>
                    `;

                    card.querySelector('.openModalButton').addEventListener('click', function(e) {
                        console.log(petData);
                        openModal(petData, e.target.getAttribute('data-pet-image'));
                    });
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
        const type = document.getElementById('type').value;
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
                const apiUrl = `/api/data/search?type=${type}&age=${age}&size=${size}&zipcode=${zipcode}&page=1`;

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






//POTENTIAL JAVASCRIPT FOR PAGINATION

// let currentPage = 1; // Initial page
// const itemsPerPage = 10; // Number of items per page
//
// function fetchPage(page) {
//     // Make an API request with the desired page parameter
//     // Update your API endpoint and handling as needed
//     fetch(`/api/resource?page=${page}&per_page=${itemsPerPage}`)
//         .then((response) => response.json())
//         .then((data) => {
//             // Process and display data here
//         })
//         .catch((error) => {
//             console.error(error);
//         });
// }
//
// // Handle the "Next" button click
// document.getElementById('forward').addEventListener('click', () => {
//     currentPage++;
//     fetchPage(currentPage);
// });
//
// // Handle the "Previous" button click
// document.getElementById('backward').addEventListener('click', () => {
//     if (currentPage > 1) {
//         currentPage--;
//         fetchPage(currentPage);
//     }
// });
//
// // Initial API request
// fetchPage(currentPage);