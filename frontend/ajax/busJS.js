// Define API base URL
const API_URL = "http://localhost:8080/api/admin";

document.addEventListener("DOMContentLoaded", function () {
    loadBuses();
    saveBus();
    handleEditButtons(); // handle edit button clicks for update modal
    handleStatusModalClose();
});

// Load all buses
function loadBuses() {
    const token = localStorage.getItem("token");

    fetch(`${API_URL}/buses`, {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token,
            "Content-Type": "application/json"
        }
    })
    .then(response => response.json())
    .then(response => {
        if (response.success) {
            const tbody = document.getElementById("busTableBody");
            tbody.innerHTML = "";

            const buses = response.data;

            buses.forEach(bus => {
                const row = `
                    <tr class="hover:bg-gray-50 dark:hover:bg-[#253241] transition-colors">
                        <td class="px-6 py-4 font-bold text-primary">#BUS-${bus.id}</td>
                        <td class="px-6 py-4">
                            <span class="font-mono font-bold px-2 py-1 rounded bg-gray-100 dark:bg-gray-700">
                                ${bus.busNumber}
                            </span>
                        </td>
                        <td class="px-6 py-4">
                            <span class="font-medium">${bus.busModel}</span>
                        </td>
                        <td class="px-6 py-4 text-center font-bold">${bus.totalSeats}</td>
                        <td class="px-6 py-4 text-sm">${bus.fuelType}</td>
                        <td class="px-6 py-4">
                            <span class="px-3 py-1 rounded-full text-xs font-bold bg-green-100 text-green-700">
                                ${bus.status}
                            </span>
                        </td>
                        <td class="px-6 py-4 text-right flex gap-2 justify-end">
                            <button class="editBtn p-1 rounded hover:bg-gray-200 dark:hover:bg-gray-700 transition-colors text-gray-500"
                                data-busnumber="${bus.busNumber}" data-currentstatus="${bus.status}">
                                <span class="material-symbols-outlined">edit</span>
                            </button>
                            <button class="p-1 rounded hover:bg-gray-200 dark:hover:bg-gray-700 transition-colors text-gray-500">
                                <span class="material-symbols-outlined">more_vert</span>
                            </button>
                        </td>
                    </tr>
                `;
                tbody.innerHTML += row;
            });

            handleEditButtons(); // reattach edit buttons after loading table

        } else {
            alert(response.message);
        }
    })
    .catch(error => console.error("Error loading buses:", error));
}

// Save new bus
function saveBus() {
    const busForm = document.getElementById("busForm");

    busForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        const token = localStorage.getItem("token");

        const busData = {
            busNumber: document.getElementById("busNumber").value,
            busModel: document.getElementById("busModel").value,
            fuelType: document.getElementById("fuelType").value,
            busType: document.getElementById("busType").value,
            totalSeats: parseInt(document.getElementById("totalSeats").value),
            status: "ACTIVE"
        };

        try {
            const response = await fetch(`${API_URL}/buses`, {
                method: "POST",
                headers: {
                    "Authorization": "Bearer " + token,
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(busData)
            });

            const data = await response.json();

            if (data.success) {
                alert("Bus Added Successfully!");
                location.reload();
            } else {
                alert("Error: " + data.message);
            }
        } catch (error) {
            console.error("Error:", error);
            alert("Server Error");
        }
    });
}

// Update bus status
function updateBusStatus(busNumber, newStatus) {
    const token = localStorage.getItem("token");
    fetch(`${API_URL}/buses/${busNumber}/status`, {
        method: "PUT",
       headers: {
                    "Authorization": "Bearer " + token,
                    "Content-Type": "application/json"
                },
        body: JSON.stringify({ status: newStatus })
    })
    .then(res => res.json())
    .then(data => {
        if (data.success) {
            
            loadBuses(); // reload table
            closeStatusModal();
        } else {
            alert(data.message);
        }
    })
    .catch(err => console.error(err));
}

// Handle edit button clicks to open status modal
function handleEditButtons() {
    const editButtons = document.querySelectorAll(".editBtn");
    editButtons.forEach(btn => {
        btn.addEventListener("click", () => {
            const busNumber = btn.dataset.busnumber;
            const currentStatus = btn.dataset.currentstatus;

            document.getElementById("modalBusNumber").value = busNumber;
            document.getElementById("modalStatus").value = currentStatus;

            document.getElementById("updateStatusModal").classList.remove("hidden");
        });
    });

    // Attach save status button
    document.getElementById("saveStatusBtn").addEventListener("click", () => {
        const busNumber = document.getElementById("modalBusNumber").value;
        const newStatus = document.getElementById("modalStatus").value;
        updateBusStatus(busNumber, newStatus);
    });
}

// Close status modal
function handleStatusModalClose() {
    const modal = document.getElementById("updateStatusModal");
    const closeBtn = document.getElementById("closeStatusModal");

    closeBtn.addEventListener("click", closeStatusModal);
    modal.addEventListener("click", e => { if (e.target === modal) closeStatusModal(); });
}

function closeStatusModal() {
    document.getElementById("updateStatusModal").classList.add("hidden");
}
