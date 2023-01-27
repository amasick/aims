import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import { ToastContainer } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'
import DashBoard from "./pages/dashboard";
import Login from "./pages/login";
import Register from "./pages/register";
import Header  from './components/Headers';
function App() {
  return (
<>
<Router>
<div className="container">
  <Header/>
<Routes>
<Route path="/" element={<DashBoard/>}/>
<Route path="/login" element={<Login/>}/>
<Route path="/register" element={<Register/>}/>

</Routes>
    </div>
</Router>
<ToastContainer />

</>

   
  );
}

export default App;
