import { useDispatch } from "react-redux";
import { addCourse } from "../features/student/studentSlice";
import { FaPlus } from "react-icons/fa";

function SubjectItem({ setforeignhook, offer }) {
  const dispatch = useDispatch();

  return (
    <div className="goal">
      {/* <div>{new Date(course.createdAt).toLocaleString('en-US')}</div> */}
      <h2>{offer.sub_name}</h2>
      <h2>Intructor: {offer.name}</h2>

      <button
        onClick={() =>
          dispatch(addCourse({ subname: offer.sub_name, email: offer.email }))
        }
        className="close"
        style={{ backgroundColor: "#abdbe3" }}
      >
        Enroll
      </button>
    </div>
  );
}

export default SubjectItem;
