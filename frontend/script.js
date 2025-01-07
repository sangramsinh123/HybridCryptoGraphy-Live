function encryptMessage() {
    const message = document.getElementById('message').value;
    const key = document.getElementById('key').value;
    const algorithm = document.getElementById('algorithm').value;

    // Call your backend API to encrypt the message
    fetch('http://127.0.0.1:8080/encrypt', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ message: message, key: key, algorithm: algorithm })
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById('output').value = data.encryptedMessage;
    })
    .catch(error => console.error('Error:', error));
}

function decryptMessage() {
    const encryptedMessage = document.getElementById('output').value;
    const key = document.getElementById('key').value;
    const algorithm = document.getElementById('algorithm').value;

    // Call your backend API to decrypt the message
    fetch('http://127.0.0.1:8080/decrypt', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ encryptedMessage: encryptedMessage, key: key, algorithm: algorithm })
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById('output').value = data.decryptedMessage;
    })
    .catch(error => console.error('Error:', error));
}