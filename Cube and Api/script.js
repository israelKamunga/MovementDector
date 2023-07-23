const cube = document.getElementById("cube");
setInterval(() => {
    fetch("coordonnee.json").then(e=>e.json()).then(e=>{
    let Xcoordinate = parseInt(e.x)
    let Ycoordinate = parseInt(e.y)

    cube.style.transform = `rotateX(${Xcoordinate}deg) rotateY(${Ycoordinate}deg)`
})
}, 100);
