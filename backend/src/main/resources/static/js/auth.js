// ================= PASSWORD TOGGLE =================
const authPasswordInput = document.getElementById("passwordInput");
const authTogglePassword = document.getElementById("togglePassword");

if (authPasswordInput && authTogglePassword) {
    authTogglePassword.addEventListener("click", function () {

        if (authPasswordInput.type === "password") {
            authPasswordInput.type = "text";
            authTogglePassword.classList.remove("fa-eye-slash");
            authTogglePassword.classList.add("fa-eye");
        } else {
            authPasswordInput.type = "password";
            authTogglePassword.classList.remove("fa-eye");
            authTogglePassword.classList.add("fa-eye-slash");
        }

    });
}


// ================= FORGOT PASSWORD MODAL =================
const authForgotLink = document.querySelector(".forgot-link");
const authModal = document.getElementById("resetModal");
const authCloseModal = document.getElementById("closeModal");

if (authForgotLink && authModal && authCloseModal) {

    authForgotLink.addEventListener("click", function (e) {
        e.preventDefault();
        authModal.classList.add("active");
    });

    authCloseModal.addEventListener("click", function () {
        authModal.classList.remove("active");
        resetModalForm();
    });

    authModal.addEventListener("click", function (e) {
        if (e.target === authModal) {
            authModal.classList.remove("active");
            resetModalForm();
        }
    });
}


// ================= EMAIL VALIDATION (MODAL ONLY) =================
const authEmailInput = document.getElementById("emailModal");
const authEmailError = document.getElementById("email-error");

if (authEmailInput && authEmailError) {

    function isValidEmail(email) {
        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailPattern.test(email);
    }

    authEmailInput.addEventListener("blur", function () {
        const emailValue = authEmailInput.value.trim();

        if (!isValidEmail(emailValue)) {
            authEmailError.textContent = "Invalid email format (example: name@example.com)";
            authEmailInput.classList.add("input-error");
        } else {
            authEmailError.textContent = "";
            authEmailInput.classList.remove("input-error");
        }
    });

    authEmailInput.addEventListener("input", function () {
        authEmailError.textContent = "";
        authEmailInput.classList.remove("input-error");
    });

    function resetModalForm() {
        authEmailInput.value = "";
        authEmailError.textContent = "";
        authEmailInput.classList.remove("input-error");
    }

}