import { useDispatch } from 'react-redux'
import { addCourse } from '../features/student/studentSlice'
import { FaPlus } from "react-icons/fa";

function CourseItem({ course }) {
  const dispatch = useDispatch()

  return (
    <div className='goal'>
      <h2>{course.subject}</h2>
      <h3>{course.name}</h3>
      <h4>{course.email}</h4>


      <button onClick={() => dispatch(addCourse(course._id))} className='close'>
      <FaPlus /> Enroll
      </button>
    </div>
  )
}

export default CourseItem
