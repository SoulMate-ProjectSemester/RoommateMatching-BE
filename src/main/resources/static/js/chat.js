function sendMessage() {
    var chatRoom = document.getElementById('chatRoom');
    var messageInput = document.getElementById('messageInput');
    var messageText = messageInput.value;
    if (messageText.trim() === '') {
        // 메시지가 비어있는 경우 아무것도 하지 않음
        return;
    }

    // 새로운 메시지 요소 생성
    var messageElement = document.createElement('div');
    messageElement.classList.add('my-message');

    // 메시지 내용
    var content = document.createElement('div');
    content.classList.add('content');
    content.textContent = messageText;

    // 시간 표시
    var timestamp = document.createElement('div');
    timestamp.classList.add('timestamp');
    var now = new Date();
    timestamp.textContent = formatDate();

    // 요소 구성
    messageElement.appendChild(content);
    messageElement.appendChild(timestamp);

    // 채팅방에 메시지 추가
    chatRoom.appendChild(messageElement);

    // 입력 필드 초기화
    messageInput.value = '';
}

function formatAMPM(date) {
    var hours = date.getHours();
    var minutes = date.getMinutes();
    var ampm = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12;
    hours = hours ? hours : 12; // 0시는 12시로 표시
    minutes = minutes < 10 ? '0'+minutes : minutes;
    var strTime = hours + ':' + minutes + ' ' + ampm;
    return strTime;
}

function formatDate() {
    var date = new Date(); // 현재 날짜 및 시간을 가져옵니다. 특정 날짜를 설정하려면 new Date(year, monthIndex, day, hours, minutes) 형식을 사용할 수 있습니다.

    var monthNames = [
        "Jan", "Feb", "Mar",
        "Apr", "May", "Jun", "Jul",
        "Aug", "Sep", "Oct",
        "Nov", "Dec"
    ];

    var day = date.getDate();
    var monthIndex = date.getMonth();
    var year = date.getFullYear();

    var formattedTime = formatAMPM(date);

    return monthNames[monthIndex] + ' ' + day + ', ' + year + ', ' + formattedTime;
}

document.addEventListener('DOMContentLoaded', function() {
    var input = document.getElementById('messageInput');

    input.addEventListener('keydown', function(event) {
        if (event.keyCode === 13) { // 13 is the keyCode for Enter key
            sendMessage(); // Call the sendMessage function when Enter is pressed
        }
    });
});


