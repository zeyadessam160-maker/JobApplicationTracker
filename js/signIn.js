document.querySelector('form').addEventListener('submit', function(e) {
    e.preventDefault();

    const email = document.querySelector('input[type="email"]').value.trim();
    const password = document.querySelector('input[type="password"]').value;

    const users = JSON.parse(localStorage.getItem('users')) || [];
    const user = users.find(user => user.email === email && user.password === password);
    // Check if user exists with matching email and password


    if (user && user.email === email && user.password === password) {
        // Save name for greeting
        localStorage.setItem('currentUser', JSON.stringify({ name: user.name, index: users.indexOf(user) }));
        window.location.href = 'welcome.html';
    } else {
        alert('Invalid email or password.');
    }
});

const emailInput = document.querySelector('#username');
const passwordInput = document.querySelector('#password');
const btnContainer = document.querySelector('.btn-container');
const btn = document.querySelector('#login-btn');
const msg = document.querySelector('.msg');

btn.disabled = true;

function shiftButton() {
    const isEmpty = emailInput.value === '' || passwordInput.value === '';
    if (!isEmpty) {
        btnContainer.classList.remove('shift-left', 'shift-top', 'shift-right', 'shift-bottom');
        // btnContainer.classList.add('no-shift');
        return;
    }

    const positions = ['shift-right',  'shift-left'];
    // btnContainer.classList.remove('no-shift');
    const currentPosition = positions.find(dir => btnContainer.classList.contains(dir));
    if (currentPosition) {
        btnContainer.classList.remove(currentPosition);
    }
    let nextPosition;
    if (!currentPosition) {
        nextPosition = positions[0];
    } else {
        nextPosition = positions[(positions.indexOf(currentPosition) + 1) % positions.length];
    }
    btnContainer.classList.add(nextPosition);
}

function showMsg() {
    const isEmpty = emailInput.value === '' || passwordInput.value === '';
    btn.disabled = isEmpty;
    if (!isEmpty) {
        btnContainer.classList.remove('shift-left', 'shift-right');
        msg.innerText = 'Great! Now you can proceed';
        msg.style.color = '#92ff92';    
    }
    else  {
        msg.style.color = 'rgb(218, 49, 49)';
        msg.innerText = 'Please fill the input fields before proceeding';
    }
}

btnContainer.addEventListener('mouseover', shiftButton);
document.querySelector('form').addEventListener('input', showMsg);
btn.addEventListener('touchstart', shiftButton);
showMsg();


