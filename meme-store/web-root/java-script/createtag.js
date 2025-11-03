
import { loadTags } from "./loadtags.js";

document.addEventListener("DOMContentLoaded", () => {
    const createTagBtn = document.getElementById("createTag");
    const tagList = document.getElementById("tagList");
    createTagBtn.addEventListener("click", async () => {
        // Ask for the tag name
        const tagName = prompt("Enter a name for the new tag:");
        if (!tagName) return;
        try {
            const response = await fetch(`/api/addtag?tagName=${encodeURIComponent(tagName)}`);
            const newTag = await response.json();
            if (!response.ok) {
                console.error(newTag);
                return;
            }
            await loadTags();

        } catch (err) {
            console.error("Error creating tag:", err);
        }
    });
});