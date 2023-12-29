const { test, expect } = require('@playwright/test');

// PHP error check polyfill
async function phpErrorChecker (page) {
    const originalGoto = page.goto;

    async function checkForPHPErrors(url, options) {
        const result = await originalGoto.call(page, url, options); // Load page
        // Find Fatal PHP error
        const qmFatalElement = await page.$('#qm-fatal');
        expect(qmFatalElement, "Fatal PHP error - visible on page").toBeNull();
        // Find internal PHP error
        const qmInternalError = await page.$('.qm-fatal-wrap');
        expect(qmInternalError, "Internal PHP error - query monitor").toBeNull();
        return result;
    };
    page.goto = checkForPHPErrors; // Overwrite page.goto function
}
