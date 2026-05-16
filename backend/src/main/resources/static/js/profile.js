const token = localStorage.getItem("token");

if (!token) {
    window.location.href = "login.html";
}

let cropper;
let selectedImage = null;

const changeBtn = document.getElementById("changePhotoBtn");
const photoInput = document.getElementById("photoInput");
const profileImage = document.getElementById("profileImage");
const avatarLetters = document.getElementById("avatarLetters");
const imageToCrop = document.getElementById("imageToCrop");
const editor = document.getElementById("imageEditor");
const cropBtn = document.getElementById("cropBtn");

async function loadProfile() {
    try {
        const response = await fetch("/profile", {
            headers: {
                "Authorization": "Bearer " + token
            }
        });

        const data = await response.json();

        document.getElementById("nameInput").value = data.name;
        document.getElementById("emailInput").value = data.email;

        if (data.profileImage) {
            profileImage.src = data.profileImage;
            profileImage.style.display = "block";
            avatarLetters.style.display = "none";
        } else {
            avatarLetters.textContent =
                data.name.split(" ").map(n => n[0]).join("").toUpperCase();
        }

    } catch (err) {
        console.error("Failed to load profile:", err);
    }
}

loadProfile();

changeBtn.addEventListener("click", () => {
    photoInput.click();
});

photoInput.addEventListener("change", function () {

    const file = this.files[0];

    if(file){

        const reader = new FileReader();

        reader.onload = function(e){

            editor.style.display = "block";
            imageToCrop.src = e.target.result;

            if(cropper){
                cropper.destroy();
            }

            cropper = new Cropper(imageToCrop,{
                aspectRatio:1,
                viewMode:1
            });

        }

        reader.readAsDataURL(file);
    }

});

cropBtn.addEventListener("click", function(){

    const canvas = cropper.getCroppedCanvas({
        width:150,
        height:150
    });

    const croppedImage = canvas.toDataURL("image/jpeg", 0.7);

    selectedImage = croppedImage;

    profileImage.src = croppedImage;
    profileImage.style.display = "block";
    avatarLetters.style.display = "none";

    editor.style.display = "none";

});

const updateBtn = document.getElementById("updateProfileBtn");

updateBtn.addEventListener("click", async function () {

    const name = document.getElementById("nameInput").value;

    let imageToSend = selectedImage;

    if (!imageToSend && profileImage.src && profileImage.style.display !== "none") {
        imageToSend = profileImage.src;
    }

    try {
        const response = await fetch("/profile", {

            method: "PUT",

            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            },

            body: JSON.stringify({
                name: name,
                profileImage: imageToSend
            })

        });

        if (!response.ok) {
            alert("Failed to update profile");
            return;
        }

        const toast = new bootstrap.Toast(document.getElementById("successToast"));
        toast.show();

    } catch (err) {
        console.error("Update failed:", err);
    }

});

const changePasswordBtn = document.getElementById("changePasswordBtn");

changePasswordBtn.addEventListener("click", async function () {

    const newPassword = document.getElementById("newPassword").value;
    const confirmPassword = document.getElementById("confirmPassword").value;
    const error = document.getElementById("passwordError");

    const regex = /^(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{8,}$/;

    if (!regex.test(newPassword)) {

        error.innerText =
            "Password must contain at least 8 characters, one capital letter and one special character.";

        return;
    }

    if (newPassword !== confirmPassword) {

        error.innerText = "Passwords do not match.";

        return;
    }

    error.innerText = "";

    try {

        const response = await fetch("/auth/change-password", {

            method: "POST",

            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            },

            body: JSON.stringify({
                oldPassword: document.getElementById("oldPassword").value,
                newPassword: newPassword
            })

        });

        if (!response.ok) {
            const errorText = await response.text();
            error.innerText = errorText;
            return;
        }

        const modalElement = document.getElementById("resetPasswordModal");
        const modalInstance = bootstrap.Modal.getInstance(modalElement);
        modalInstance.hide();

        const toastElement = document.getElementById("passwordToast");
        const toast = new bootstrap.Toast(toastElement);
        toast.show();

    } catch (err) {
        console.error("Change password failed:", err);
        error.innerText = "Something went wrong.";
    }

});