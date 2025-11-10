document.addEventListener("DOMContentLoaded", () => {
    const deleteAccountBtn = document.getElementById("deleteAccountBtn");
    const userName = document.getElementById("userName");

    deleteAccountBtn.addEventListener("click", async() => {
        if (!confirm("Are you sure you want to delete your entire account and all videos/tags? This cannot be undone")) {
            return;
        }

        try {
            const email = userName.textContent;
            const response = await fetch(`/api/deleteaccount?email=${email}`);
            if (response.ok) {
                alert("Your account was deleted successfully");
                window.location.href = "/index.html";
            } else {
                const msg = await response.text();
                alert("Error deleting account: " + msg);
            }
        } catch (err) {
            console.error(err);
            alert("An error occurred while deleting your account");
        }
    });
});