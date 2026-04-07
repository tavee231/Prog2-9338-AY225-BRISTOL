const fs = require('fs');
const readline = require('readline');

const rl = readline.createInterface({ input: process.stdin, output: process.stdout });

rl.question('Enter dataset path (Sample_Data-Prog-2-csv.csv): ', (path) => {
    try {
        const data = fs.readFileSync(path, 'utf8');
        let rows = data.trim().split('\n');
        const header = rows.shift(); // Remove header for shuffling

        // Processing logic: Randomize using sort
        rows.sort(() => Math.random() - 0.5);
        
        console.log("\n--- RANDOM 10 SELECTION ---");
        console.log("HEADER: " + header);
        rows.slice(0, 10).forEach((r, i) => console.log(`[${i+1}] ${r}`));
    } catch (e) {
        console.log("File access denied.");
    }
    rl.close();
});