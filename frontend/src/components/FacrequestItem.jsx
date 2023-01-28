import { useDispatch } from 'react-redux'
import { approve } from '../features/student/studentSlice'

function FacrequestItem({ facrequest }) {
  const dispatch = useDispatch()

  return (
    <div className='goal'>
      <div>{new Date(facrequest.createdAt).toLocaleString('en-US')}</div>
      <h2>{facrequest.subject}</h2>
      {/* <h2>{course.status}</h2> */}

      <button onClick={() => dispatch(approve({subname: facrequest.subject, st_email:facrequest.st_email, resp: "1"}))} className='close'>
         Decline
      </button>
      <button onClick={() => dispatch(approve({subname: facrequest.subject, st_email:facrequest.st_email, resp: "1"}))} className='close'>
         Accept
      </button>
     
    </div>
  )
}

export default FacrequestItem
