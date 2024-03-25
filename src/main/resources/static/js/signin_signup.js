document.querySelector('.img__btn').addEventListener('click', function() {
    document.querySelector('.cont').classList.toggle('s--signup');
});

function login() {
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
        baseURL: "http://localhost:8080",
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
            if(responseData=='SUCCESS'){
                window.localStorage.setItem('token',response.data.data.accessToken);
                window.localStorage.setItem('refreshtoken',response.data.data.refreshToken);
                window.location.href='http://localhost:8080';
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
