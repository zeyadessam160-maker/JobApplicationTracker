const currentUser = JSON.parse(localStorage.getItem("currentUser"));
const userIndex = currentUser.index;
const users = JSON.parse(localStorage.getItem("users"));



// const name = localStorage.getItem("currentUser");
document.getElementById("greeting").textContent = currentUser.name
  ? `Hello, ${currentUser.name}!`
  : "Hello!";

function logout() {
  localStorage.removeItem("currentUser");
  window.location.href = "signIn.html"; // or the appropriate login page
}

function deleteAccount() {
  // Get users array
  let users = JSON.parse(localStorage.getItem("users")) || [];

  // Remove user at the specified index
  users.splice(currentUser.index, 1);

  // Save updated users array
  localStorage.setItem("users", JSON.stringify(users));

  // Remove currentUser and redirect
  localStorage.removeItem("currentUser");
  window.location.href = "signIn.html";
}

function changePassword() {
  document.querySelector(".changePassword").classList.remove("d-none");
  document.querySelector(".container").classList.add("d-none");

  // Only add the event listener once
  const form = document.getElementById("changePasswordForm");
  if (!form.dataset.listenerAdded) {
    form.addEventListener("submit", function (e) {
      e.preventDefault();

      const oldPassword = document.querySelector(".o-pass").value;
      const newPassword = document.querySelector(".n-pass").value;
      const repeatNewPassword = document.querySelector(".r-n-pass").value;

      if (newPassword !== repeatNewPassword) {
        alert("New passwords do not match.");
        return;
      }

      if (users[userIndex].password === oldPassword) {
        users[userIndex].password = newPassword;
        localStorage.setItem("users", JSON.stringify(users));
        alert("Password changed successfully!");
        document.querySelector(".changePassword").classList.add("d-none");
        document.querySelector(".container").classList.remove("d-none");
        document.getElementById("changePasswordForm").reset();
      } else {
        alert("Old password is incorrect.");
      }
    });
    form.dataset.listenerAdded = "true";
  }
}

function closeChangePassword() {
  document.querySelector(".changePassword").classList.add("d-none");
  document.querySelector(".container").classList.remove("d-none");
  document.getElementById("changePasswordForm").reset();
}