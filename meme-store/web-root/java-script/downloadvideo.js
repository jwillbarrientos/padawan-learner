document.addEventListener("DOMContentLoaded", () => {
    const downloadBtn = document.getElementById("downloadBtn");
    const linkInput = document.getElementById("videoUrl");

    downloadBtn.addEventListener("click", async () => {
        const videoLink = linkInput.value.trim();
        if (!videoLink) {
            alert("Please enter a video link.");
            return;
        }

        try {
            const response = await fetch(`/api/downloadvideo?link=${encodeURIComponent(videoLink)}`);
            const result = await response.text(); // read server response message

            if (!response.ok) {
                console.error(result);
                alert("Error: " + result);
                return;
            }

            alert("Video download started!");
            linkInput.value = "";
        } catch (err) {
            console.error("Download error:", err);
            alert("Something went wrong!");
        }
    });
});