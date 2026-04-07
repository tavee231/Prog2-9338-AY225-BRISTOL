function calculatePrelim() {
    // 1. Get Elements
    const screen = document.getElementById('display-screen');
    const attStr = document.getElementById('attendance').value;
    const l1Str = document.getElementById('lab1').value;
    const l2Str = document.getElementById('lab2').value;
    const l3Str = document.getElementById('lab3').value;

    // 2. Validate Empty Inputs
    if (attStr === "" || l1Str === "" || l2Str === "" || l3Str === "") {
        screen.innerHTML = "ERROR:\nMissing input values.\nPlease fill all fields.";
        return;
    }

    // 3. Parse Numbers
    const attendance = parseFloat(attStr);
    const lab1 = parseFloat(l1Str);
    const lab2 = parseFloat(l2Str);
    const lab3 = parseFloat(l3Str);

    // 4. Compute Logic
    // Lab Average
    const labAverage = (lab1 + lab2 + lab3) / 3.0;

    // Class Standing (40% Att, 60% Lab Avg)
    const classStanding = (attendance * 0.40) + (labAverage * 0.60);

    // Required Exams: (Target - (CS * 0.70)) / 0.30
    let reqPass = (75 - (classStanding * 0.70)) / 0.30;
    let reqExc = (100 - (classStanding * 0.70)) / 0.30;

    // 5. Logic Checks & Clamping (The "Don't surpass 100" rule)
    let passRemark = "";
    
    // Check Pass Requirement
    if (reqPass > 100) {
        reqPass = 100; // Cap at 100
        passRemark = "\n(Status: IMPOSSIBLE)";
        screen.style.color = "#a00"; // Red text
    } else if (reqPass <= 0) {
        reqPass = 0; // Floor at 0
        passRemark = "\n(Status: PASSED)";
        screen.style.color = "#004400"; // Green text
    } else {
        screen.style.color = "#222"; // Black text
    }

    // Check Excellent Requirement
    if (reqExc > 100) {
        reqExc = 100; // Cap at 100
    } else if (reqExc <= 0) {
        reqExc = 0;
    }

    // 6. Format Output with % symbol
    let resultText = "--- RESULTS ---\n";
    resultText += `Class Standing: ${classStanding.toFixed(2)}\n`;
    resultText += `Lab Average:    ${labAverage.toFixed(2)}\n`;
    resultText += "----------------\n";
    resultText += `To Pass (75):   ${reqPass.toFixed(2)}%${passRemark}\n`;
    resultText += `To Excel (100): ${reqExc.toFixed(2)}%\n`;
    resultText += "----------------";

    // 7. Update Screen
    screen.innerHTML = resultText;
}

function clearCalculator() {
    // Clear inputs
    document.getElementById('attendance').value = "";
    document.getElementById('lab1').value = "";
    document.getElementById('lab2').value = "";
    document.getElementById('lab3').value = "";
    
    // Reset screen
    const screen = document.getElementById('display-screen');
    screen.innerHTML = '<span class="placeholder">Ready for calculation...<br>Enter grades below.</span>';
    screen.style.color = "#222";
}