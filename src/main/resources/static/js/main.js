(function ($) {
    "use strict";

    // Dropdown on mouse hover
    $(document).ready(function () {
        function toggleNavbarMethod() {
            if ($(window).width() > 992) {
                $('.navbar .dropdown').on('mouseover', function () {
                    $('.dropdown-toggle', this).trigger('click');
                }).on('mouseout', function () {
                    $('.dropdown-toggle', this).trigger('click').blur();
                });
            } else {
                $('.navbar .dropdown').off('mouseover').off('mouseout');
            }
        }
        toggleNavbarMethod();
        $(window).resize(toggleNavbarMethod);
    });


    // Skills
    $('.skills').waypoint(function () {
        $('.progress .progress-bar').each(function () {
            $(this).css("width", $(this).attr("aria-valuenow") + '%');
        });
    }, {offset: '80%'});


    // Back to top button
    $(window).scroll(function () {
        if ($(this).scrollTop() > 100) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').click(function () {
        $('html, body').animate({scrollTop: 0}, 1500, 'easeInOutExpo');
        return false;
    });
})(jQuery);

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
            var name = response.data.data.name;
            var major = response.data.data.major;
            loginId=response.data.data.loginId;
            document.getElementById("myProfileName").innerHTML = name;
            document.getElementById("content-space").innerHTML="&nbsp";
            document.getElementById("myProfileMajor").innerHTML = major;
        }
    }).catch(error => {
        // Handle errors if the Promise is rejected
        console.error('Error occurred:', error);
        console.log('아이디 혹은 비밀번호를 다시한번 확인하세요.');
    });

}catch (error){
    console.error("로그인 중 에러:", error);
}

function logout(){
    if(confirm("정말 로그아웃 하시겠습니까?")==true){
        window.location.href='http://localhost:8080/api/member/login';
        window.localStorage.setItem('token', "");
    }
    else
        return false;
}

function move(){
    window.location.href="http://localhost:8080/api/member/info_edit";
}

// Get the modal
var modal = document.getElementById("myModal");

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

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

function saveChange(){
    console.log(loginId);
    var inputTitle = document.getElementById("chatroom-name");
    var messageTitle=inputTitle.value;
    if (messageTitle.trim() === '') {
        // 메시지가 비어있는 경우 아무것도 하지 않음
        return;
    }
    // Send POST request using axios
    try {
        const response=instance.post("/chat/room",{
            loginId: loginId,
            userId: "vkflco08",
            roomName: messageTitle,
            createDate: getCurrentDate()
        });
        //promise에서 내가 원하는 value 값 받기
        // response.then(response => {
        //     // Access the 'data' property from the resolved value
        //     const responseData = response.data.resultCode;
        //     if(responseData=='SUCCESS'){
        //         window.location.href='http://localhost:8080';
        //         window.localStorage.setItem('token',response.data.data.accessToken);
        //     }
        // }).catch(error => {
        //     // Handle errors if the Promise is rejected
        //     console.error('Error occurred:', error);
        //     alert('아이디 혹은 비밀번호를 다시한번 확인하세요.');
        // });

        //채팅방 생성 후, 제목 입력칸 초기화
        inputTitle.value=null;
        window.location.href="http://localhost:8080/api/member/chat";
    }catch (error){
        console.error("로그인 중 에러:", error);
    }

}

function getCurrentDate() {
    const today = new Date();
    const year = today.getFullYear();
    // 월은 0부터 시작하기 때문에 1을 더해주고, 두 자리 수로 만들기 위해 '0'을 앞에 붙이고 마지막 두 자리만 가져옵니다.
    const month = ('0' + (today.getMonth() + 1)).slice(-2);
    // 일자도 두 자리 수로 만듭니다.
    const day = ('0' + today.getDate()).slice(-2);
    // 최종적으로 'YYYY-MM-DD' 형식의 문자열을 반환합니다.
    return `${year}-${month}-${day}`;
}






