console.log("LOGIN JS LOADED");

// Wait until DOM is fully loaded
document.addEventListener("DOMContentLoaded", () => {

    const form = document.querySelector("form");

    const emailInput = document.getElementById("emailInput");
    const passwordInput = document.getElementById("passwordInput");

    const emailError = document.getElementById("emailError");
    const passwordError = document.getElementById("passwordError");

    if (!form) {
        console.error("Form not found!");
        return;
    }

    form.addEventListener("submit", async function (e) {

        e.preventDefault();

        const email = emailInput.value.trim();
        const password = passwordInput.value.trim();

        emailError.textContent = "";
        passwordError.textContent = "";

        if (!email) {
            emailError.textContent = "Email is required";
            return;
        }

        if (!password) {
            passwordError.textContent = "Password is required";
            return;
        }

        try {

            const response = await fetch("/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    email: email,
                    password: password
                })
            });

            if (!response.ok) {
                const errorText = await response.text();
                passwordError.textContent = errorText;
                return;
            }

            const data = await response.json();

            localStorage.setItem("token", data.token);
            localStorage.setItem("userName", data.name);

            window.location.href = "main.html";

        } catch (error) {
            console.error("Login failed:", error);
            passwordError.textContent = "Something went wrong.";
        }

    });

});