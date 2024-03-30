document.addEventListener('DOMContentLoaded', () => {
    const topics = document.querySelectorAll('.topic');
    const submitButton = document.querySelector('.submit-btn');
    let selectedCount = 0; // 선택된 주제의 수를 추적하는 변수

    topics.forEach(topic => {
        topic.addEventListener('click', function() {
            this.classList.toggle('active');
            // 주제가 활성화 상태인 경우 카운트를 증가, 비활성화 상태인 경우 카운트를 감소
            if(this.classList.contains('active')) {
                selectedCount++;
            } else {
                selectedCount--;
            }
            // 버튼의 텍스트 업데이트
            submitButton.textContent = `저장하기 (${selectedCount}/5)`;
        });
    });

    submitButton.addEventListener('click', () => {
        const selectedTopics = document.querySelectorAll('.topic.active');
        // 선택된 주제를 배열로 변환하여 이름만 추출합니다.
        const selectedTopicNames = Array.from(selectedTopics).map(topic => topic.textContent);
        alert('선택된 주제: ' + selectedTopicNames.join(', '));
        // 선택된 주제를 서버로 보내는 코드를 여기에 추가할 수 있습니다.
    });
});
