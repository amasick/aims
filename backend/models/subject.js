
const mongoose = require("mongoose");

// const conn = require("../config/db");



var subjectschema = new mongoose.Schema(
  {
 
   
    name:{ 
        type:String
  },
  status:{
    type:String,
    default:"not alloted"
  }

},
  {
    timestamps: true,
  }
);

let subject = mongoose.model("subjects", subjectschema);

module.exports = subject;
