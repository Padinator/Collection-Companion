const { test, expect } = require('@playwright/test');

// Test for opening the Main Page -> index.html
test('Opening Main Page', async ({ page }) => {

    // Navigate to Main page
    await page.goto('http://localhost:8090');

    // Click on register button
    await page.click('#register');

    await page.goto("http://localhost:8090/register.html");

    // Fill in registration details
    await page.type('#username', 'testuser');
    await page.type('#password', 'testpassword');
    await page.type('#email', 'test@test.de');

    // Click register button
    await page.click("#cc-registration");

    await page.goto("http://localhost:8090/index.html");

    //Fill in login
    await page.type('#username', 'testuser');
    await page.type('#password', 'testpassword');

    // Click on login button
    await page.click('#cc-login');

    await page.goto("http://localhost:8090/mainpage.html?username=testuser");

    // Check if on the I am on the next right page 
    expect(page.url(), 'Not the right page ').toBe('http://localhost:8090/mainpage.html?username=testuser');
});

// Test for clicking the Search Button on the Search bar
test('Click Search Button', async ({ page }) => {

    // Navigate to Mainpage
    await page.goto('http://localhost:8090/mainpage.html?username=testuser');

    // Click the search button on the search bar
    await page.click('#cc-search-button');
});

// Test for go to Mainpage
test('Click Mainpage Button', async ({ page }) => {

    // Navigate to Mainpage
    await page.goto('http://localhost:8090/mainpage.html?username=testuser');

    // Click the go to Mainpage button 
    await page.click('#cc-mainpage-button');
    expect(page.url(), 'Not the right page ').toBe('http://localhost:8090/mainpage.html?username=testuser');
});

// Test for go to Account Page and testing their Buttons
test('Click Account Button', async ({ page }) => {

    // Navigate to Mainpage
    await page.goto('http://localhost:8090/mainpage.html?username=testuser');

    // Click the go to Account button 
    await page.click('#cc-account-button');
    await page.goto("http://localhost:8090/account.html?username=testuser");

    // Click the submit button for the account data
    await page.click('#cc-submit-account-data-button');

});

// Test for go to Collection Page and testing their Buttons
test('Click Collection Button', async ({ page }) => {

    // Navigate to Mainpage
    await page.goto('http://localhost:8090/mainpage.html?username=testuser');

    // Click the go to Collection button 
    await page.click('#cc-collection-button');
    await page.goto('http://localhost:8090/collection.html?username=testuser');

    // Click the create a Collection button
    await page.click('#cc-create-collection-button');

    await page.type("#sammlungName", "testsammlung");

    await page.getByLabel("sichtbarkeit").selectOption("Private");
    await page.getByLabel("kategorie").selectOption("Spiele");

    // Click the edit a Collection submit button
    await page.click('#cc-create-collection-submit-button');
});

// Test for go to Friends Page and testing their Buttons
test('Click Friends Button', async ({ page }) => {

    // Navigate to Mainpage
    await page.goto('http://localhost:8090/mainpage.html?username=testuser');

    // Click the Friends Dropdown 
    await page.click('#freundeDropdown');
});