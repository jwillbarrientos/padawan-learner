import { createDeleteButton } from "./deletetag.js";
import { createEditButton } from "./edittag.js";
export async function loadTags() {
    try {
        var response = await fetch("/api/loadtags");
        if (!response.ok) {
            throw new Error("Failed to load tags");
        }
        const tags = await response.json();

        const tagList = document.getElementById("tagList");
        tagList.innerHTML = "";

        tags.forEach(tag => {
            if (!tag) return;
            const tagItem = document.createElement("div");

            const nameSpan = document.createElement("span");
            nameSpan.textContent = tag.name + " ";

            const editBtn = createEditButton(tag.id, tag.name, loadTags);
            const deleteBtn = createDeleteButton(tag.id, loadTags);

            tagItem.appendChild(nameSpan);
            tagItem.appendChild(editBtn);
            tagItem.appendChild(deleteBtn);

            tagList.appendChild(tagItem);
        });
    } catch (err) {
        console.error("Error getting tags:", err);
    }
}

document.addEventListener("DOMContentLoaded", loadTags);