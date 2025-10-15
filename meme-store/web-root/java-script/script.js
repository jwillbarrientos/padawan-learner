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

document.addEventListener("DOMContentLoaded", () => {
    const setCookieBtn = document.getElementById("setCookie");
    setCookieBtn.addEventListener("click", async () => {
        try {
            const response = await fetch("/setcookie");
            if (!response.ok) {
              throw new Error("Failed to set cookie");
            }
            alert("✅ Cookie set! (Check devtools -> Application -> Cookies)");
            console.log("✅ Cookie set! (Check devtools -> Application -> Cookies)");
        } catch (err) {
            console.error("Error setting cookie:", err);
        }
    });
});

//document.addEventListener("DOMContentLoaded", () => {
//    const readCookieBtn = document.getElementById("readCookies");
//    readCookieBtn.addEventListener("click", async () => {
//        try {
//            const response = await fetch("/readcookies");
//
//            if (!response.ok) {
//                throw new Error("Network response was not ok");
//            }
//
//            const data = await response.text();
//
//            console.log("🍪 Cookies from server:", data);
//            alert("🍪 Cookies from server:\n" + data);
//        } catch (err) {
//             console.error("Error reading cookies:", err);
//        }
//    });
//});

document.addEventListener("DOMContentLoaded", () => {
    const readCookieBtn = document.getElementById("readCookies");
    readCookieBtn.addEventListener("click", () => {
        if (document.cookie) {
            alert("🍪 Current cookies:\n" + document.cookie);
            console.log("🍪 Current cookies:\n" + document.cookie);
        } else {
            alert("❌ No cookies found.");
            console.log("❌ No cookies found.");
        }
    });
});

document.addEventListener("DOMContentLoaded", () => {
    const deleteCookieBtn = document.getElementById("deleteCookies");
    deleteCookieBtn.addEventListener("click", async () => {
        try {
            const response = await fetch("/deletecookies");
            if (!response.ok) {
                throw new Error("Failed to delete cookie");
            }
            alert("🗑️ Cookie deleted!");
            console.log("🗑️ Cookie deleted!");
        } catch (err) {
            console.error("Error deleting cookie:", err);
        }
    });
});