const express=require("express");
const router=express.Router();
const userctrl=require("../controllers/userscontroller");
const bodyParser=require("body-parser");
const protect=require("../middleware/authmiddleware");

router.use(bodyParser.json());
router.use(bodyParser.urlencoded({extended:false}));


  router.post("", userctrl.useradd);
  router.post("/login", userctrl.userLogin);

  router.get("/student/profile",protect.protectstu, userctrl.liststu);
  router.get("/faculty/profile",protect.protectfac, userctrl.listfac);


  router.post("/student/add",protect.protectstu, userctrl.studentAdd);
  router.post("/faculty/add",protect.protectfac, userctrl.courseadd);
  router.post("/faculty/approval",protect.protectfac, userctrl.facapproval);
  router.post("/advisor/approval", userctrl.advapproval);



  

  
  module.exports=  router;
  