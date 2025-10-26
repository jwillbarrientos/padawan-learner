document.addEventListener("DOMContentLoaded", async () => {
    try {
        var response = await fetch("/api/loadtags");
        if (!response.ok) {
            throw new Error("Failed to load tags");
        } else {
            const text = await response.text();
            const tagNames = text.trim().split("\n"); // Convert response into array

            const tagList = document.getElementById("tagList");
            tagList.innerHTML = ""; // Clear existing items

            tagNames.forEach(name => {
                if (!name) return;
                const tagItem = document.createElement("div");
                tagItem.textContent = name + " ";

                const editBtn = document.createElement("button");
                editBtn.textContent = "Edit";

                const deleteBtn = document.createElement("button");
                deleteBtn.textContent = "Delete";

                tagItem.appendChild(editBtn);
                tagItem.appendChild(deleteBtn);

                tagList.appendChild(tagItem);
            });
        }
    } catch (err) {
        console.error("Error getting tags:", err);
    }
});