
const mongoose = require("mongoose");

// const conn = require("../config/db");



var recordschema = new mongoose.Schema(
  {
    st_email: {
        type:String
    },
    prof_email:{
        type:String
    } ,
    subject:{ 
        type:String
  },
  status:{
    type:String,
    default:"pending advisor approval"
  }
},
  {
    timestamps: true,
  }
);

let record = mongoose.model("records", recordschema);

module.exports = record;
