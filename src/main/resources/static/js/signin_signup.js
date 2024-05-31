document.querySelector('.img__btn').addEventListener('click', function() {
    document.querySelector('.cont').classList.toggle('s--signup');
});

async function login() {
    // Get input values
    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;

    if(!username) { alert("아이디를 다시 입력하세요"); return; }
    if(!password) {alert("비밀번호를 다시 입력하세요"); return; }

    // Prepare data to send
    var data = {
        loginId: username,
        password: password
    };

    //set axios instance
    const instance = axios.create({
        baseURL: "43.201.214.57",
        timeout: 5000,
        headers: {
            "Cache-Control": "no-cache",
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*",
        },
        responseType: "json",
    });

    // Send POST request using axios
    try {
        const response=instance.post("/api/member/login",{
            loginId: username,
            password: password
        });
        //promise에서 내가 원하는 value 값 받기
        response.then(response => {
            // Access the 'data' property from the resolved value
            const responseData = response.data.resultCode;
            console.log(response);
            //로그인 성공시 처리되어야 할 로직
            if(responseData=="SUCCESS"){
                window.localStorage.setItem('token',response.data.data.accessToken);
                window.localStorage.setItem('refreshtoken',response.data.data.refreshToken);
                const token=response.data.data.accessToken;

                //set axios instance again with bearer token for 키워드 불러와서 최초가입자 여부 파악
                const instance = axios.create({
                    baseURL: "43.201.214.57",
                    timeout: 5000,
                    headers: {
                        "Cache-Control": "no-cache",
                        "Access-Control-Allow-Origin": "*",
                        "Authorization": `Bearer ${token}`,
                    },
                });

                try{
                    const response=instance.get("/api/keyword/keywords");
                    response.then(response=>{
                        console.log(response.data.data.keywordSet);
                        const keywordSetLength=response.data.data.keywordSet;
                        //로그인을 성공했지만, 키워드 입력을 하지 않은 최초로그인, null인 경우
                        if(keywordSetLength===null){
                            window.location.href="43.201.214.57/api/page/keyword";
                        }
                        //로그인을 성공했고, 키워드 입력을 미리 한사람 (기존 가입자), null이 아닌경우
                        else{
                            //키워드 입력페이지로 이동
                            window.location.href='43.201.214.57/api/page/main';
                        }
                    }).catch(error=>{
                        console.log('error occurred:',error);
                        console.log('Keyword 불러오기 응답 실패');
                    })
                }catch (error){
                    console.log("keyword 불러오기 에러");
                }
            }
        }).catch(error => {
            // Handle errors if the Promise is rejected
            console.error('Error occurred:', error);
            alert('아이디 혹은 비밀번호를 다시한번 확인하세요.');
        });

    }catch (error){
        console.error("로그인 중 에러:", error);
    }

}
