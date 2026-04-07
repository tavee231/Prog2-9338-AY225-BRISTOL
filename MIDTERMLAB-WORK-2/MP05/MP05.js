const fs = require('fs');
const readline = require('readline');

/**
 * MP05 - Extract and display a selected column from CSV dataset (Node.js)
 * Dataset handling: Pearson VUE exam results CSV with quoted names
 * Processing logic: User prompt -> read/parse CSV -> extract column -> table format
 * Features: readline prompts, quoted CSV parser, error handling
 */

// Variables:
// - rl: Readline interface for sequential prompts
// - path: User CSV file path
// - colIndex: Column to extract (0-based)
const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

// Functions:
// - parseCSVLine: Handle quoted fields like "Nanete,Ennor"
function parseCSVLine(line) {
    const fields = [];
    let inQuotes = false;
    let field = '';
    for (let i = 0; i < line.length; i++) {
        const c = line[i];
        if (c === '"') {
            inQuotes = !inQuotes;
        } else if (c === ',' && !inQuotes) {
            fields.push(field.trim());
            field = '';
        } else {
            field += c;
        }
    }
    fields.push(field.trim()); // Last field
    return fields;
}

// Main processing logic: Start program -> prompt path -> prompt index -> process
console.log('=== MP05 - COLUMN EXTRACTOR (JavaScript) ===');
rl.question('Enter dataset file path: ', (path) => {
    // Dataset handling: Read file
    fs.readFile(path, 'utf8', (err, data) => {
        if (err) {
            console.error('ERROR: Cannot read file. Check path.');
            rl.close();
            return;
        }

        // Parse CSV into rows
        const lines = data.trim().split('\n');
        const header = parseCSVLine(lines[0]).join(' | ');
        const dataRows = lines.slice(1).map(parseCSVLine);

        // Prompt for column index (sequential)
        rl.question(`Enter column index (0 for first, cols: ${lines[0].split(',').length}): `, (input) => {
            const colIndex = parseInt(input);
            if (isNaN(colIndex) || colIndex < 0) {
                console.log('Invalid index.');
                rl.close();
                return;
            }

            // Display formatted results
            console.log('\n=== EXTRACTED COLUMN ===');
            console.log('Dataset rows:', dataRows.length);
            console.log('Header:', header);
            console.log('Column ' + colIndex + ':');
            console.log('='.repeat(50));
            dataRows.forEach((row, i) => {
                if (colIndex < row.length) {
                    console.log(`| ${row[colIndex]} |`);
                }
            });
            rl.close(); // End program
        });
    });
});
