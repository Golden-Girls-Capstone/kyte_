

function hideLoader() {
    document.querySelector("#hamsterLoader").style.display ='none';
}
setTimeout(hideLoader, 3000);







document.onreadystatechange = function() {
    if (document.readyState !== "complete") {
        // Add the "loading" class to the body element to apply the blur filter
        document.querySelector("body").classList.add("loading");
        document.querySelector("#loading-ham").style.visibility = "visible";
            document.querySelector("#content-container").style.display = "block";
    } else {
        // Delay the execution of this code by 5 seconds (5000 milliseconds)
        setTimeout(function() {
            document.querySelector("#loading-ham").style.display = "none";
            document.querySelector("body").classList.remove("loading");
        }, 3000);
    }
};
