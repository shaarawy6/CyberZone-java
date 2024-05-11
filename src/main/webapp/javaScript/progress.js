document.addEventListener('DOMContentLoaded', function() {
    fetch('../javaScript/check_login_status.jsp')  
    .then(response => response.json())
    .then(data => {
        const registerDiv = document.querySelector('.register');
        if (data.isLoggedIn) {
            // User is logged in
            registerDiv.innerHTML = '<a onclick="signOut()" class="login"><button>Sign Out</button></a>';

            // Other code remains unchanged
            const progressBar = document.createElement('a');
            progressBar.textContent = data.progress + '%';
            progressBar.style.background = '#ec2e2083';
            progressBar.style.color = 'white';
            progressBar.style.textAlign = 'center';
            progressBar.style.lineHeight = '30px';
            progressBar.style.height = '30px';
            progressBar.style.borderRadius = '10px';
            progressBar.style.boxShadow = '0 0 3px #ffffff41';
            progressBar.style.transition = '1s';
            progressBar.style.marginTop = '8px';
            progressBar.style.padding = '5px';

            registerDiv.prepend(progressBar);
        } else {
            // User is not logged in
            registerDiv.innerHTML = '<a href="/html/sign-in.html" class="login"><button>Login</button></a><a href="/html/sign-up.html" class="signup"><button>Sign Up</button></a>';
        }
    })
    .catch(error => console.error('Error:', error));
});

function signOut() {
    fetch('../javaScript/sign_out.jsp')
    .then(() => {
        window.location.reload();
    });
}
