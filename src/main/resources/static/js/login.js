    function loginJWT() {
        fetch('/login/jwt', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                username: document.querySelector('#username').value,
                password: document.querySelector('#password').value
            })
        })
        .then(response => response.text())
        .then(jwt => {
            // JWT를 받은 후 처리할 로직
            console.log("Received JWT:", jwt);
            document.cookie = `jwt=${jwt}; Max-Age=1000; Path=/`; // JWT를 쿠키에 저장
            window.location.replace('/list'); // list 페이지로 리다이렉트
        })
        .catch(error => {
            console.error('Error during login:', error);
            alert('로그인에 실패했습니다. 다시 시도해 주세요.');
        });
    }