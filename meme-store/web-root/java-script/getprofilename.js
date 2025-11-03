document.addEventListener("DOMContentLoaded", async () => {
    try {
        var response = await fetch("/api/getprofilename");
        if (!response.ok) {
            throw new Error("Failed to get profile name");
        }
        if (response.status === 200) {
            document.getElementById("userName").textContent = await response.text()
        }
    } catch (err) {
        console.error("Error getting name:", err);
    }
});