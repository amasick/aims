const students = require("../models/student");
const faculty = require("../models/faculty");
const course = require("../models/courses");
const record = require("../models/records");
const subject = require("../models/subject");
const jwt = require("jsonwebtoken");
const bcrypt = require("bcryptjs");
// const asynchandler=require("express-async-handler");

const liststu = async (req, res) => {
  let user = await record.find({st_email:req.user.email});
  // console.log(user);
  res.json(user);
};

const facrequests = async (req, res) => {
  let user = await record.find({prof_email:req.user.email});
  console.log(user);
  res.json(user);
};

const listfac = async (req, res) => {
  let user = await record.find({prof_email: req.user.email});
  res.json(user);
};
const listcourses = async (req,res)=>{
  let data= await course.find();
  res.json(data);
}

const listsubjects = async (req,res)=>{
  let data= await subject.find();
  res.json(data);
}

const listcoursesfac = async (req,res)=>{
  let data= await course.find({email: req.user.email});
  res.json(data);
}
const userLogin = async (req, res) => {
  if (req.body.role == "student") {

    const user = await students.findOne({ email: req.body.email });
  console.log(user);

    if (user && (await bcrypt.compare(req.body.password, user.password))) {
      let data = user;
      console.log(user);
      res.json({
        role:"student",
        _id: data._id,
        name: data.name,
        email: data.email,
        token: generateToken(data._id),
      });
    } else {
      res.status(400).send("invalid credentials");
    }
  } else {
    const user = await faculty.findOne({ email: req.body.email });
    if (user && (await bcrypt.compare(req.body.password, user.password))) {
      let data = user;
      res.json({
        role:"faculty",
        _id: data._id,
        name: data.name,
        email: data.email,
        token: generateToken(data._id),
      });
    } else {
      res.status(400).send("invalid credentials");
    }
  }
};

const useradd = async (req, res) => {
  // console.log(req.body);
  if(req.body.role=="student"){
     let user=await students.findOne({email:req.body.email});
  if(!user){

     let stname=req.body.name;
     let stemail=req.body.email;
     let stpassword=req.body.password;
     const salt=await bcrypt.genSalt(10);
     const hashedpassword=await bcrypt.hash(stpassword,salt);
      let data = new students ({name:stname,email:stemail,password:hashedpassword});
      let response=await data.save();

     res.json({
      _id:data._id,
      role:"student",
      name: data.name,
      email: data.email,
      token: generateToken(data._id)
     })

  // res.sendFile(__dirname + "/student.html");
  }
  else{
    res.status(400).send("user already exists");
  }
  // console.log(user);
  }

  if (req.body.role == "faculty") {
    let user = await faculty.findOne({ email: req.body.email });
    if (!user) {
      let fname = req.body.name;
      let femail = req.body.email;
      let fpassword = req.body.password;
      const salt = await bcrypt.genSalt(10);
      const hashedpassword = await bcrypt.hash(fpassword, salt);
      let data = new faculty({
        name: fname,
        email: femail,
        password: hashedpassword,
      });
      let response = await data.save();
      res.json({
      role:"faculty",
        _id: data._id,
        name: data.name,
        email: data.email,
        token: generateToken(data._id),
      });
      // res.sendFile(__dirname + "/faclulty.html");
    } else {
      res.status(400).send("user already exists");
    }
    // console.log("faculty", user);
  }

  //express-session
};

const studentAdd = async (req, res) => {
console.log("mkc");

console.log(req.body);

  let sub = req.body.subject;
  let stu_mail = req.user.email;
  let a = await course.findOne({ sub_name: String(sub) });
console.log(a);
  if (a) {
    let prof_mail = a.email;
    let data = new record({
      st_email: stu_mail,
      prof_email: prof_mail,
      subject: String(sub),
      status: "pending instructor approval",
    });
    let response = await data.save();
    // console.log(data);
  } else {
    console.log("course not available");
  }
};

const courseadd = async (req, res) => {
  console.log(req.body);
  let sub = req.body.subject;
  let prof_name = req.user.name;
  let prof_mail = req.user.email;
  let a = await subject.findOne({ name: String(sub) });
  // console.log(a);
  if (a) {
    let data = new course({ sub_name: sub, name: prof_name, email: prof_mail });
    let response = await data.save();
    // subject.deleteOne({ name: String(sub) }, function (err, obj) {});
    // console.log(data);
  }
};



const facapproval = async (req, res) => {
  let sub = req.body.subject;
  let prof_mail = req.user.email;
  let stu_mail = req.body.studemail;
  let resp = String(req.body.response);
  console.log(resp);
  let a = await record.findOne({
    st_email: stu_mail,
    prof_email: prof_mail,
    subject: sub,
  });
  // console.log(a);
  if (a) {
    if (resp == "1") {
      a.status = "pending advisor approval";
    } else {
      a.status = "request rejected by the instructor";
    }
    a.save();
    console.log(a);
  } else {
    console.log("bad request");
  }
};

const advapproval = async (req, res) => {
  let sub = req.body.subject;
  let prof_mail = req.body.profemail;
  let stu_mail = req.body.studemail;
  let resp = String(req.body.response);
  console.log(resp);
  let a = await record.findOne({
    st_email: stu_mail,
    prof_email: prof_mail,
    subject: sub,
  });
  console.log(a);
  if (a) {
    if (resp == "1") {
      a.status = "enrolled";
    } else {
      a.status = "request rejected by the advisor";
    }
    a.save();
    console.log(a);
  } else {
    console.log("bad request");
  }
};

const deletestu = async (req, res) => {
  const goal = await record.findById(req.params.id)

  if (!goal) {
    res.status(400)
    throw new Error('Goal not found')
  }

  // Check for user
  if (!req.user) {
    res.status(401)
    throw new Error('User not found')
  }

  // Make sure the logged in user matches the goal user
  if (goal.st_email !== req.user.email) {
    res.status(401)
    throw new Error('User not authorized')
  }

  await goal.remove()

  res.status(200).json({ id: req.params.id })
};



const deletefac =async (req, res) => {
  const goal = await course.findById(req.params.id)

  if (!goal) {
    res.status(400)
    throw new Error('Goal not found')
  }

  // Check for user
  if (!req.user) {
    res.status(401)
    throw new Error('User not found')
  }

  // Make sure the logged in user matches the goal user
  if (goal.prof_email !== req.user.email) {
    res.status(401)
    throw new Error('User not authorized')
  }

  await goal.remove()

  res.status(200).json({ id: req.params.id })
}

const generateToken = (id) => {
  return jwt.sign({ id }, process.env.JWT_SECRET, { expiresIn: "30d" });
};

module.exports = {
  deletestu,
  listsubjects,
  facrequests,
  listcoursesfac,
  listcourses,
  deletefac,
  liststu,
  listfac,
  studentAdd,
  userLogin,
  courseadd,
  facapproval,
  useradd,
  advapproval,
};
