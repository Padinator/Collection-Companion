const { test, expect } = require('@playwright/test');

// Test for opening the Main Page -> index.html
test('Opening Main Page', async ({ page }) => {

    // Navigate to Main page
    await page.goto('http://localhost:8090');



    // Click on register button
    await page.click('#register');

    // Fill in registration details
    await page.type('#username', 'testuser');
    await page.type('#password', 'testpassword');
    await page.type('#email', 'test@test.de');

    //Fill in login
    await page.type('#username', 'testuser');
    await page.type('#password', 'testpassword');

    // Click on login button
    await page.click('#cc-login');

    // Check if on the I am on the next right page 
    expect(page.url(), 'Not the right page ').toBe('http://localhost:8090/mainpage.html');
});

// Test for clicking the Search Button on the Search bar
test('Click Search Button', async ({ page }) => {

    // Navigate to Mainpage
    await page.goto('http://localhost:8090/mainpage.html');

    // Click the search button on the search bar
    await page.click('#cc-search-button');
});

// Test for adding a Entry in a Collection
test('Click add Collection Button', async ({ page }) => {

    // Navigate to Mainpage
    await page.goto('http://localhost:8090/mainpage.html');

    // Click the first add collection button 
    await page.click('#cc-add-collection-button-one');

    // Click the second add collection button on the pop up menu
    await page.click('#cc-add-collection-button-two');
});

// Test for go to Mainpage
test('Click Mainpage Button', async ({ page }) => {

    // Navigate to Mainpage
    await page.goto('http://localhost:8090/mainpage.html');

    // Click the go to Mainpage button 
    await page.click('#cc-mainpage-button');
});

// Test for go to Account Page and testing their Buttons
test('Click Account Button', async ({ page }) => {

    // Navigate to Mainpage
    await page.goto('http://localhost:8090/mainpage.html');

    // Click the go to Account button 
    await page.click('#cc-account-button');

    // Click the submit button for the account data
    await page.click('#cc-submit-account-data-button');
});

// Test for go to Collection Page and testing their Buttons
test('Click Collection Button', async ({ page }) => {

    // Navigate to Mainpage
    await page.goto('http://localhost:8090/mainpage.html');

    // Click the go to Collection button 
    await page.click('#cc-collection-button');

    // Click the create a Collection button
    await page.click('#cc-create-collection-button');

    // Click the create a Collection submit button
    await page.click('#cc-create-collection-submit-button');

    // Click the edit a Collection button
    await page.click('#cc-edit-collection-button');

    // Click the edit a Collection submit button
    await page.click('#cc-submit-collection-button');

});

// Test for go to Friends Page and testing their Buttons
test('Click Friends Button', async ({ page }) => {

    // Navigate to Mainpage
    await page.goto('http://localhost:8090/mainpage.html');

    // Click the Friends Dropdown 
    await page.click('#freundeDropdown');

    // Click one friend in the Dropdown
    await page.click('#cc-friends-link');

    // Click the Thumps up Button
    await page.click('#cc-thumps-up-button');

    // Click the Thumps down Button
    await page.click('#cc-thumps-down-button');

    // Click the show collection Button from friend
    await page.click('#cc-show-friend-collection-button');
});