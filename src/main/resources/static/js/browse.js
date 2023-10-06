const modal = document.getElementById('myModal');
const openModalButton = document.getElementById('openModalButton');
const closeModalButton = document.getElementById('closeModalButton');
const confirmButton = document.getElementById('confirmButton');
// Function to open the modal
function openModal() {
    modal.style.display = 'block';
}
// Function to close the modal
function closeModal() {
    modal.style.display = 'none';
}
// Event listeners for opening and closing the modal
openModalButton.addEventListener('click', openModal);
closeModalButton.addEventListener('click', closeModal);
// Event listener for the confirm button (you can customize this)
confirmButton.addEventListener('click', () => {
    // Add your code to handle confirmation here
    alert('You confirmed!');
    closeModal();
});
// Function to open the modal
function openModal() {
    modal.style.display = 'block';
}
// Function to close the modal
function closeModal() {
    modal.style.display = 'none';
}
// Event listeners for opening and closing the modal
openModalButton.addEventListener('click', openModal);
closeModalButton.addEventListener('click', closeModal);
// Event listener for the confirm button (you can customize this)
confirmButton.addEventListener('click', () => {
    // Add your code to handle confirmation here
    alert('You confirmed!');
    closeModal();
});

