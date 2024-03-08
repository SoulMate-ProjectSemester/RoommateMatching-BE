document.querySelector('.img__btn').addEventListener('click', function() {
    document.querySelector('.cont').classList.toggle('s--signup');
});

document.getElementById('college-select').addEventListener('change', function() {
    var selectedOption=this.value;
    var selectAll=document.querySelectorAll('.major-select select');

    selectAll.forEach(function(select) {
        select.style.display='none';
    });

    var majorSelect=document.getElementById(selectedOption);
    if(majorSelect) {
        majorSelect.style.display="inline-block";
        majorSelect.style.width="144px";
    }
});

$(document).ready(function () {
    $("#submit").click(function () {
        $.ajax({
            method: "POST",
            url: "https://localhost:8080/api/member/login",
            data: {"loginId":"금일카센터", "password": "Kia motors" }
        })
            .done(function( msg ) {
                console.log(msg);
            });
    });
});