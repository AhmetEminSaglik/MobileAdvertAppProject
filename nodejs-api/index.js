const express = require("express");
const cors = require("cors");
const app = express();
const db = require("./app/models");

app.use(cors());
app.use(express.json());
app.use(express.urlencoded({extended:false}));
app.use(express.static(__dirname + '/public'));

// resimler klasoru yoksa olusturacak
var fs = require('fs');
var dir = './public';
if (!fs.existsSync(dir)){
    fs.mkdirSync(dir);
}
var dir2 = './public/resimler';
if (!fs.existsSync(dir2)){
    fs.mkdirSync(dir2);
}

db.sequelize.sync({force:false}).then(()=>{
    console.log('olustu');
});


app.get("/", (req, res) => {
    res.json({ message: "Welcome to ahmet-advert application." });
});

//? Tanımlama İşlemleri
const userRouter = require('./app/routes/user.routes');
const advertRouter = require('./app/routes/advert.routes');

app.use('/api/advert', advertRouter)
app.use('/api/user', userRouter)

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}.`);
});
