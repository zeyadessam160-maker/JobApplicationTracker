// ================= AUTH CHECK =================
const token = localStorage.getItem("token");

if (!token) {
    window.location.href = "login.html";
}


// ================= NAVIGATION =================
function goBack() {
    window.location.href = "main.html";
}


// ================= CREATE APPLICATION =================
const form = document.getElementById("createForm");

if (form) {

    form.addEventListener("submit", async function (e) {

        e.preventDefault();

        const company = document.getElementById("companyInput").value.trim();
        const position = document.getElementById("positionInput").value.trim();
        const status = document.getElementById("statusInput").value;
        const date = document.getElementById("dateInput").value;

        if (!company || !position || !date) {
            alert("Please fill all required fields.");
            return;
        }

        const newApp = {
            companyName: company,
            position: position,
            status: status,
            appliedDate: date
        };

        try {

            const response = await fetch("/applications", {

                method: "POST",

                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + token
                },

                body: JSON.stringify(newApp)

            });

            if (!response.ok) {
                const errorText = await response.text();
                console.error("Backend error:", errorText);
                alert("Error: " + errorText);
                return;
            }

            const data = await response.json();
            console.log("Application saved:", data);

            window.location.href = "main.html";

        } catch (error) {
            console.error("Error saving application:", error);
            alert("Failed to save application.");
        }

    });

}