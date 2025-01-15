function getFloorandRoom(){
    const ip = document.getElementById("destination");
    const ipValue = ip.value;
    if (Number.isInteger(Number(ipValue)) && ipValue.length>=4 && ipValue.length<=5){
        let roomNumber = ipValue.split('').reverse().join('');
        let block = roomNumber.substring(3).split('').reverse().join('');
        let floor = roomNumber.substring(2,3).split('').reverse().join('');
        let room = roomNumber.substring(0,2).split('').reverse().join('');
        if (Number(block) < 12){
            document.getElementById('block').textContent = block;
            document.getElementById('floor').textContent = floor;
            document.getElementById('room').textContent = room;
            document.getElementById('roominfo').style.display = "table";
        }
        else{
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
        }, function(){
            alert("Location access denied. Please enable location services.");
        });
    }
    else {
        alert("Geolocation is not supported by this browser.");
    }
});

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
            document.getElementById('response').innerHTML = 'Your path: <br>' + source + " > " + data.path.join(" > ") + " > " + destination + ' <br><br> Distance: ' + data.totalDistance.toFixed(2) + 'm';
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

let isDragging = false, startX, startY, posX = 0, posY = 0, initialDistance = 0;

function zoomIn() {
    zoomLevel = Math.min(zoomLevel + 0.1, 3);
    CampusGraph.style.transform = `translate(${posX}px, ${posY}px) scale(${zoomLevel})`;
}

function zoomOut() {
    zoomLevel = Math.max(zoomLevel - 0.1, 1.0);
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
    CampusGraph.style.cursor = "grab"
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
        zoomLevel = Math.max(1, Math.min(zoomLevel * scaleChange, 3));
        initialDistance = newDistance;
        CampusGraph.style.transform = `translate(${posX}px, ${posY}px) scale(${zoomLevel})`;
    }
};

const handleWheel = (e) => {
    e.preventDefault();
    zoomLevel = Math.max(1, Math.min(zoomLevel + (e.deltaY < 0 ? 0.1 : -0.1), 3));
    CampusGraph.style.transform = `translate(${posX}px, ${posY}px) scale(${zoomLevel})`;
};

CampusGraph.addEventListener('mousedown', e => {
    e.preventDefault();
    isDragging = true;
    startX = e.pageX - posX;
    startY = e.pageY - posY;
    CampusGraph.style.cursor = "grab"
});

CampusGraph.addEventListener('mouseup', handleTouchEnd);
CampusGraph.addEventListener('mousemove', e => {
    if (isDragging) {
        posX = e.pageX - startX;
        posY = e.pageY - startY;
        CampusGraph.style.transform = `translate(${posX}px, ${posY}px) scale(${zoomLevel})`;
        CampusGraph.style.cursor = "grabbing"
    }
});

CampusGraph.addEventListener('touchstart', handleTouchStart);
CampusGraph.addEventListener('touchend', handleTouchEnd);
CampusGraph.addEventListener('touchmove', handleTouchMove);
CampusGraph.addEventListener('wheel', handleWheel);















   // ANUJ






function toggleSidebar() {
    const sidebar = document.getElementById('sidebar');
    const body = document.body;

       if (sidebar.style.left === '0px') {
        sidebar.style.left = '-250px'; 
    } else {
        sidebar.style.left = '0px';     
    }
}


document.addEventListener('click', function(event) {
    const sidebar = document.getElementById('sidebar');
    const sidebarIcon = document.querySelector('.sidebar-icon');
    
    if (!sidebar.contains(event.target) && !sidebarIcon.contains(event.target) && sidebar.style.left === '0px') {
        sidebar.style.left = '-250px';  
        document.body.style.marginLeft = '0';
    }
});

        function navigateToPage(page) {
            
            setTimeout(function() {
                if (page === 'home') {
                    window.location.href = 'index.html';
                       
                    
                } else if (page === 'about') {
                    window.location.href = 'about.html'; 
                } else if (page === 'help') {
                    window.location.href = 'help.html'; 
                }
            }, 500); 
        }

        window.addEventListener('load', () => {
            setTimeout(() => {
                document.getElementById('preloader').style.display = 'none';
                document.getElementById('main-content').style.display = 'block';
            }, 3000); // Preloader stays for 3 seconds
        });
        

// Function to handle input change and fill the first available field
function inputChange() {
    const source = document.getElementById('source');
    const destination = document.getElementById('destination');

    // Check if Start Location is empty, if so, fill it
    if (source.value === "" && destination.value === "") {
        source.value = none;  // Replace with any default location
    }
    // If Start Location is filled, check for End Location
    else if (source.value !== "" && destination.value === "") {
        destination.value = none;  // Replace with any default location
    }
}

// Function to set destination based on Quick Access buttons
function setDest(button, locationName) {
    const source = document.getElementById('source');
    const destination = document.getElementById('destination');

    // If Start Location is empty, fill it
    if (source.value === "") {
        source.value = locationName;
    }
    // If Start Location is filled, fill the End Location
    else if (destination.value === "") {
        destination.value = locationName;
    }
}
