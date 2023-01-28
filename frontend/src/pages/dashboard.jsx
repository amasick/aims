import { useEffect } from "react";
import { FaPlus } from "react-icons/fa";
import { Link, useNavigate } from "react-router-dom";

import { useSelector, useDispatch } from "react-redux";
import CourseItem from "../components/CourseItem";
import SubjectItem from "../components/SubjectItem";

import Spinner from "../components/Spinner";
import {
  getCourses,
  getSubjects,
  reset,
} from "../features/student/studentSlice";

function Dashboard() {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const { user } = useSelector((state) => state.auth);
  const { courses, offers, isLoading, isError, message } = useSelector(
    (state) => state.courses
  );
  // const subjects = useSelector((state) => state.subjects);

  // console.log(offers);

  useEffect(() => {
    if (isError) {
      console.log(message);
    }

    if (!user) {
      navigate("/login");
    }
    dispatch(getSubjects());

    dispatch(getCourses());

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
        <div>
          <h1>faculty</h1>
        </div>
      )}
    </>
  );
}

export default Dashboard;
