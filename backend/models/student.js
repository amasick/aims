const mongoose = require("mongoose");
// const bcrypt=require("bcryptjs");
// const conn = require("../config/db");
// const jwt=require("jsonwebtoken"); 
var studenschema = new mongoose.Schema(
  {
    name: String,
    email:{type:String,
      unique:true
    },
    password: {
      type:String,
      select: true
  }
},
  {
    timestamps: true,
  }
);
// studenschema.pre('save',function(next){
//   var salt=bcrypt.genSaltSync(10);
//   this.password=bcrypt.hashSync(this.password,salt);
//   console.log(this.password);
//   next;
// })
// studenschema.methods.getAuthToken=function(data){

// }
let students = mongoose.model("students", studenschema);

module.exports = students;
