function post(key) {
    let questionId = $("#question_id").val();
    let content = $("#comment_content").val();
    
    $.ajax({
        type: 'POST',
        url: '/comment',
        contentType: 'application/json',
        data: JSON.stringify({
            'parentId': questionId,
            'content': content,
            'type': 1
        }),
        
        success: function (response) {
            if (response.code === 200) {
                $("#comment_section").hide();
            } else {
                if (response.code === 2003) {
                    /*返回boolean*/
                    let isAccept = confirm(response.message);
                    if (isAccept) {
                        window.open("http://localhost:7888/login/github");
                        window.localStorage.setItem("closable", "true");
                    }
                } else {
                    alert(response.message);
                }
                
            }
            console.log(response);
        },
        dataType: 'json'
    });
}

