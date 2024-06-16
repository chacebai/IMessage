document.addEventListener('DOMContentLoaded', () => {
    const wsUrlInput = document.getElementById('ws-url');
    const connectBtn = document.getElementById('connect-btn');
    const statusDiv = document.getElementById('status');
    const messagesDiv = document.getElementById('messages');
    const messageInput = document.getElementById('message-input');
    const sendBtn = document.getElementById('send-btn');

    let websocket = null;

    connectBtn.addEventListener('click', () => {
        if (websocket) {
            websocket.close();
        } else {
            const wsUrl = wsUrlInput.value.trim();
            if (wsUrl) {
                websocket = new WebSocket(wsUrl);

                websocket.onopen = () => {
                    statusDiv.textContent = 'Connected';
                    statusDiv.style.color = 'green';
                    connectBtn.textContent = 'Disconnect';
                    messageInput.disabled = false;
                    sendBtn.disabled = false;
                };

                websocket.onclose = () => {
                    statusDiv.textContent = 'Disconnected';
                    statusDiv.style.color = 'red';
                    connectBtn.textContent = 'Connect';
                    messageInput.disabled = true;
                    sendBtn.disabled = true;
                    websocket = null;
                };

                websocket.onmessage = (event) => {
                    const message = document.createElement('div');
                    message.textContent = `Received: ${event.data}`;
                    messagesDiv.appendChild(message);
                    messagesDiv.scrollTop = messagesDiv.scrollHeight;
                };

                websocket.onerror = (error) => {
                    console.error('WebSocket Error:', error);
                };
            }
        }
    });

    sendBtn.addEventListener('click', () => {
        const message = messageInput.value.trim();
        if (message && websocket && websocket.readyState === WebSocket.OPEN) {
            websocket.send(message);
            const sentMessage = document.createElement('div');
            sentMessage.textContent = `Sent: ${message}`;
            messagesDiv.appendChild(sentMessage);
            messagesDiv.scrollTop = messagesDiv.scrollHeight;
            messageInput.value = '';
        }
    });
});
