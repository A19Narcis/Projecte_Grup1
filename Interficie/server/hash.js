const CryptoJS = require('crypto-js')
const password = "admin2";

// Hash the password using SHA-256
const hashedPassword = CryptoJS.SHA256(password).toString();

console.log(hashedPassword); // prints "f5c6f5dd6a3fb7f2a4a4b7a17d6da9f7ebe8b8f2fa167d56d1c5577de1b6f94c"