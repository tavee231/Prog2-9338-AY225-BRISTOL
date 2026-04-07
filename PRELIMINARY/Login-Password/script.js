// Audio Setup
const errorSound = new Audio('arayko.mp4.mp4'); 
const successSound = new Audio('anggaling.mp4.mp4');

let logs = JSON.parse(localStorage.getItem('attendanceData')) || [];
let currentSession = null;

const AUTH_DB = {
    "NovaAdmin": "blue123",
    "CyberSpectre": "ghost99",
    "NexusUser": "pass456",
    "AzureRoot": "zero77",
    "EchoDelta": "delta88"
};

function showPage(pageId) {
    document.querySelectorAll('.page').forEach(p => {
        p.classList.remove('active');
        p.classList.add('hidden');
    });
    document.getElementById(pageId).classList.add('active');
    document.getElementById(pageId).classList.remove('hidden');
}

// MATRIX ANIMATION
const canvas = document.getElementById('matrixCanvas');
const ctx = canvas.getContext('2d');
canvas.width = window.innerWidth; canvas.height = window.innerHeight;
const letters = "01BSITGD1stYear01";
const drops = Array(Math.floor(canvas.width / 16)).fill(1);

function drawMatrix() {
    ctx.fillStyle = "rgba(0, 11, 26, 0.1)";
    ctx.fillRect(0, 0, canvas.width, canvas.height);
    ctx.fillStyle = "#00d4ff";
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

    if (AUTH_DB[u] && AUTH_DB[u] === p) {
        // SUCCESS CASE
        successSound.play().catch(e => console.log("Sound error: ", e));
        
        currentSession = { user: u, in: new Date().toLocaleString(), out: "ACTIVE" };
        
        document.getElementById('loginBox').classList.add('hidden');
        document.getElementById('statusBox').classList.remove('hidden');
        document.getElementById('globalLogout').classList.remove('hidden');
        
        document.getElementById('userDisplay').innerText = `USER: ${u.toUpperCase()}`;
        document.getElementById('activeUserLabel').innerText = u.toUpperCase();
        document.getElementById('timeInDisplay').innerText = `INFILTRATED: ${currentSession.in}`;
        document.getElementById('userSignature').innerText = u;
        
        document.getElementById('username').value = ""; 
        document.getElementById('password').value = "";
    } else {
        // FAILURE CASE
        errorSound.play().catch(e => console.log("Sound error: ", e));
        alert("ACCESS DENIED: ARAY KO!");
    }
}

function handleLogout() {
    if (!currentSession) return;
    currentSession.out = new Date().toLocaleString();
    logs.unshift(currentSession);
    localStorage.setItem('attendanceData', JSON.stringify(logs));
    updateTable();
    
    document.getElementById('statusBox').classList.add('hidden');
    document.getElementById('loginBox').classList.remove('hidden');
    document.getElementById('globalLogout').classList.add('hidden');
    
    currentSession = null;
    showPage('historyPage');
}

function updateTable() {
    const container = document.getElementById('logContent');
    if (logs.length === 0) {
        container.innerHTML = "<div style='padding:20px;'>ARCHIVES EMPTY</div>";
        return;
    }
    container.innerHTML = logs.map(l => `
        <div class="log-row">
            <div>${l.user}</div>
            <div style="color:#66ccff">${l.in}</div>
            <div style="color:#00ffff">${l.out}</div>
        </div>
    `).join('');
}

function downloadFullReport() {
    let txt = "BSIT-GD 1st YEAR ATTENDANCE LOG\n" + "=".repeat(30) + "\n";
    logs.forEach(l => txt += `${l.user} | IN: ${l.in} | OUT: ${l.out}\n`);
    const blob = new Blob([txt], {type: 'text/plain'});
    const a = document.createElement('a');
    a.href = URL.createObjectURL(blob); a.download = 'BSITGD_Attendance.txt'; a.click();
}

function clearStorage() {
    if(confirm("DANGER: WIPE ARCHIVES?")) {
        localStorage.clear(); logs = []; updateTable();
    }
}

updateTable();