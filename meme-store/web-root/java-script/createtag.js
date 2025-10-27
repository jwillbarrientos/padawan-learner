import { createEditButton } from "./edittag.js";
import { createDeleteButton } from "./deletetag.js";
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

            // const tagItem = document.createElement("div");
            //
            // const nameSpan = document.createElement("span");
            // nameSpan.textContent = newTag.name + " ";
            //
            // const editBtn = createEditButton(newTag.id, newTag.name, loadTags);
            // const deleteBtn = createDeleteButton(newTag.id, loadTags);
            //
            // tagItem.appendChild(nameSpan);
            // tagItem.appendChild(editBtn);
            // tagItem.appendChild(deleteBtn);
            //
            // tagList.appendChild(tagItem);
        } catch (err) {
            console.error("Error creating tag:", err);
        }
    });
});