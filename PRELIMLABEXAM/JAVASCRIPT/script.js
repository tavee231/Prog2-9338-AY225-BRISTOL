// Programmer: [Your Name] [Your ID]

// REQUIREMENT: Hardcode CSV as multi-line string (Automatic Data Source)
const mockDataString = `ID,Name,Grade
11104322,Moses Buer,91
72401064,Valery Lester,12
1.25E+08,Durant Cahalan,64
1.23E+08,Ahmad Luetchford,38
72413751,Sergio Oosthout,52
3.22E+08,Alli Wintersgill,100
31313562,Flint Brimacombe,83
1.10E+07,Petey Lorey,79`;

let students = []; // Main Array of Objects

/**
 * AUTOMATIC METHOD: 
 * Parses the hardcoded string into the students array on load.
 */
function autoLoadData() {
    const lines = mockDataString.trim().split("\n");
    // Skip index 0 (the header)
    for (let i = 1; i < lines.length; i++) {
        const parts = lines[i].split(",");
        students.push({
            id: parts[0],
            name: parts[1],
            grade: parseFloat(parts[2])
        });
    }
    render();
}

/**
 * MANUAL METHOD: 
 * Adds a student from the input form.
 */
function addStudentManual() {
    const id = document.getElementById("idInput").value;
    const name = document.getElementById("nameInput").value;
    const grade = document.getElementById("gradeInput").value;

    if (id && name && grade) {
        // REQUIREMENT: Push new object to array
        students.push({
            id: id,
            name: name,
            grade: parseFloat(grade)
        });
        
        render(); // Update UI
        
        // Clear fields
        document.getElementById("idInput").value = "";
        document.getElementById("nameInput").value = "";
        document.getElementById("gradeInput").value = "";
    } else {
        alert("Please fill in all Excel cells!");
    }
}

/**
 * DELETE METHOD:
 * Removes specific student from the array.
 */
function deleteStudent(index) {
    students.splice(index, 1);
    render();
}

/**
 * READ/DISPLAY METHOD:
 * REQUIREMENT: render() function using Template Literals (backticks).
 */
function render() {
    const tbody = document.querySelector("#studentTable tbody");
    tbody.innerHTML = ""; // Clear for re-population

    students.forEach((student, index) => {
        const isPass = student.grade >= 50;
        
        // Using Template Literals to generate HTML rows
        const rowHTML = `
            <tr>
                <td>${index + 1}</td>
                <td>${student.id}</td>
                <td>${student.name}</td>
                <td>${student.grade}%</td>
                <td class="${isPass ? 'pass' : 'fail'}">${isPass ? 'PASS' : 'FAIL'}</td>
                <td>
                    <button class="del-btn" onclick="deleteStudent(${index})">Delete</button>
                </td>
            </tr>
        `;
        tbody.innerHTML += rowHTML;
    });
}

// Start the automatic loading process when the page opens
window.onload = autoLoadData;