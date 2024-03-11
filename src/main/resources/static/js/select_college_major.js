function collegeChange() {
    var majorCategories = {
        "공공정책대학": ["경제정책학전공", "공공사회학전공", "통일외교안보전공", "빅데이터사이언스학부", "정부행정학부"],
        "과학기술대학": ["디스플레이융합전공", "반도체물리전공", "미래모빌리티학과", "생명정보공학과", "스마트에코시티 융합전공",
            "식품생명공학과", "신소재화학과", "데이터계산과학전공", "인공지능사이버보안학과", "자율주행시스템 융합전공", "전자/기계융합공학과",
            "전자및정보공학과", "지능형반도체공학과", "컴퓨터융합소프트웨어학과", "환경시스템공학과"],
        "글로벌비즈니스대학": ["독일학전공", "영미학전공", "중국학전공", "한국학전공", "글로벌경영전공", "디지털경영전공", "표준/지식학과"],
        "문화스포츠대학": ["스포츠과학전공", "스포츠비즈니스전공", "문화유산융합학부", "문화콘텐츠전공", "미디어문예창작전공", "한류문화산업경영융합전공"],
        "스마트도시학부": ["스마트도시학부"],
        "약학대학": ["약학과"]
    };

    var selectCollege = $('#college-select').val();
    var majors = majorCategories[selectCollege] || [];
    updateMajorSelect(majors);
}

function updateMajorSelect(majors) {
    var $majorSelect = $('#major-select');
    $majorSelect.empty();
    majors.forEach(function(major) {
        var option = $("<option class=\"dropdown-item\">" + major + "</option>");
        $majorSelect.append(option);
    });
}
