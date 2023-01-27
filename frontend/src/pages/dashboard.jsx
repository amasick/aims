import { useEffect } from "react";
import { FaPlus } from "react-icons/fa";
import { Link, useNavigate } from "react-router-dom";

import { useSelector, useDispatch } from "react-redux";
// import addcourse from '../components/addcourse'
// import courseitem from '../components/courseitem'
// import Spinner from "../components/Spinner";
// import { getCourses, reset } from '../features/student/studentSlice'

function Dashboard() {
  const navigate = useNavigate()
  const dispatch = useDispatch()

  const { user } = useSelector((state) => state.auth);
  // const { course, isLoading, isError, message } = useSelector(
  //   (state) => course.goals
  // )

  // useEffect(() => {
  //   // if (isError) {
  //   //   console.log(message)
  //   // }

  //   if (!user) {
  //     navigate('/login')
  //   }

  //   // dispatch(getGoals())

  //   // return () => {
  //   //   dispatch(reset())
  //   // }
  // }, [user, navigate])

  // if (isLoading) {
  //   return <Spinner />
  // }

  return (
    <>
      {user && String(user.role) == "student" ? (
        <div>
          <div>
            <section className="heading">
              <h1>Welcome {user && user.name}</h1>
              <p>Courses Dashboard</p>
            </section>
            <div>
              <ul>
                <Link to="/add">
                  <button>
                    <FaPlus /> Add Courses
                  </button>
                </Link>
              </ul>
            </div>

            {/* <h1>Add Courses</h1> */}

            <h1>Your Courses</h1>

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
