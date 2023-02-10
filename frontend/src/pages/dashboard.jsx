import { useEffect } from "react";
import { FaPlus } from "react-icons/fa";
import { Link, useNavigate } from "react-router-dom";

import { useSelector, useDispatch } from "react-redux";
import CourseItem from "../components/CourseItem";
import SubjectItem from "../components/SubjectItem";
import FacsubjectItem from "../components/FacsubjectItem";
import FaccourseItem from "../components/FaccourseItem";
import FacrequestItem from "../components/FacrequestItem";




import Spinner from "../components/Spinner";
import {
  getCourses,
  getfacSubjects,
  getfacCourses,
  getfacRequests,
  getSubjects,
  reset,
} from "../features/student/studentSlice";



function Dashboard() {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const { user } = useSelector((state) => state.auth);
  const { courses, offers,faccourses,facrequests, facsubjects,isLoading, isError, message } = useSelector(
    (state) => state.courses
  );
 
  useEffect(() => {
    if (isError) {
      console.log(message);
    }

    if (!user) {
      navigate("/login");
    }
    if(user != null && String(user.role)=="student"){
    dispatch(getSubjects());

    dispatch(getCourses());
    }

    if(user != null && String(user.role)=="faculty"){
      dispatch(getfacSubjects());
  
      dispatch(getfacCourses());
      // dispatch(getfacRequests());
      // dispatch(getfacRequests());


      }
    return () => {
      dispatch(reset());
    };
  }, [user, navigate, isError, message, dispatch]);

  if (isLoading) {
    return <Spinner />;
  }

  return (
    <>
      {user && String(user.role) == "student" ? (
        <div>
          <div>
            <section className="heading">
              <h1>Welcome {user && user.name}</h1>
              <p>Your Courses</p>
            </section>

            <section className="content enrollments">
              {courses.length > 0 ? (
                <div className="goals">
                  {courses.map((course) => (
                    <CourseItem key={course._id} course={course} />
                  ))}
                </div>
              ) : (
                <h3>You are not enrolled in any courses</h3>
              )}
            </section>
            <section className="heading">
              <p>Offered Courses</p>
            </section>
            <section className="content offers">
              {offers.length > 0 ? (
                <div className="goals">
                  {offers.map((offer) => (
                    <SubjectItem key={offer._id} offer={offer} />
                  ))}
                </div>
              ) : (
                <h3>no courses are available for enrollments</h3>
              )}
            </section>
          </div>
        </div>
      ) : (
        <div className="faculty">
           <section className="heading">
              <h1>Welcome {user && user.name}</h1>
              <p>Add Courses</p>
            </section>
            <section className="content offerings">
              {facsubjects.length > 0 ? (
                <div className="goals">
                  {facsubjects.map((facsubject) => (
                    <FacsubjectItem key={facsubject._id} facsubject={facsubject} />
                  ))}
                </div>
              ) : (
                <h3>No subjects yet</h3>
              )}
            </section>

            <section className="heading">
              <p>Your Courses</p>
            </section>

            <section className="content courses">
              {faccourses.length > 0 ? (
                <div className="goals">
                  {faccourses.map((faccourse) => (
                    <FaccourseItem key={faccourse._id} faccourse={faccourse} />
                  ))}
                </div>
              ) : (
                <h3>No courses enrolled yet</h3>
              )}
            </section>


            <section className="heading">
              <p>Requests </p>
            </section>
            
            <section className="content courses">
              {facrequests.length > 0 ? (
                <div className="goals">
                  {facrequests.map((facrequest) => (
                    <FacrequestItem key={facrequest._id} facrequest={facrequest} />
                  ))}
                </div>
              ) : (
                <h3>No enrollment requests  yet</h3>
              )}
            </section>


        </div>
      )}
    </>
  );
}

export default Dashboard;
