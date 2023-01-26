import { useState, useEffect } from 'react'
// import { useSelector, useDispatch } from 'react-redux'
// import { useNavigate } from 'react-router-dom'
// import { toast } from 'react-toastify'
import { FaSignInAlt } from 'react-icons/fa'
// import { register, reset } from '../features/auth/authSlice'
// import Spinner from '../components/Spinner'

function Login() {
  const [formData, setFormData] = useState({
    role: '',
    email: '',
    password: '',
  })

  const { role, email,  password } = formData

  // const navigate = useNavigate()
  // const dispatch = useDispatch()

  // const { user, isLoading, isError, isSuccess, message } = useSelector(
  //   (state) => state.auth
  // )

  // useEffect(() => {
  //   if (isError) {
  //     toast.error(message)
  //   }

  //   if (isSuccess || user) {
  //     navigate('/')
  //   }

  //   dispatch(reset())
  // }, [user, isError, isSuccess, message, navigate, dispatch])

  const onChange = (e) => {
    setFormData((prevState) => ({
      ...prevState,
      [e.target.name]: e.target.value,
    }))
  }

  const onSubmit = (e) => {
    e.preventDefault()

    // if (password !== password2) {
    //   toast.error('Passwords do not match')
    // } else {
    //   const userData = {
    //     name,
    //     email,
    //     password,
    //   }

    //   dispatch(register(userData))
    // }
  }

  // if (isLoading) {
  //   return <Spinner />
  // }

  return (
    <>
      <section className='heading'>
        <h1>
          <FaSignInAlt /> Login
        </h1>
        <p>Please add your login info</p>
      </section>

      <section className='form'>
        <form onSubmit={onSubmit}>
          
        <div className='form-group'>

        <select value={role} onChange={onChange}>
        <option value="student">student</option>
        <option value="faculty">faculty</option>
        <option value="advisor">advisor</option>
      </select>
  
          </div>
          <div className='form-group'>
            <input
              type='email'
              className='form-control'
              id='email'
              name='email'
              value={email}
              placeholder='Enter your email'
              onChange={onChange}
            />
          </div>
      
          <div className='form-group'>
            <input
              type='password'
              className='form-control'
              id='password'
              name='password'
              value={password}
              placeholder='Enter password'
              onChange={onChange}
            />
          </div>
        
          <div className='form-group'>
            <button type='submit' className='btn btn-block'>
              Submit
            </button>
          </div>
        </form>
      </section>
    </>
  )
}

export default Login