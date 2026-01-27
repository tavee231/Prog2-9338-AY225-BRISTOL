const beep = new Audio('beep.mp3');
let logs = JSON.parse(localStorage.getItem('attendanceData')) || [];
let currentSession = null;

// 5 RANDOM USERNAME & PASSWORD COMBINATIONS
const AUTH_DB = {
    "Jandrei": "123abc",
    "Vargas": "123abc",
    "NovaAdmin": "aqua123",
    "AzureRoot": "zero77",
    "EchoDelta": "delta88"
};

function showPage(pageId) {
    document.querySelectorAll('.page').forEach(p => {
        p.classList.remove('active');
        p.classList.add('hidden');
    });
    document.getElementById(pageId).classList.remove('hidden');
    document.getElementById(pageId).classList.add('active');
}

// MATRIX ANIMATION - UPDATED TO BLUE
const canvas = document.getElementById('matrixCanvas');
const ctx = canvas.getContext('2d');
canvas.width = window.innerWidth; canvas.height = window.innerHeight;
const letters = "01010101AZURESYSTEM010101";
const drops = Array(Math.floor(canvas.width / 16)).fill(1);

function drawMatrix() {
    ctx.fillStyle = "rgba(0, 11, 26, 0.1)";
    ctx.fillRect(0, 0, canvas.width, canvas.height);
    ctx.fillStyle = "#00d4ff"; // Electric Blue
    ctx.font = "16px monospace";
    drops.forEach((y, i) => {
        ctx.fillText(letters[Math.floor(Math.random()*letters.length)], i*16, y*16);
        if (y*16 > canvas.height && Math.random() > 0.975) drops[i] = 0;
        drops[i]++;
    });
}
setInterval(drawMatrix, 40);

function handleLogin() {
    const u = document.getElementById('username').value;
    const p = document.getElementById('password').value;

    // CHECK AGAINST AUTHORIZED DATABASE
    if (AUTH_DB[u] && AUTH_DB[u] === p) {
        currentSession = { user: u, in: new Date().toLocaleString(), out: "ACTIVE" };
        
        document.getElementById('loginBox').classList.add('hidden');
        document.getElementById('statusBox').classList.remove('hidden');
        document.getElementById('userDisplay').innerText = `USER: ${u.toUpperCase()}`;
        document.getElementById('timeInDisplay').innerText = `INFILTRATED: ${currentSession.in}`;
        
        document.getElementById('username').value = ""; 
        document.getElementById('password').value = "";
    } else {
        beep.play();
        alert("ACCESS DENIED: UNAUTHORIZED CREDENTIALS");
    }
}

function handleLogout() {
    currentSession.out = new Date().toLocaleString();
    logs.unshift(currentSession);
    localStorage.setItem('attendanceData', JSON.stringify(logs));
    
    updateTable();
    document.getElementById('statusBox').classList.add('hidden');
    document.getElementById('loginBox').classList.remove('hidden');
    currentSession = null;
    showPage('historyPage');
}

function updateTable() {
    const container = document.getElementById('logContent');
    container.innerHTML = logs.map(l => `
        <div class="log-row">
            <div>${l.user}</div>
            <div style="color:#66ccff">${l.in}</div>
            <div style="color:#00ffff">${l.out}</div>
        </div>
    `).join('');
}

function downloadFullReport() {
    let txt = "AZURE ARCHIVE EXPORT\n" + "=".repeat(25) + "\n";
    logs.forEach(l => txt += `${l.user} | IN: ${l.in} | OUT: ${l.out}\n`);
    const blob = new Blob([txt], {type: 'text/plain'});
    const a = document.createElement('a');
    a.href = URL.createObjectURL(blob); a.download = 'Azure_Logs.txt'; a.click();
}

function clearStorage() {
    if(confirm("DANGER: WIPE CENTRAL MEMORY?")) {
        localStorage.clear(); logs = []; updateTable();
    }
}

updateTable();