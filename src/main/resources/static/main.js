function getFloorandRoom(){
    const ip = document.getElementById("destination");
    const ipValue = ip.value;
    if (Number.isInteger(Number(ipValue)) && ipValue.length>=5 && ipValue.length<=6){
        let roomNumber = ipValue.padStart(6, '0');
        let block = roomNumber.substring(0,2);
        let floor = roomNumber.substring(2, 4);
        let room = roomNumber.substring(4, 6);
        if (Number(block) < 12){
            document.getElementById('block').textContent = block;
            document.getElementById('floor').textContent = floor;
            document.getElementById('room').textContent = room;
            document.getElementById('roominfo').style.display = "table";
        }
        else {
            document.getElementById('roominfo').style.display = "none";
        }
    }
    else{
        document.getElementById('roominfo').style.display = "none";
    }
}

document.getElementById('getLoc').addEventListener('click', async function(e) {
    e.preventDefault();
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(async function(position) {
            const latitude = position.coords.latitude;
            const longitude = position.coords.longitude;

            const response = await fetch('/api/getLoc', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ latitude, longitude })
            });
            if (response.ok){
                const data = await response.json();
                if (data.place != "-1"){
                    document.getElementById('source').value = data.place;
                }
                else{
                    document.getElementById('source').value = "You are far away from the campus.";
                }
            }
        });
    }
    else {
        alert("Geolocation is not supported by this browser.");
    }
})

function setDest(button){
    document.getElementById('destination').value = button.textContent;
    inputChage();
}

document.getElementById('search_btn').addEventListener('click', async function(e) {
    e.preventDefault();
    const source = document.getElementById('source').value;
    const destination = document.getElementById('destination').value;

    const response = await fetch('/api/getPath', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ source, destination })
    });
    if (response.ok) {
        const data = await response.json();
        if (data.path != -1 && data.totalDistance != -1) {
            document.getElementById('response').innerHTML = 'Your path: <br>' + source + " > " + data.path.join(" > ") + " > " + destination + ' <br><br> Distance: ' + data.totalDistance.toFixed(2);
        }
        else if(data.path[0] == -1) {
            document.getElementById('response').innerHTML = 'The source does not exist.';
        }
        else if (data.path[1] == -1) {
            document.getElementById('response').innerHTML = 'The destination does not exist.';
        }
        getFloorandRoom();
        document.getElementById('response_container').style.display="flex";
    }
    else {
        document.getElementById('response').innerHTML = 'Error retrieving path. Please try again.';
    }
});

function inputChage(){
    document.getElementById('response_container').style.display="none"
    document.getElementById('response_container').style.display="none"
}


let zoomLevel = 1;
const CampusGraph = document.getElementById('zoomable-image');
function zoomIn(){
    zoomLevel += 0.1;
    CampusGraph.style.transform = `scale(${zoomLevel})`;
}
function zoomOut(){
    if (zoomLevel > 1) {
        zoomLevel -= 0.1;
        CampusGraph.style.transform = `scale(${zoomLevel})`;
    }
}


let isDragging = false, startX, startY, posX = 0, posY = 0, initialDistance = 0;

function zoomIn() {
    zoomLevel = Math.min(zoomLevel + 0.1, 2);
    CampusGraph.style.transform = `translate(${posX}px, ${posY}px) scale(${zoomLevel})`;
}

function zoomOut() {
    zoomLevel = Math.max(zoomLevel - 0.1, 0.5);
    CampusGraph.style.transform = `translate(${posX}px, ${posY}px) scale(${zoomLevel})`;
}

const handleTouchStart = (e) => {
    if (e.touches.length === 1) {
        isDragging = true;
        const touch = e.touches[0];
        startX = touch.pageX - posX;
        startY = touch.pageY - posY;
    } else if (e.touches.length === 2) {
        initialDistance = Math.hypot(e.touches[0].pageX - e.touches[1].pageX, e.touches[0].pageY - e.touches[1].pageY);
    }
};

const handleTouchEnd = () => {
    if (isDragging) isDragging = false;
    initialDistance = 0;
};

const handleTouchMove = (e) => {
    e.preventDefault();
    if (isDragging && e.touches.length === 1) {
        const touch = e.touches[0];
        posX = touch.pageX - startX;
        posY = touch.pageY - startY;
        CampusGraph.style.transform = `translate(${posX}px, ${posY}px) scale(${zoomLevel})`;
    } else if (e.touches.length === 2) {
        const newDistance = Math.hypot(e.touches[0].pageX - e.touches[1].pageX, e.touches[0].pageY - e.touches[1].pageY);
        const scaleChange = newDistance / initialDistance;
        zoomLevel = Math.max(0.5, Math.min(zoomLevel * scaleChange, 2));
        initialDistance = newDistance;
        CampusGraph.style.transform = `translate(${posX}px, ${posY}px) scale(${zoomLevel})`;
    }
};

const handleWheel = (e) => {
    e.preventDefault();
    zoomLevel = Math.max(0.5, Math.min(zoomLevel + (e.deltaY < 0 ? 0.1 : -0.1), 2));
    CampusGraph.style.transform = `translate(${posX}px, ${posY}px) scale(${zoomLevel})`;
};

CampusGraph.addEventListener('mousedown', e => {
    e.preventDefault();
    isDragging = true;
    startX = e.pageX - posX;
    startY = e.pageY - posY;
});

CampusGraph.addEventListener('mouseup', handleTouchEnd);
CampusGraph.addEventListener('mousemove', e => {
    if (isDragging) {
        posX = e.pageX - startX;
        posY = e.pageY - startY;
        CampusGraph.style.transform = `translate(${posX}px, ${posY}px) scale(${zoomLevel})`;
    }
});

CampusGraph.addEventListener('touchstart', handleTouchStart);
CampusGraph.addEventListener('touchend', handleTouchEnd);
CampusGraph.addEventListener('touchmove', handleTouchMove);
CampusGraph.addEventListener('wheel', handleWheel);
