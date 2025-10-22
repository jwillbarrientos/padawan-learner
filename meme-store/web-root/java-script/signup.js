document.addEventListener("DOMContentLoaded", () => {
    const signUpBtn = document.getElementById("signUpBtn");
    signUpBtn.addEventListener("click", async () => {
        const params = new URLSearchParams();
        params.append("email", document.getElementById("email").value);
        params.append("password", document.getElementById("password").value)
        const response = await fetch(`/public/signup?${params}`);
        console.log("Request sent to: " + response);
        if (response.status === 200) {
            window.location.href = '/app/welcome-page.html';
        } else {
            alert('Sign up failed');
        }
    });
});