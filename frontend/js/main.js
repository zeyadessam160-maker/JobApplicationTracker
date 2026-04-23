let selectedId = null;
let applications = [];


document.getElementById("welcomeMessage").textContent =
    "Welcome back, Ahmed Darwish!";

const grid = document.getElementById("applicationsGrid");
const overlay = document.getElementById("detailsOverlay");
const closeBtn = document.getElementById("closeDetails");

const confirmOverlay = document.getElementById("confirmOverlay");
const confirmYes = document.getElementById("confirmYes");
const confirmNo = document.getElementById("confirmNo");

const logoutBtn = document.getElementById("logoutBtn");
const logoutOverlay = document.getElementById("logoutOverlay");
const logoutYes = document.getElementById("logoutYes");
const logoutNo = document.getElementById("logoutNo");



function formatDateForDisplay(isoDate) {
    const [year, month, day] = isoDate.split("-");
    return `${day}/${month}/${year}`;
}


// ==========================
// LOAD APPLICATIONS FROM BACKEND
// ==========================
async function loadApplications() {

    try {

        const response = await fetch("http://localhost:8080/applications");
        applications = await response.json();

        renderCards();

    } catch (error) {
        console.error("Error loading applications:", error);
    }
}


// ==========================
// RENDER APPLICATION CARDS
// ==========================
function renderCards() {

    document.getElementById("applicationsCount").textContent =
        `You have ${applications.length} job applications`;

    grid.innerHTML = "";

    if (applications.length === 0) {
        return;
    }

    applications.forEach((app) => {

        const card = document.createElement("div");
        card.classList.add("application-card");

        card.innerHTML = `
            <div class="card-logo">
                <i class="fa-regular fa-file-lines"></i>
            </div>

            <span class="status-badge status-${app.status.toLowerCase()}">
                ${app.status}
            </span>

            <div class="company-name">${app.companyName}</div>
            <div class="position">${app.position}</div>

            <div class="date-row">
                <i class="fa-regular fa-calendar"></i>
                ${formatDateForDisplay(app.appliedDate)}
            </div>
        `;

        card.addEventListener("click", () => openDetails(app));
        grid.appendChild(card);
    });
}


// ==========================
// OPEN DETAILS MODAL
// ==========================
function openDetails(app) {

    selectedId = app.id;

    document.getElementById("detailCompany").value = app.companyName;
    document.getElementById("detailPosition").value = app.position;
    document.getElementById("detailStatus").value = app.status;
    document.getElementById("detailDate").value = app.appliedDate;

    overlay.style.display = "flex";
}


// Close modal
closeBtn.addEventListener("click", () => {
    overlay.style.display = "none";
});

overlay.addEventListener("click", (e) => {
    if (e.target === overlay) {
        overlay.style.display = "none";
    }
});


// ==========================
// UPDATE APPLICATION
// ==========================
document.getElementById("updateBtn").addEventListener("click", async () => {

    if (!selectedId) return;

    const updatedApp = {
        companyName: document.getElementById("detailCompany").value,
        position: document.getElementById("detailPosition").value,
        status: document.getElementById("detailStatus").value,
        appliedDate: document.getElementById("detailDate").value
    };

    try {

        await fetch(`http://localhost:8080/applications/${selectedId}`, {

            method: "PUT",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(updatedApp)

        });

        overlay.style.display = "none";

        loadApplications();

    } catch (error) {
        console.error("Update failed:", error);
    }
});


// ==========================
// DELETE APPLICATION
// ==========================
document.getElementById("deleteBtn").addEventListener("click", () => {

    if (!selectedId) return;

    confirmOverlay.style.display = "flex";
});


confirmNo.addEventListener("click", () => {
    confirmOverlay.style.display = "none";
});


confirmYes.addEventListener("click", async () => {

    if (!selectedId) return;

    try {

        await fetch(`http://localhost:8080/applications/${selectedId}`, {
            method: "DELETE"
        });

        confirmOverlay.style.display = "none";
        overlay.style.display = "none";

        loadApplications();

    } catch (error) {
        console.error("Delete failed:", error);
    }

});



logoutBtn.addEventListener("click", () => {
    logoutOverlay.style.display = "flex";
});

logoutNo.addEventListener("click", () => {
    logoutOverlay.style.display = "none";
});

logoutYes.addEventListener("click", () => {

    localStorage.removeItem("user");

    window.location.href = "login.html";
});



const newAppBtn = document.getElementById("newApplicationBtn");

if (newAppBtn) {
    newAppBtn.addEventListener("click", () => {
        window.location.href = "../pages/newApp.html";
    });
}



loadApplications();