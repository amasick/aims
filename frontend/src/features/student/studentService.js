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
  console.log(subjectdata);
const data={
  subject: subjectdata.subname,
  email:subjectdata.email
}
  const response = await axios.post(
    API_URL + "student/add",
   data,
    config
  );

  return response.data;
};

const addfacCourse = async (subjectdata, token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  // console.log({ subjectname, email });
  console.log(subjectdata);
const data={
  subject: subjectdata.subname,
}
  const response = await axios.post(
    API_URL + "faculty/add",
   data,
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

  const response = await axios.get(API_URL + "student/list", config);

  return response.data;
};

const getfacCourses = async (token) => {
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

  const response = await axios.get(API_URL + "student/courses/list", config);

  return response.data;
};

const getfacSubjects = async (token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  const response = await axios.get(API_URL + "faculty/subjects/list", config);

  return response.data;
};

// Delete user course
const deleteCourse = async (courseId, token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  const response = await axios.delete(API_URL + "student/" + courseId, config);

  return response.data;
};

const deletefacCourse = async (courseId, token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  const response = await axios.delete(API_URL + "faculty/" + courseId, config);

  return response.data;
};

const getfacRequests = async ( token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  const response = await axios.post(API_URL + "faculty/requests", config);

  return response.data;
};

const approve = async (data, token) => {
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };

  const response = await axios.post(API_URL + "faculty/requests",data, config);

  return response.data;
};


const studenService = {
  addCourse,
  addfacCourse,
  getCourses,
  getfacCourses,
  getfacRequests,
  getfacSubjects,
  deleteCourse,
  deletefacCourse,
  approve,
  getSubjects,
};

export default studenService;
