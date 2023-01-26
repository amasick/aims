
const mongoose = require("mongoose");
// const bcrypt=require("bcryptjs");
// const conn = require("../config/db");



var facultyschema = new mongoose.Schema(
  {
    name: {
        type:String
    },
    email:{
        type:String,
        unique:true
    } ,
    password:{ 
      type:String,
      select: true

  }
},
  {
    timestamps: true,
  }
);
// facultyschema.pre('save',function(next){
//   var salt=bcrypt.genSaltSync(10);
//   this.password=bcrypt.hashSync(this.password,salt);
//   console.log(this.password);
//   next;
// })
let faculty = mongoose.model("faculties", facultyschema);


module.exports = faculty;
