import axios from "axios";

const API_URL = "/api/users/";

// Create new course
const addCourse = async (subjectdata, token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  // console.log({ subjectname, email });
//   console.log(subjectdata);


  const response = await axios.post(
    API_URL + "facluty/add",
   subjectdata,
    config
  );

  return response.data;
};

// Get user courses
const getCourses = async (token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  const response = await axios.get(API_URL + "faculty/list", config);

  return response.data;
};

// Get all subjects
const getSubjects = async (token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  const response = await axios.get(API_URL + "faculty/courses/list", config);

  return response.data;
};

// Delete user course
const deleteCourse = async (courseId, token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  const response = await axios.delete(API_URL + "faculty/" + courseId, config);

  return response.data;
};
const approve =async (recorddata,token)=>{
    const config = {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      };
      const response = await axios.post(API_URL + "faculty/approval" , config);

      return response.data;
}
const facService = {
  addCourse,
  getCourses,
  deleteCourse,
  getSubjects,
  approve,
};

export default facService;
