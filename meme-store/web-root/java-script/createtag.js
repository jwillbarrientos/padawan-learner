document.addEventListener("DOMContentLoaded", () => {
    const createTagBtn = document.getElementById("createTag");
    const tagList = document.getElementById("tagList");
    createTagBtn.addEventListener("click", async () => {
        // Ask for the tag name
        const tagName = prompt("Enter a name for the new tag:");
        if (!tagName) return;
        try {
            const response = await fetch(`/api/addtag?tagName=${encodeURIComponent(tagName)}`);
            const serverText = await response.text();
            if (!response.ok) {
                console.error(serverText);
                return;
            }
            // ✅ Create tag row in HTML
            const tagItem = document.createElement("div");
            tagItem.textContent = tagName + " ";
            // Edit button
            const editBtn = document.createElement("button");
            editBtn.textContent = "Edit";
            // (later we will add update functionality here)
            // Delete button
            const deleteBtn = document.createElement("button");
            deleteBtn.textContent = "Delete";
            // (later we will add delete functionality here)
            tagItem.appendChild(editBtn);
            tagItem.appendChild(deleteBtn);
            tagList.appendChild(tagItem);
        } catch (err) {
            console.error("Error creating tag:", err);
        }
    });
});