document.addEventListener('DOMContentLoaded', () => {
    const topics = document.querySelectorAll('.topic');
    const submitButton = document.querySelector('.submit-btn');
    let selectedCount = 0; // 선택된 주제의 수를 추적하는 변수

    // 요청을 보내기 위한 초기 instance 설정
    const token = window.localStorage.getItem("token");

    const instance = axios.create({
        baseURL: "http://soulmate.pe.kr",
        timeout: 5000,
        headers: {
            "Cache-Control": "no-cache",
            "Access-Control-Allow-Origin": "*",
            "Authorization": `Bearer ${token}`,
        },
    });


    //키워드마다 클릭처리를 해줌
    topics.forEach(topic => {
        topic.addEventListener('click', function() {
            // 이미 활성화된 주제를 클릭한 경우 선택을 취소
            if (this.classList.contains('active')) {
                this.classList.remove('active');
                selectedCount--;
            } else if (selectedCount < 10) { // 활성화되지 않은 주제를 클릭하고 선택된 주제의 수가 10개 미만인 경우
                this.classList.add('active');
                selectedCount++;
            } else {
                // 선택된 주제가 이미 10개인 경우 경고 메시지를 표시
                alert('최대 10개의 주제만 선택할 수 있습니다.');
            }

            // 버튼의 텍스트 업데이트
            submitButton.textContent = `저장하기 (${selectedCount}/10)`;
        });
    });

    //키워드 저장하기 버튼을 눌렀을때, 키워드가 저장되고 페이지가 넘어가는 로직
    submitButton.addEventListener('click', () => {
        if (selectedCount < 3) {
            // 선택된 키워드의 수가 최소 요구 사항을 충족하지 않는 경우 경고
            alert(`최소 3개의 키워드를 선택해야 합니다.\n현재 선택된 키워드 수: ${selectedCount}`);
        } else {
            // 선택된 주제를 가져와서 배열로 변환한 뒤, 각 주제의 텍스트 내용을 추출합니다.
            const selectedTopics = document.querySelectorAll('.topic.active');
            const selectedTopicNames = Array.from(selectedTopics).map(topic => topic.textContent);

            // 선택된 키워드를 서버로 전송하는 코드를 여기에 추가할 수 있습니다.
            // 예: 서버에 데이터 전송하는 함수 sendSelectedTopics(selectedTopicNames);

            try {
                const response = instance.post("/api/keyword/new",{
                    keywordSet: selectedTopicNames
                });
                response.then(response => {
                    console.log(response);
                    const result = response.data.resultCode;
                    if(result == "SUCCESS"){
                        //키워드 저장 성공시, 메인페이지로 넘어가는 로직
                        // window.location.href="http://soulmate.pe.kr/api/member/main";
                        window.location.href="http://soulmate.pe.kr/api/page/main";
                    }
                    else console.error('키워드 저장 응답 받기 실패:', error);
                })
            }catch (error){
                console.error("키워드 저장 요청 실패:", error);
            }
        }
    });
});
