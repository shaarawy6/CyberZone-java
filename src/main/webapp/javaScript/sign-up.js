function updateEmailPlaceholder() {
    var emailInput = document.getElementById('email');
    var isStudentYes = document.getElementById('studentYes').checked;

    if (isStudentYes) {
        emailInput.placeholder = "xyz@student.etc.edu";
    } else {
        emailInput.placeholder = "xyz@gmail.com";
    }
}
function validateForm() {
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;
    var phoneNumber = document.getElementById("phoneNumber").value;

    // Check if any field is empty (browser required attribute also handles this)
    var inputs = document.querySelectorAll(".input-box input");
    for (var input of inputs) {
        if (input.value.trim() === "") {
            alert("All fields must be filled out");
            return false;
        }
    }

    // Check if passwords match and are at least 8 characters long
    if (password !== confirmPassword) {
        alert("Passwords do not match.");
        return false;
    }
    if (password.length < 8) {
        alert("Password must be at least 8 characters long.");
        return false;
    }

    // Validate phone number
    var phoneRegex = /^\+[\d\s]+$/;
    if (!phoneNumber.match(phoneRegex) || isNaN(phoneNumber.replace(/\+/g, ''))) {
        alert("Invalid phone number. It must start with '+' then your country code and be numeric.");
        return false;
    }

    return true; // return true if all validations pass
}