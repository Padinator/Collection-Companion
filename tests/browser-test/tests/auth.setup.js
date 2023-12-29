import { test as setup, expect } from '@playwright/test';

const authFile = 'playwright/.auth/user.json';

setup('authenticate', async ({ page }) => {
    await page.goto('http://localhost/');
    await page.waitForSelector('#wp-submit');

    // get the input field and type in the username
    await page.waitForSelector('#user_login');
    await page.fill('#user_login', 'testuser');
    // get the input field and type in the password
    await page.waitForSelector('#user_pass');
    await page.fill('#user_pass', '2EF^eS@gTj0u3%Z)IEXFKeVe');
});

