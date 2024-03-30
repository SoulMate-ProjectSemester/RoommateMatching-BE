var stompClient = null;
var myloginId;
var myId;

//roomId를 path variable로 가져오는 과정
const path = window.location.pathname; // 현재 페이지의 경로를 가져옵니다.
const pathSegments = path.split('/'); // '/'를 기준으로 경로를 분할합니다.
const roomId = pathSegments[pathSegments.length - 1]; // URL의 마지막 부분을 추출합니다.

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
    }).catch(error => {
        // Handle errors if the Promise is rejected
        console.error('Error occurred:', error);
        console.log('아이디 혹은 비밀번호를 다시한번 확인하세요.');
    });
}catch (error){
    console.error("로그인 중 에러:", error);
}

connect();

function connect(){
    var socket = new SockJS('/ws-stomp');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
}

function onConnected(){
    stompClient.subscribe(`/sub/${roomId}`, onMessageReceived);
    stompClient.send(`/pub/${roomId}`);
}

function onError(error){
    alert('웹 소켓 서버에 연결할 수 없습니다. 페이지를 새로고침 해보세요!');
}

function sendMessage() {
    // var chatRoom = document.getElementById('chatRoom');
    var messageInput = document.getElementById('messageInput');
    var messageText = messageInput.value;
    if (messageText.trim() === '') {
        // 메시지가 비어있는 경우 아무것도 하지 않음
        return;
    }

    if(messageText && stompClient) {
        var chatMessage = {
            userId: myId, // 사용자 이름 또는 ID
            messageText: messageText,
            // messageType: "CHAT"
        };
        // stompClient.send("/app/chat/message", {}, JSON.stringify(chatMessage));
        stompClient.send(`/pub/${roomId}`, {}, JSON.stringify(chatMessage));
        document.getElementById('messageInput').value = '';
    }
}

//메세지를 수신 시, 처리 동작
function onMessageReceived(payload){
    console.log(payload);

    var ParseBody = JSON.parse(payload.body);
    var message=ParseBody.messageText;
    var userId=ParseBody.userId;

    if(myId==userId){
        var chatRoom = document.getElementById('chatRoom');

        // 새로운 메시지 요소 생성
        var messageElement = document.createElement('div');
        messageElement.classList.add('my-message');

        // 메시지 내용
        var content = document.createElement('div');
        content.classList.add('content');
        content.textContent = message;

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
    }else{
        var chatRoom = document.getElementById('chatRoom');

        // 새로운 메시지 요소 생성
        var messageElement = document.createElement('div');
        messageElement.classList.add('another_message');

        // 메시지 내용
        var content = document.createElement('div');
        content.classList.add('content');
        content.textContent = message;

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
    }

    chatRoom.scrollTop = chatRoom.scrollHeight;

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
    stompClient.unsubscribe();
    stompClient.disconnect();
    window.history.back();
}

// 모달창 띄우기
var modal = document.getElementById("myModal");

// Get the button that opens the modal
var btn = document.getElementById("LeftBtn");

// Get the element that closes the modal
var span = document.getElementsByClassName("close")[0];
var closeBtn = document.getElementById("closeBtn");

// When the user clicks the button, open the modal
btn.onclick = function() {
    modal.style.display = "block";
}

// When the user clicks on <span> (x) or the Close button, close the modal
span.onclick = closeBtn.onclick = function() {
    modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

// 모달창 띄우기 끝

//채팅방 나가는 함수(채팅내역 모두 삭제)
function ChatRoomLeft(){
    try {
        const response=axios.delete("http://localhost:8080/api/room/quit", {
            data:{
                loginId: myId,
                roomId: roomId
            }
        });
        response.then(response => {
            console.log(response);
            // Access the 'data' property from the resolved value
            const responseData = response.data.resultCode;
            //채팅방 삭제가 성공하였을 경우
            if(responseData=='SUCCESS'){
                // 로그인 아이디를 어디에서든 사용하기 위해 전역변수로 선언
                location.href="http://localhost:8080/api/member/chat_list";
            }
        }).catch(error => {
            // Handle errors if the Promise is rejected
            console.error('Error occurred:', error);
            alert('채팅방 삭제 실패');
        });
    }catch (error){
        console.error("채팅방 삭제 요청 실패 : ", error);
    }
}