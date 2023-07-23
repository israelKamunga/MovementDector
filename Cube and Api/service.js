const express = require("express");
const bodyParser = require("body-parser")
const fs = require("fs")
const app = express();
const url = require("url")
const port = 3000;


let coordonnee = {
    x:0,
    y:0
}

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.get("/",(req,res)=>{
    res.statusCode = 200;
    res.setHeader("content-Type","text/plain");
    res.end(JSON.stringify(coordonnee))
    res.send(JSON.stringify(coordonnee))
})

app.post("/",(req,res)=>{
    let params = url.parse(req.url).query;
    let Xvalue = req.body.x;
    let Yvalue = req.body.y;

    let whatToWrite = `{"x":${Xvalue},"y":${Yvalue}}`
    fs.writeFile("coordonnee.json",whatToWrite,(error)=>{
        if(error){
            console.log("une erreur s'est produite lors de l'écriture dans le fichier")
        }else{
            console.log("succes");
        }
    })

    res.end(`x:${Xvalue}\ny:${Yvalue}`);
})

app.listen(port,()=>{
    console.log(`le serveur écoute sur le port ${port}`);
})

function setValues(x,y){
    coordonnee.x=x;
    coordonnee.y=y;
}