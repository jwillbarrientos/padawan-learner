export function createDeleteButton(tagId, reloadCallback) {
    const deleteBtn = document.createElement("button");
    deleteBtn.textContent = "Delete";
    deleteBtn.dataset.tagId = tagId;

    deleteBtn.addEventListener("click", async () => {
        try {
            const response = await fetch(`/api/deletetag?id=${tagId}`);
            if (!response.ok) {
                alert("Error deleting tag");
                return;
            }
            reloadCallback(); // refresh tags list after delete
        } catch (err) {
            console.error("Error deleting tag:", err);
        }
    });
    return deleteBtn;
}