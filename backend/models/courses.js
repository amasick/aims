
const mongoose = require("mongoose");

// const conn = require("../config/db");



var courseschema = new mongoose.Schema(
  {
    sub_name: {
        type:String,
    },
    name: {
      type:String
  },
  email:{
    type:String
  }
    
},
  {
    timestamps: true,
  }
);

let course = mongoose.model("courses", courseschema);

module.exports = course;
