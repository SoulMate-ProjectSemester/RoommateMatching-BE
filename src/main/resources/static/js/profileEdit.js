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
let myId;

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
            var email=response.data.data.email;
            var birthDate=response.data.data.birthDate;
            var loginId=response.data.data.loginId;
            var studentNumber=response.data.data.studentNumber;
            myId=response.data.data.id;

            document.getElementById("myProfileName").innerHTML = name;
            document.getElementById("content-space").innerHTML="&nbsp";
            document.getElementById("myProfileMajor").innerHTML = major;
            document.getElementById("myMajor").innerHTML=major;
            document.getElementById("myEmail").innerHTML=email;
            myName.placeholder=name;
            myLoginId.placeholder=loginId;
            myBirthDate.placeholder=birthDate;
            myStudentNumber.placeholder=studentNumber;
        }
    }).catch(error => {
        // Handle errors if the Promise is rejected
        console.error('Error occurred:', error);
        console.log('아이디 혹은 비밀번호를 다시한번 확인하세요.');
    });

}catch (error){
    console.error("로그인 중 에러:", error);
}


//logout 함수
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
                    //로그아웃 성공
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

//내가 선택한 키워드 동적으로 추가
try {
    //키워드들이 동적으로 들어갈 div container
    var container = document.querySelector('.topics-container');
    const response=instance.get("/api/keyword/keywords");
    response.then(response => {
        //응답을 성공적으로 받았다면,
        const responseData = response.data.resultCode;
        if(responseData=='SUCCESS'){
            for(let i=0;i<response.data.data.keywordSet.length;i++){
                let keywordValue=response.data.data.keywordSet[i];
                var topicDiv = document.createElement('div');
                topicDiv.className = 'topic';
                topicDiv.innerHTML=keywordValue;
                container.appendChild(topicDiv);
            }
        }
    }).catch(error => {
        // Handle errors if the Promise is rejected
        console.error('키워드 불러오기 응답 실패:', error);
    });

}catch (error){
    console.error("키워드 불러오기 요청 실패:", error);
}

function showModal(event){
    //모달창을 띄울때 왜 그런지는 모르겠지만 새로고침이 발생하여 새로고침을 막는 코드를 추가합니다.
    event.preventDefault();
    // Get the modal
    var modal = document.getElementById("myModal");
    modal.style.display = "block";

    // Get the element that closes the modal
    var span = document.getElementsByClassName("close")[0];
    var closeBtn = document.getElementById("closeBtn");

    // When the user clicks on <span> (x) or the Close button, close the modal
    span.onclick = closeBtn.onclick = function() {
        modal.style.display = "none";
    }

    //When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
}


function toKeywordPage(){
    window.location.href='/api/page/keyword_edit';
}