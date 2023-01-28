import { useDispatch } from "react-redux";
import { addfacCourse } from "../features/student/studentSlice";
import { FaPlus } from "react-icons/fa";

function FacsubjectItem({ facsubject }) {
  const dispatch = useDispatch();

  return (
    <div className="goal">
      {/* <div>{new Date(course.createdAt).toLocaleString('en-US')}</div> */}
      <h2>{facsubject.name}</h2>

      <button
        onClick={() =>
          dispatch(addfacCourse({ subname: facsubject.name }))
        }
        className="close"
        style={{ backgroundColor: "#abdbe3" }}
      >
        Enroll
      </button>
    </div>
  );
}

export default FacsubjectItem;
