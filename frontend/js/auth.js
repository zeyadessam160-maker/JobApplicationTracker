//toggle password visibility
const passwordInput = document.getElementById("passwordInput");
const togglePassword = document.getElementById("togglePassword");

togglePassword.addEventListener("click", function () {

    // If password is hidden
    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        togglePassword.classList.remove("fa-eye-slash");
        togglePassword.classList.add("fa-eye");
    } 
    else {
        passwordInput.type = "password";
        togglePassword.classList.remove("fa-eye");
        togglePassword.classList.add("fa-eye-slash");
    }

});

//forgot password modal
const forgotLink = document.querySelector(".forgot-link");
const modal = document.getElementById("resetModal");
const closeModal = document.getElementById("closeModal");

// Open modal
forgotLink.addEventListener("click", function (e) {
    e.preventDefault();
    modal.classList.add("active");
});

// Close when clicking X
closeModal.addEventListener("click", function () {
    modal.classList.remove("active");
    resetModalForm();
});

// Close when clicking outside
modal.addEventListener("click", function (e) {
    if (e.target === modal) {
        modal.classList.remove("active");
        resetModalForm();
    }
});


//email validation in forgot password modal
const emailInput = document.getElementById("emailModal");
const emailError = document.getElementById("email-error");

function isValidEmail(email) {
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailPattern.test(email);
}

emailInput.addEventListener("blur", function () {
    const emailValue = emailInput.value.trim();

    if (!isValidEmail(emailValue)) {
        emailError.textContent = "Invalid email format (example: name@example.com)";
        emailInput.classList.add("input-error");
    } else {
        emailError.textContent = "";
        emailInput.classList.remove("input-error");
    }
});

// Clear error on input
emailInput.addEventListener("input", function () {
    emailError.textContent = "";
    emailInput.classList.remove("input-error");
});

// Reset form when modal is closed
function resetModalForm() {
    emailInput.value = "";
    emailError.textContent = "";
    emailInput.classList.remove("input-error");
}