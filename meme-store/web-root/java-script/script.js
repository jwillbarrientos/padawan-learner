document.addEventListener("DOMContentLoaded", () => {
  const button = document.getElementById("colorButton");

  button.addEventListener("click", () => {
    const randomColor = "#" + Math.floor(Math.random() * 16777215).toString(16);
    document.body.style.backgroundColor = randomColor;
  });
});

document.addEventListener("DOMContentLoaded", () => {
  const button = document.getElementById("defaultColor");

  button.addEventListener("click", () => {
    document.body.style.backgroundColor = "#FFFFFF";
  });
});

document.addEventListener("DOMContentLoaded", () => {
  const button = document.getElementById("fetchButton");
  const container = document.getElementById("dataContainer");

  button.addEventListener("click", async () => {
    try {
      var response = await fetch("/getdate");

      if (!response.ok) {
        throw new Error("Network response was not ok");
      }

      var data = await response.text();

      // Create a new div element
      const newDiv = document.createElement("div");
      newDiv.textContent = `Server time: ${data}`;

      // Add it to the page
      container.appendChild(newDiv);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  });
});