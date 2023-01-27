const express = require("express");
const connection=require("./config/db");
require("dotenv").config();
// const bodyParser=require("body-parser");
const app = express();
app.use(express.urlencoded({extended:false}));
app.use(express.json())
connection();


// app.use(bodyParser.json());
// app.use(bodyParser.urlencoded({extended:false}));

app.use(express.static("public"));
const userRoute=require("./routes/users");
app.use("/api/users/",userRoute);

// app.get("", function (req, res) {
//   res.sendFile(__dirname+"/home.html");
// });

app.listen(5000, function () {
  console.log("server is running on port 5000");
});
