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
// const refreshtoken = window.localStorage.getItem('refreshToken');

console.log("Loaded Token:", token); // 로드된 토큰을 로그로 확인

const instance = axios.create({
    baseURL: "http://soulmate.pe.kr",
    timeout: 5000,
    headers: {
        "Cache-Control": "no-cache",
        "Access-Control-Allow-Origin": "*",
        Authorization: `Bearer ${token}`,
    },
});

console.log("Axios Headers:", instance.defaults.headers); // 설정된 헤더를 로그로 확인

try {
    const response=instance.get("/api/member/info");
    response.then(response => {
        // Access the 'data' property from the resolved value
        const responseData = response.data.resultCode;
        console.log(response);

        if(responseData=='SUCCESS'){
            var name = response.data.data.name;
            var major = response.data.data.major;
            //loginId를 전역변수로 선언하여 어디서든 사용할 수 있도록 함.(var 선언만 안하면 됨)
            loginId=response.data.data.loginId;
            myId=response.data.data.id;
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
        try {
            const response=instance.delete("/api/member/logout",{
                params:{
                    loginId:myId
                }
            });
            console.log(response);
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
        window.location.href='http://soulmate.pe.kr/api/member/login';
        window.localStorage.setItem('token', "");
    }
    else
        return false;
}

showMates();

function showMates(){
    const instance = axios.create({
        baseURL: "http://soulmate.pe.kr",
        timeout: 5000,
        headers: {
            "Cache-Control": "no-cache",
            "Access-Control-Allow-Origin": "*",
            "Authorization": `Bearer ${token}`,
        },
        responseType: "json",
    })

    const response=instance.get("/api/member/user_list");
    response.then(response=>{
        console.log(response);
        const result=response.data.resultCode;
        if(result=="SUCCESS" && response.data.data.length>0){
            var blogContainer = document.getElementById('blog-container');

            for(let i=0;i<response.data.data.length;i++) {
                var mateName = response.data.data[i].name;
                var mateMajor = response.data.data[i].major;
                var blogDiv = document.createElement('div');

                blogDiv.innerHTML = `
                        <div class="row blog-item px-3 pb-5">
                            <div class="col-md-5">
                                <img class="img-fluid mb-4 mb-md-0" src="img/blog-1.jpg" alt="Image">
                            </div>
                            <div class="col-md-7">
                                <h3 class="mt-md-4 px-md-3 mb-2 py-2 bg-white font-weight-bold">${mateName}</h3>
                                <div class="d-flex mb-3">
                                    <small class="mr-2 text-muted"><i class="fa fa-calendar-alt"></i> ${mateMajor} </small>
                                    <small class="mr-2 text-muted"><i class="fa fa-folder"></i> Web Design</small>
                                    <small class="mr-2 text-muted"><i class="fa fa-comments"></i> 15 Comments</small>
                                </div>
                                <p>${mateName}</p>
                                <a class="btn btn-link p-0" id="myBtn" onclick="startChat()"> 테스트 채팅 <i class="fa fa-angle-right"></i></a>
                            </div>
                        </div>
                `;

                blogContainer.appendChild(blogDiv);
            }
        }
    }).catch(error => {
        console.log('error occurred:', error);
    })
}

function move(){
    window.location.href="http://soulmate.pe.kr/api/member/info_edit";
}

function startChat(){
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
}


var roomId = null;

//채팅방 이름 입력후 채팅방 생성하는 함수
function saveChange(){
    var inputTitle = document.getElementById("chatroom-name");
    var messageTitle=inputTitle.value;
    if (messageTitle.trim() === '') {
        // 메시지가 비어있는 경우 아무것도 하지 않음
        return;
    }
    // Send POST request using axios
    try {
        const response=instance.post("/api/room/new",{
            loginId: loginId,
            userId: "vkflco08",
            roomName: messageTitle,
            createDate: getCurrentDate()
        });

        //채팅방 생성 후, 제목 입력칸 초기화
        inputTitle.value=null;
        console.log(response);
        response.then(response => {
            // Access the 'data' property from the resolved value
            const responseData = response.data.resultCode;
            if(responseData=='SUCCESS'){
                roomId = response.data.data.roomId;
                location.href=`http://soulmate.pe.kr/api/room/${roomId}`;
            }
        }).catch(error => {
            // Handle errors if the Promise is rejected
            console.error('Error occurred:', error);
            console.log('roomId 가져오기 실패');
        });
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

//AI로 내 성향 분석
function MyAnalyze(){
    showLoading();

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
        const element=document.getElementById('ai-analyze');
        element.innerText=response.data.response;
        const elementId1=document.getElementById('ai-analyze-text1');
        elementId1.style.display='none';
        const elementId2=document.getElementById('ai-analyze-text2');
        elementId2.style.display='none';
        if(element.innerText)
            closeLoading();
    }).catch(error => {
        console.log('error occurred:', error);
    })


}

// 로딩 열기
function showLoading() {
    var screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
    var screenHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;

    // 현재 스크롤 위치 가져오기
    var scrollX = window.scrollX || window.pageXOffset || document.documentElement.scrollLeft;
    var scrollY = window.scrollY || window.pageYOffset || document.documentElement.scrollTop;

    // 화면 중앙 좌표 계산
    var centerX = (scrollX + screenWidth / 2) - 30;
    var centerY = (scrollY + screenHeight / 2) - 12.7;

    $("#spinner").attr("style", "top:" + centerY + "px" + "; left:" + centerX + "px");
    document.querySelector("#loading").style.height = "100%";
    //body 스크롤 막기
    document.querySelector('body').classList.add('prev_loading');

    $('#loading').show();
}

// 로딩 닫기
function closeLoading() {
    document.querySelector('body').classList.remove('prev_loading');
    $('#loading').hide();
    var screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
    var screenHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;

    // 현재 스크롤 위치 가져오기
    var scrollX = window.scrollX || window.pageXOffset || document.documentElement.scrollLeft;
    var scrollY = window.scrollY || window.pageYOffset || document.documentElement.scrollTop;

    // 화면 중앙 좌표 계산
    var centerX = (scrollX + screenWidth / 2) - 30;
    var centerY = (scrollY + screenHeight / 2) - 12.7;
    document.querySelector("#loading").style.height = "100%";
    $("#spinner").attr("style", "top:" + centerY + "px" + "; left:" + centerX + "px");
}



