document.getElementById('signupForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const name = document.getElementById('name').value.trim();
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value;

    // Simple validation
    if (name.length < 3) {
        alert('Name must be at least 3 characters.');
        return;
    }
    if (!/^\S+@\S+\.\S+$/.test(email)) {
        alert('Please enter a valid email.');
        return;
    }

    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/;
    if (!passwordRegex.test(password)) {
        alert('Password must be at least 8 characters and include uppercase, lowercase, number, and special character.');
        return;
    }

    // Get existing users or initialize empty array
    let users = JSON.parse(localStorage.getItem('users')) || [];

    // Check if email already exists
    if (users.some(user => user.email === email)) {
        alert('Email already registered. Please use another email or sign in.');
        return;
    }

    // Add new user
    users.push({ name, email, password });

    // Save back to localStorage
    localStorage.setItem('users', JSON.stringify(users));

    alert('Sign up successful! Please sign in.');
    window.location.href = 'signIn.html';
});