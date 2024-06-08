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
    //waypoint가 함수가 아니라는 typeerror가 있어서 잠시 주석처리 합니다.

    // $('.skills').waypoint(function () {
    //     $('.progress .progress-bar').each(function () {
    //         $(this).css("width", $(this).attr("aria-valuenow") + '%');
    //     });
    // }, {offset: '80%'});


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
    baseURL: "http://soulmate.pe.kr",
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
            // 로그인 아이디를 어디에서든 사용하기 위해 전역변수로 선언
            myloginId=response.data.data.loginId;
            //pk사용을 위한 myId
            myId=response.data.data.id;
            document.getElementById("myProfileName").innerHTML = name;
            document.getElementById("content-space").innerHTML="&nbsp";
            document.getElementById("myProfileMajor").innerHTML = major;
        }
        ShowChatList();
    }).catch(error => {
        // Handle errors if the Promise is rejected
        console.error('Error occurred:', error);
    });

}catch (error){
    console.error("로그인 중 에러:", error);
}

//로그아웃 로직
function logout(){
    if(confirm("정말 로그아웃 하시겠습니까?")==true){
        try {
            const response=instance.delete("/api/member/logout",{
                params:{
                    loginId:myId
                }
            });
            response.then(response => {
                // Access the 'data' property from the resolved value
                const responseData = response.data.resultCode;
                if(responseData=='SUCCESS'){
                    var return_message = response.data.data.message;
                    console.log(return_message);
                }
            }).catch(error => {
                // Handle errors if the Promise is rejected
                console.error('Error occurred:', error);
                console.log('로그아웃 실패(response 오류)');
            });

        }catch (error){
            console.error("로그아웃 실패 : ", error);
        }
        window.location.href='/api/member/login';
        window.localStorage.setItem('token', "");
    }
    else
        return false;
}

function move(){
    window.location.href="/api/page/info_edit";
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

//채팅방들을 동적으로 표시해주는 함수
function ShowChatList(){
    try {
        const response=instance.get("/api/room/rooms", {
            params:{
                loginId:myloginId
            }
        });
        //promise에서 내가 원하는 value 값 받기
        response.then(response => {
            // Access the 'data' property from the resolved value
            if(response.data.data.length>0 && response.data.resultCode=="SUCCESS"){
                //'no-content' 요소 숨기기
                var noContentDiv = document.querySelector('.no-content');
                noContentDiv.style.display = 'none';
                for(let i=0;i<response.data.data.length;i++){
                    //룸 아이디와 생성날짜 가져오기
                    var roomId=response.data.data[i].roomId;
                    var createDate=response.data.data[i].createDate;
                    var roomName=response.data.data[i].roomName;
                    var userName=response.data.data[i].members[0].memberName;
                    var chatRoomDiv = document.createElement('div');
                    chatRoomDiv.className = 'row blog-item px-3 pb-5';

                    // HTML 내용을 문자열로 추가
                    chatRoomDiv.innerHTML = `
                    <div class="col-md-5">
                      <img class="img-fluid mb-4 mb-md-0" src="/images/user-2.jpg" alt="Image">
                    </div>
                    <div class="col-md-7">
                      <h3 class="mt-md-4 px-md-3 mb-2 font-weight-bold">${roomName}</h3>
                      <div class="d-flex mb-3">
                        <small class="mr-2 text-muted" style="margin-left: -45%; margin-top: -0.7rem;"><i class="fa fa-calendar-alt"></i> ${createDate}</small>
                      
                      </div>
                      <p style="margin-left: -45.5%; margin-top: -1rem;"><i class="fa-solid fa-user" style="margin-right: 3px;"></i>${username}</p>
                      <a class="chat_btn" id="myBtn" href="http://soulmate.pe.kr/api/page/${roomId}"><i class="fa-solid fa-comment a-volume-up fa-2x" style="z-index: 11;"></i></a>
                    </div>
                  `;
                    // 생성된 div를 문서에 추가
                    var container = document.querySelector('.container.bg-white.pt-5');
                    container.appendChild(chatRoomDiv);

                }

            }else{
                console.log('there is no chat-list');
            }

        }).catch(error => {
            // Handle errors if the Promise is rejected
            console.error('Error occurred:', error);
        });

        //채팅방 생성 후, 제목 입력칸 초기화
    }catch (error){
        console.error("로그인 중 에러:", error);
    }
}

function MyAnalyze(){
    const instance = axios.create({
        baseURL: "http://soulmate.pe.kr:8181",
        timeout: 500000,
        headers: {
            "Cache-Control": "no-cache",
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*",
        },
        responseType: "json",
    });

    const response = instance.post("/chat",{
        userId: 1,
        message: "나는 어떤 사람이야?"
    });
    response.then(response => {
        console.log(response);
        // console.log(response.data.response);
    }).catch(error => {
        console.log('error occurred:', error);
    })
}


