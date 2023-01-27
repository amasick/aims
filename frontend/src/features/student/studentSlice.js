import { createSlice, createAsyncThunk } from '@reduxjs/toolkit'
import studentService from './studentService'

const initialState = {
  courses: [],
  isError: false,
  isSuccess: false,
  isLoading: false,
  message: '',
}

// add new course
export const addCourse = createAsyncThunk(
  'courses/add',
  async (courseData, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token
      return await studentService.addCourse(courseData, token)
    } catch (error) {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString()
      return thunkAPI.rejectWithValue(message)
    }
  }
)

// Get user courses
export const getCourses = createAsyncThunk(
  'courses/getAll',
  async (_, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token
      return await studentService.getCourses(token)
    } catch (error) {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString()
      return thunkAPI.rejectWithValue(message)
    }
  }
)

// Delete user course
export const deleteCourse = createAsyncThunk(
  'course/delete',
  async (id, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token
      return await studentService.deleteCourse(id, token)
    } catch (error) {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString()
      return thunkAPI.rejectWithValue(message)
    }
  }
)

export const studentSlice = createSlice({
  name: 'course',
  initialState,
  reducers: {
    reset: (state) => initialState,
  },
  extraReducers: (builder) => {
    builder
      .addCase(addCourse.pending, (state) => {
        state.isLoading = true
      })
      .addCase(addCourse.fulfilled, (state, action) => {
        state.isLoading = false
        state.isSuccess = true
        state.courses.push(action.payload)
      })
      .addCase(addCourse.rejected, (state, action) => {
        state.isLoading = false
        state.isError = true
        state.message = action.payload
      })
      .addCase(getCourses.pending, (state) => {
        state.isLoading = true
      })
      .addCase(getCourses.fulfilled, (state, action) => {
        state.isLoading = false
        state.isSuccess = true
        state.courses = action.payload
      })
      .addCase(getCourses.rejected, (state, action) => {
        state.isLoading = false
        state.isError = true
        state.message = action.payload
      })
      .addCase(deleteCourse.pending, (state) => {
        state.isLoading = true
      })
      .addCase(deleteCourse.fulfilled, (state, action) => {
        state.isLoading = false
        state.isSuccess = true
        state.courses = state.courses.filter(
          (course) => course._id !== action.payload.id
        )
      })
      .addCase(deleteCourse.rejected, (state, action) => {
        state.isLoading = false
        state.isError = true
        state.message = action.payload
      })
  },
})

export const { reset } = studentSlice.actions
export default studentSlice.reducer
