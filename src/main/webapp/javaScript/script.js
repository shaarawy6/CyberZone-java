//phone screen
function toggleDropdown() {
    var dropdownContent = document.getElementById("myDropdown");
    if (dropdownContent.style.opacity === "0") {
        // Show dropdown content with opacity transition
        dropdownContent.style.opacity = "1";
        dropdownContent.style.transition = "opacity 1s";
    } else {
        // Hide dropdown content
        dropdownContent.style.opacity = "0";
    }
}


//phone screen
function toggleDropdown() {
    var dropdownContent = document.getElementById("myDropdown");
    var button = document.getElementById("dropdownButton");

    // Toggle visibility of dropdown content
    if (dropdownContent.style.opacity === "1") {
        // Hide dropdown content
        dropdownContent.style.opacity = "0";
    } else {
        // Show dropdown content
        dropdownContent.style.opacity = "1";
    }

    // Toggle rotation of button
    button.classList.toggle("rotate");
}

//phone screen
document.addEventListener('DOMContentLoaded', function() {
    const button = document.getElementById('ddm_button');
    const iconContainer = document.getElementById('mydd_content');

    button.addEventListener('click', function() {
        if (iconContainer.style.display === 'block') {
            iconContainer.style.opacity = '0';
            setTimeout(function() {
                iconContainer.style.display = 'none';
            }, 1500);
        } else {
            iconContainer.style.display = 'block';
            setTimeout(function() {
                iconContainer.style.opacity = '1';
            }, 100);
        }
    });
});

//register button
document.addEventListener("DOMContentLoaded", () => {
    const registerButton = document.getElementById("registerButton");
    const optionsContainer = document.getElementById("optionsContainer");
    const signInButton = document.getElementById("signInButton");
    const signUpButton = document.getElementById("signUpButton");

    // Show options container when register button is clicked
    registerButton.addEventListener("click", () => {
        optionsContainer.style.display = "flex";
    });

    // Hide options container when clicking outside the container
    document.addEventListener("click", (event) => {
        if (!optionsContainer.contains(event.target) && event.target !== registerButton) {
            optionsContainer.style.display = "none";
        }
    });

    // Handle sign in button click
    signInButton.addEventListener("click", () => {
        alert("Sign In clicked"); // Replace with your sign in logic
    });

    // Handle sign up button click
    signUpButton.addEventListener("click", () => {
        alert("Sign Up clicked"); // Replace with your sign up logic
    });
});