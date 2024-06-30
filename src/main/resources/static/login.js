    function loginJWT(){
    fetch('/login/jwt', {
        method : 'POST',
        headers : {'Content-Type': 'application/json'},
        body : JSON.stringify({
            username : document.querySelector('#username').value,
            password : document.querySelector('#password').value
        })
    }).then(r => r.text()).then((r)=>{ console.log(r) })
}


    function getMypage(){
        fetch('mypage/jwt')
        .then((r) => r.text())
        .then((r => {console.log(r)}))
    }