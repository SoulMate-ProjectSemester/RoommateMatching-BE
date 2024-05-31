document.addEventListener('DOMContentLoaded', () => {
    const topics = document.querySelectorAll('.topic');
    const submitButton = document.querySelector('.submit-btn');
    let selectedCount = 0; // 선택된 주제의 수를 추적하는 변수
    let keywordValueArr=[];

    // 요청을 보내기 위한 초기 instance 설정
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

    //내가 기존에 선택했던 키워드 선택 표시 해둠.
    try {
        const response=instance.get("/api/keyword/keywords");
        response.then(response => {
            //응답을 성공적으로 받았다면,
            const responseData = response.data.resultCode;
            if(responseData=='SUCCESS'){
                keywordValueArr=response.data.data.keywordSet;
            }

            // Iterate over each keyword in the array
            keywordValueArr.forEach((keyword) => {
                // Find the topic element that matches the keyword and add the 'active' class
                topics.forEach((topic) => {
                    if (topic.textContent === keyword) {
                        topic.classList.add('active');
                        selectedCount++; // Increment the selectedCount for each found and activated topic
                    }
                });
            });
            submitButton.textContent = `수정하기 (${selectedCount}/10)`;
        }).catch(error => {
            // Handle errors if the Promise is rejected
            console.error('키워드 불러오기 응답 실패:', error);
        });

    }catch (error){
        console.error("키워드 불러오기 요청 실패:", error);
    }

    //키워드마다 클릭처리를 해줌
    topics.forEach(topic => {
        topic.addEventListener('click', function() {
            // 이미 활성화된 주제를 클릭한 경우 선택을 취소
            if (topic.classList.contains('active')) {
                topic.classList.remove('active');
                selectedCount--;
            } else if (selectedCount < 10) { // 활성화되지 않은 주제를 클릭하고 선택된 주제의 수가 10개 미만인 경우
                topic.classList.add('active');
                selectedCount++;
            } else {
                // 선택된 주제가 이미 10개인 경우 경고 메시지를 표시
                alert('최대 10개의 주제만 선택할 수 있습니다.');
            }

            // 버튼의 텍스트 업데이트
            submitButton.textContent = `수정하기 (${selectedCount}/10)`;
        });
    });

    submitButton.addEventListener('click', () => {
        if (selectedCount < 3) {
            // 선택된 키워드의 수가 최소 요구 사항을 충족하지 않는 경우 경고
            alert(`최소 3개의 키워드를 선택해야 합니다.\n현재 선택된 키워드 수: ${selectedCount}`);
        } else {
            // 선택된 키워드를 가져와서 배열로 변환한 뒤, 각 주제의 텍스트 내용을 추출합니다.
            const selectedTopics = document.querySelectorAll('.topic.active');
            const selectedTopicNames = Array.from(selectedTopics).map(topic => topic.textContent);

            // 선택된 키워드를 서버로 전송하여 키워드를 수정하는 로직
            const response_put=instance.put("/api/keyword/keywords",{
                keywordSet: selectedTopicNames
            });
            response_put.then(response => {
                //응답을 성공적으로 받았다면,
                const responseData = response.data.resultCode;
                if(responseData=='SUCCESS'){
                    console.log(response.data.message);
                    window.location.href="http://localhost:8080/api/page/info_edit";
                }
            }).catch(error => {
                // Handle errors if the Promise is rejected
                console.error('키워드 수정 실패:', error);
            });

        }
    });
});
