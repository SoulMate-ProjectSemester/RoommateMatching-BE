var stompClient = null;
var myloginId;
var myId;
var roomId;

const token = window.localStorage.getItem("token");

const instance = axios.create({
    baseURL: "http://localhost:8080",
    timeout: 5000,
    headers: {
        "Cache-Control": "no-cache",
        "Access-Control-Allow-Origin": "*",
        Authorization: `Bearer ${token}`,
    },
});

try {
    const response=instance.get("/api/member/info");
    response.then(response => {
        // Access the 'data' property from the resolved value
        const responseData = response.data.resultCode;

        if(responseData=='SUCCESS'){
            // 로그인 아이디를 어디에서든 사용하기 위해 전역변수로 선언
            myId=response.data.data.id;
            myloginId=response.data.data.loginId;
        }
        showChatListInfo();
    }).catch(error => {
        // Handle errors if the Promise is rejected
        console.error('Error occurred:', error);
        console.log('아이디 혹은 비밀번호를 다시한번 확인하세요.');
    });
}catch (error){
    console.error("로그인 중 에러:", error);
}

function showChatListInfo() {
    try {
        const response=axios.get("http://localhost:8080/chat/rooms", {
            params:{
                loginId:myloginId
            }
        });
        //promise에서 내가 원하는 value 값 받기
        response.then(response => {
            console.log(response);
            // Access the 'data' property from the resolved value
            if(response.data.length>0){
                //룸 아이디와 생성날짜 가져오기
                var index=response.data.length-1;
                roomId=response.data[index].roomId;
                console.log(typeof(roomId));
            }
        }).catch(error => {
            // Handle errors if the Promise is rejected
            console.error('Error occurred:', error);
            alert('채팅방 리스트 조회 에러(roomId)');
        });
        //채팅방 생성 후, 제목 입력칸 초기화
    }catch (error){
        console.error("채팅방 리스트 조회 에러(roomId):", error);
    }
}

connect();

function connect(){
    var socket = new SockJS('/ws-stomp');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
}

function onConnected(){
    stompClient.subscribe(`/sub/chat/room/${roomId}`, function (message) {
        // 나중에 메시지 수신 시 동작 구현 필요
        console.log(JSON.parse(message.body).content);
    });

    // stompClient.send("/app/chat/message");
    stompClient.send("/pub/message");
}

function onError(error){
    alert('웹 소켓 서버에 연결할 수 없습니다. 페이지를 새로고침 해보세요!');
}

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
    chatRoom.scrollTop = chatRoom.scrollHeight;
    // 입력 필드 초기화
    messageInput.value = '';

    if(messageText && stompClient) {
        var chatMessage = {
            chatRoomId: roomId,
            userId: myId, // 사용자 이름 또는 ID
            messageText: messageText,
            messageType: "CHAT"
        };
        // stompClient.send("/app/chat/message", {}, JSON.stringify(chatMessage));
        stompClient.send("/pub/message", {}, JSON.stringify(chatMessage));
        document.getElementById('messageInput').value = '';
    }
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

//채팅 입력 엔터처리
document.addEventListener('DOMContentLoaded', function() {
    var input = document.getElementById('messageInput');
    input.addEventListener('keydown', function(event) {
        if (event.keyCode === 13) { // 13 is the keyCode for Enter key
            sendMessage(); // Call the sendMessage function when Enter is pressed
        }
    });
});

//전 페이지로 돌아가는 함수, 히스토리를 백하는 함수
function hisBack(){
    window.history.back();
    stompClient.unsubscribe();
    stompClient.disconnect();
}
