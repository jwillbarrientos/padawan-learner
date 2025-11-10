import { myFetch } from "./myfetch.js";

export async function loadVideos() {
    try {
        const response = await myFetch("/api/loadvideos");
        if (!response.ok) {
            throw new Error("Failed to load videos");
        }

        const videos = await response.json();
        const videoList = document.getElementById("videoContainer");
        videoList.innerHTML = "";

        videos.forEach(video => {
            if (!video) return;

            const videoItem = document.createElement("div");
            videoItem.classList.add("video-item");

            const nameSpan = document.createElement("span");
            nameSpan.textContent = video.name + " ";

            const videoPlayer = document.createElement("video");
            videoPlayer.controls = true;

            // The browser will request the video from this URL
            videoPlayer.src = "/api/streamingvideos?id=" + encodeURIComponent(video.id);

            videoItem.appendChild(nameSpan);
            videoItem.appendChild(videoPlayer);
            videoList.appendChild(videoItem);
        });

    } catch (err) {
        console.error("Error loading videos:", err);
    }
}

document.addEventListener("DOMContentLoaded", loadVideos);