function serverConnected() {
    let eleLoading = document.getElementById("spinner");
    eleLoading.setAttribute("hidden", "");

    let eleContent = document.getElementById("content");
    eleContent.removeAttribute("hidden");
}

function serverDisconnected() {
    let eleContent = document.getElementById("content");
    eleContent.setAttribute("hidden", "");

    let eleLoading = document.getElementById("spinner");
    eleLoading.removeAttribute("hidden");

    setTimeout(function(){ connectServer(); }, 3000);
}

var webSocket = null;
function connectServer() {
    if ("WebSocket" in window) {
        console.log("WebSocket is supported by Browser!");

        webSocket = new WebSocket("ws://localhost:8080/handler");

        webSocket.onopen = function() {
            serverConnected();
            getVersion();
            getHostnames();
        };

        webSocket.onmessage = function (evt) {
            var received_msg = evt.data;
            let serverMessage = JSON.parse(received_msg);
            serverMessageHandler(serverMessage);
            console.log("Message is received...", serverMessage);
        };

        webSocket.onclose = function() {
            serverDisconnected();
        };
    } else {
        alert("WebSocket NOT supported by your Browser!");
    }
}

function getVersion() {
    let msg = [
        {
            seqNum: 0,
            action: "CONFIG_GET_SERVER_VERSION",
        },
    ];
    webSocket.send(JSON.stringify(msg));
}

function getHostnames() {
    let msg = [
        {
            seqNum: 0,
            action: "CONFIG_GET_SERVER_HOSTS",
        },
    ];
    webSocket.send(JSON.stringify(msg));
}

function serverMessageHandler(serverResponse) {

    for(var index = 0 ; index < serverResponse.length ; index++) {
        let response = serverResponse[index];

        if(response.action === "CONFIG_GET_SERVER_VERSION") {
            document.getElementById("version").innerHTML = response.data.version;
        } else if(response.action === "CONFIG_GET_SERVER_HOSTS") {
            let eleHosts = document.getElementById("hosts");
            let eleHostTemp = document.createElement("input");
            eleHostTemp.setAttribute("type", "text");
            eleHostTemp.setAttribute("readonly", "");
            eleHostTemp.setAttribute("disabled", "");

            for(var i = 0 ; i < response.data.hosts.length ; i++) {
                let ip = response.data.hosts[i];
                let el = eleHostTemp.cloneNode();
                el.setAttribute("value", ip);
                eleHosts.appendChild(el);
                eleHosts.appendChild(document.createElement("br"));
            }
        }
    }
}
