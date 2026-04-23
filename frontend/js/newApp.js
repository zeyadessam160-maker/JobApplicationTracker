
function goBack() {
    window.location.href = "../pages/main.html";
}

document.getElementById("createForm").addEventListener("submit", async function (e) {

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
        const response = await fetch("http://localhost:8080/applications", {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(newApp)

        });

        if (!response.ok) {
            const errorText = await response.text();
            console.error("Backend error:", errorText);
            alert(errorText);
            return;
        }

        const data = await response.json();
        console.log("Application saved:", data);

        window.location.href = "../pages/main.html";

    } catch (error) {

        console.error("Error saving application:", error);

        alert("Failed to save application.");
    }

});