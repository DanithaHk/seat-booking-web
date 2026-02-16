document.addEventListener("DOMContentLoaded", function() {
    console.log("login page");

    const loginForm = document.getElementById("loginForm");
    const message = document.getElementById("message");

    loginForm.addEventListener("submit", function(e) {
        e.preventDefault();

        // Take values from the HTML input IDs
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;

        const payload = { email, password };

        fetch("http://localhost:8080/api/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        })
        .then(res => {
            if (!res.ok) throw new Error(`HTTP error! Status: ${res.status}`);
            return res.json();
        })
        .then(data => {
            console.log(data); // for debugging

            if (data.success) {
                message.textContent = data.message || "Login successful";
                message.classList.remove("text-red-500");
                message.classList.add("text-green-500");

                // Save JWT token
                if (data.token) localStorage.setItem("token", data.token);

                // Save user info if available
                const user = data.data;
                console.log(user);
                
                if (user) {
                    localStorage.setItem("user", JSON.stringify(user));
                    console.log("Logged in user:", user);

                    // Role-based redirect
                    if (user.role === "ADMIN") {
                        window.location.href = "/frontend/adminDashbord.html";
                    } else if (user.role === "USER") {
                        window.location.href = "index.html";
                    }
                }

                alert("Login successful");

            } else {
                message.textContent = data.message || "Login failed";
                message.classList.remove("text-green-500");
                message.classList.add("text-red-500");
                alert("Failed: " + (data.message || "Unknown error"));
            }
        })
        .catch(err => {
            console.error("Login error:", err);
            message.textContent = "Server error. Please try again later.";
            message.classList.add("text-red-500");
            alert("Something went wrong");
        });
    });
});
