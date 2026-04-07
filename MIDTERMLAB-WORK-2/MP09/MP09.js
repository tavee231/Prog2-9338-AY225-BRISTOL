const fs = require('fs');
const readline = require('readline');

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

console.log('=== MP09 Dataset Statistics ===');
rl.question('Enter CSV file path: ', (path) => {
    fs.readFile(path, 'utf8', (err, data) => {
        if (err) {
            console.error('Error: ' + err.message);
            rl.close();
            return;
        }

        const lines = data.trim().split('\n');
        let totalRows = 0, totalCols = 0, passCount = 0;
        let scoreSum = 0, maxScore = 0, minScore = Infinity;
        const examCounts = {};

        const header = lines[0].split(',');
        totalCols = header.length;

        lines.slice(1).forEach(line => {
            const fields = line.split(',');
            totalRows++;
            if (fields.length > 7) {
                const scoreStr = fields[6].trim();
                const result = fields[7].trim();
                const exam = fields[3].trim();
                
                const score = parseInt(scoreStr);
                if (!isNaN(score)) {
                    scoreSum += score;
                    maxScore = Math.max(maxScore, score);
                    minScore = Math.min(minScore, score);
                }
                if (result.includes('PASS')) passCount++;
                
                examCounts[exam] = (examCounts[exam] || 0) + 1;
            }
        });

        const avgScore = totalRows > 0 ? scoreSum / totalRows : 0;
        const passRate = totalRows > 0 ? (passCount / totalRows) * 100 : 0;

        console.log('\n=== STATISTICS ===');
        console.log(`Total data rows: ${totalRows}`);
        console.log(`Total columns: ${totalCols}`);
        console.log(`Average score: ${avgScore.toFixed(1)}`);
        console.log(`Score range: ${minScore} - ${maxScore}`);
        console.log(`Pass rate: ${passRate.toFixed(1)}%`);
        console.log('Top exams:');
        Object.entries(examCounts)
            .sort(([,a], [,b]) => b - a)
            .slice(0, 5)
            .forEach(([exam, count]) => console.log(`  ${exam}: ${count}`));

        rl.close();
    });
});
// Comments: Node.js dataset stats with score/result/exam analysis, sorting top exams

