const { test, expect } = require('@playwright/test');

test('Opening Main Page', async ({ page }) => {

    // Navigate to Main page
    await page.goto('http://localhost');

    // Click on login button
    await page.click('#cc-login');

    // Check if on the I am on the next right page 
    expect(page.url(), 'Not the right page ').toBe('http://localhost/mainpage.html');

    // // Click on first exercise sheet entry
    // await page.click('.fl-table tbody tr:first-child');
    // expect(page.url(), 'Not sheet url').toMatch(/^http:\/\/localhost\/uebungsblatt\/\d+/);

    // // Does the back back button work?
    // await page.click('.buttonDefault.zurueck');
    // expect(page.url()).toBe('http://localhost/uebungsblatt/');
});
