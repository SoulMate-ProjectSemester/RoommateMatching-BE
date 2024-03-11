function collegeChange(){
    var major_policy = ["경제정책학전공", "공공사회학전공", "통일외교안보전공", "빅데이터사이언스학부", "정부행정학부"];
    var major_science = ["디스플레이융합전공", "반도체물리전공", "미래모빌리티학과", "생명정보공학과", "스마트에코시티 융합전공",
        "식품생명공학과", "신소재화학과", "데이터계산과학전공", "인공지능사이버보안학과", "자율주행시스템 융합전공", "전자/기계융합공학과",
        "전자및정보공학과", "지능형반도체공학과", "컴퓨터융합소프트웨어학과", "환경시스템공학과"];
    var major_global = ["독일학전공", "영미학전공", "중국학전공", "한국학전공", "글로벌경영전공", "디지털경영전공", "표준/지식학과"];
    var major_sports_media = ["스포츠과학전공", "스포츠비즈니스전공", "문화유산융합학부", "문화콘텐츠전공",
        "미디어문예창작전공", "한류문화산업경영융합전공"];
    var major_smart_city = ["스마트도시학부"];
    var major_medicine = ["약학과"];

    var selectCollege = $('#college-select').val();
    var changeCollege;
    if(selectCollege == "공공정책대학"){
        changeCollege = major_policy;
    }
    else if(selectCollege == "과학기술대학"){
        changeCollege = major_science;
    }
    else if(selectCollege == "글로벌비즈니스대학"){
        changeCollege = major_global;
    }
    else if(selectCollege == "문화스포츠대학"){
        changeCollege = major_sports_media;
    }
    else if(selectCollege == "스마트도시학부"){
        changeCollege = major_smart_city;
    }
    else if(selectCollege == "약학대학"){
        changeCollege = major_medicine;
    }
    $('#major-select').empty();
    for(var count = 0; count < changeCollege.length; count++){
        var option = $("<option class=\"dropdown-item\">"+changeCollege[count]+"</option>");
        $('#major-select').append(option);
    }
}