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
            var email=response.data.data.email;
            var birthDate=response.data.data.birthDate;
            var loginId=response.data.data.loginId;
            var studentNumber=response.data.data.studentNumber;

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

function logout(){
    window.location.href="http://localhost:8080/api/member/login";
    window.localStorage.setItem('token', "");
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