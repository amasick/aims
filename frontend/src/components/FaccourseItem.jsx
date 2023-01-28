import { useDispatch } from 'react-redux'
import { deletefacCourse } from '../features/student/studentSlice'

function FaccourseItem({ faccourse }) {
  const dispatch = useDispatch()

  return (
    <div className='goal'>
      <div>{new Date(faccourse.createdAt).toLocaleString('en-US')}</div>
      <h2>{faccourse.sub_name}</h2>
      {/* <h2>{course.status}</h2> */}

      <button onClick={() => dispatch(deletefacCourse(faccourse._id))} className='close'>
        X
      </button>
    </div>
  )
}

export default FaccourseItem
