import { useDispatch } from 'react-redux'
import { deleteCourse } from '../features/student/studentSlice'

function CourseItem({ course }) {
  const dispatch = useDispatch()

  return (
    <div className='goal'>
      <div>{new Date(course.createdAt).toLocaleString('en-US')}</div>
      <h2>{course.subject}</h2>
      <h2>{course.status}</h2>

      <button onClick={() => dispatch(deleteCourse(course._id))} className='close'>
        X
      </button>
    </div>
  )
}

export default CourseItem
