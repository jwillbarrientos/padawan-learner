document.addEventListener("DOMContentLoaded", () => {
    const changePasswordBtn = document.getElementById("changePasswordBtn");
    const userName = document.getElementById("userName");

    changePasswordBtn.addEventListener("click", async () => {
        const newPassword = prompt("Enter your new password:");
        if (!newPassword) return;

        try {
            const email = userName.textContent;
            const response = await fetch(`/api/changepassword?email=${email}&newpassword=${encodeURIComponent(newPassword)}`)
            if (response.ok) {
                alert("Password updated successfully!");
                window.location.href = "/index.html";
            } else {
                const msg = await response.text();
                alert("Error changing password:" + msg);
            }
        } catch (err) {
            console.error(err);
            alert("An error occurred while changing password");
        }
    });
});