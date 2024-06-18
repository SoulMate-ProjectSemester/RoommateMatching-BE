// $(document).ready(function() {
//     $('#signupBtn').click(function() {
//         signup();
//     });
// });
// async function signup() {
//     var signupData = {
//         loginId: $('#signupLoginId').val(),
//         password: $('#signupPassword').val(),
//         name: $('#name').val(),
//         birthDate: $('#birthDate').val(),
//         gender: $('input[name="gender"]:checked').val(),
//         studentNumber : $('#studentNumber').val(),
//         college: $('#college-select').val(),
//         major: $('#major-select').val(),
//         email: $('#email').val()
//     };
//
//     // set axios instance
//     const instance = axios.create({
//         baseURL: "http://soulmate.pe.kr",
//         timeout: 5000,
//         headers: {
//             "Cache-Control": "no-cache",
//             "Content-Type": "application/json",
//             "Access-Control-Allow-Origin": "*",
//         },
//         responseType: "json",
//     });
//
//     try {
//         // Await the response of the POST request
//         const response = await instance.post("/api/member/signup", signupData);
//
//         // Check if the response status is OK (200-299)
//         if (response.status >= 200 && response.status < 300) {
//             // 회원가입이 성공적으로 완료되었다는 메시지를 alert로 표시
//             alert("회원가입이 완료되었습니다");
//             // 페이지를 새로고침하여 최신 상태를 반영
//             window.location.reload();
//         }
//     } catch (error) {
//         const responseData = error.response ? error.response.data : null;
//         if(responseData && responseData.resultCode === "ERROR") {
//             // 기본 에러 메시지 표시
//             alert(`입력값이 잘못되었습니다`);
//             // 구체적인 에러 정보가 있다면 추가로 표시
//             if(responseData.data) {
//                 // responseData.data가 객체인 경우
//                 if(typeof responseData.data === "object" && responseData.data !== null) {
//                     const errorMessages = Object.entries(responseData.data).map(([key, value]) => {
//                         // key에서 시작하는 '_' 제거
//                         const cleanedKey = key.startsWith('_') ? key.slice(1) : key;
//                         return `${cleanedKey} : ${value}`; // 예: "email: 올바른 형식의 이메일 주소여야 합니다"
//                     }).join('\n'); // 각 에러 메시지를 줄바꿈 문자로 구분하여 하나의 문자열로 합칩니다.
//                     alert(`\n${errorMessages}`);
//                 } else {
//                     // responseData.data가 기본 타입이거나 문자열인 경우, 직접 표시
//                     alert(`\n${responseData.data}`);
//                 }
//             }
//         } else {
//             // 기타 에러 처리
//            console.error("회원가입 중 에러:", error);
//         }
//     }
// }
//

$(document).ready(function() {
    $('#signupBtn').click(function() {
        signup();
    });
});

// 로그인 아이디와 이름의 배열
const loginIds = [
    'whdydwls', 'dbswngml', 'tjwldus', 'wlsrkgml', 'rlackddnjs',
    'chlrhgus', 'chlanstjr', 'chlqhrud', 'gktkddn', 'gkgjscks',
    'gkstlgus', 'ghdtjdus', 'chlwhddnjs', 'whgudwns', 'tjgmlwjd',
    'dlwnsdnjs', 'rnjstnsqja', 'rhkrtmdco', 'rlawodud', 'rlaehdgus'
];

const names = [
    '조용진', '윤주희', '서지연', '진가희', '김창원',
    '최고현', '최문석', '최보경', '하상우', '하헌찬',
    '한시현', '홍서연', '최종원', '조형준', '서희정',
    '이준원', '권순범', '곽승채', '김재영', '김동현'
];

// 고정된 회원 정보
const fixedSignupData = {
    password: 'defaultPassword123!',
    birthDate: '2002-10-14',
    gender: 'male', // or 'female' as needed
    studentNumber: '2020270646',
    college: '문화스포츠대학',
    major: '문화유산융합학부',
    email: 'defaultEmail@korea.ac.kr'
};

// Axios 인스턴스 생성
// set axios instance
const instance = axios.create({
    baseURL: "http://soulmate.pe.kr",
    timeout: 5000,
    headers: {
        "Cache-Control": "no-cache",
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
    },
    responseType: "json",
});

async function signup() {
    for (let i = 0; i < 20; i++) {
        const signupData = {
            ...fixedSignupData,
            loginId: loginIds[i],
            name: names[i]
        };

        try {
            const response = await instance.post("/api/member/signup", signupData);
            console.log(`User ${signupData.loginId} signed up successfully:`, response.data);
        } catch (error) {
            console.error(`Error signing up user ${signupData.loginId}:`, error);
        }
    }
}

