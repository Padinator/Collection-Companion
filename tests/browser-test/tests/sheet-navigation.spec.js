const { test, expect } = require('@playwright/test');

const { chromium } = require('playwright');

const browser = chromium.launch();
const page = browser.newPage();

// This is an empty test that does nothing

browser.close();