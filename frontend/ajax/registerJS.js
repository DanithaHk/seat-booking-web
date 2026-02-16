document.addEventListener("DOMContentLoaded", () => {
console.log("register");
  const registerForm = document.getElementById("registerForm");

  registerForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    console.log("register");
    
    const userData = {
      fullName: document.getElementById("name").value,
      email: document.getElementById("email").value,
      phone: document.getElementById("phone").value,
      password: document.getElementById("password").value,
      role: "USER" // role from frontend
    };

    console.log(userData);

    try {
      const response = await fetch("http://localhost:8080/api/auth/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(userData)
      });

      const data = await response.json();

      if (data.success) {
        alert("Registration Successful!");
        console.log("JWT Token:", data.token);
        window.location.href = "/frontend/login.html";
      } else {
        alert("Error: " + data.message);
      }

    } catch (error) {
      console.error("Registration failed:", error);
      alert("Server error");
    }
  });

});
