import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import studentService from "./studentService";

const initialState = {
  courses: [],
  offers: [],
  faccourses:[],
  facrequests:[],
  facsubjects:[],
  isError: false,
  isSuccess: false,
  isLoading: false,
  message: "",
};

// add new course
export const addCourse = createAsyncThunk(
  "courses/add",
  async (subjectdata, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token;
      // console.log({subjectdata,token});
      return await studentService.addCourse(subjectdata, token);
    } catch (error) {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString();
      return thunkAPI.rejectWithValue(message);
    }
  }
);
// add fac course
export const addfacCourse = createAsyncThunk(
  "faculty/add",
  async (subjectdata, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token;
      // console.log({subjectdata,token});
      return await studentService.addfacCourse(subjectdata, token);
    } catch (error) {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString();
      return thunkAPI.rejectWithValue(message);
    }
  }
);

// Get user courses
export const getCourses = createAsyncThunk(
  "courses/getAll",
  async (_, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token;
      return await studentService.getCourses(token);
    } catch (error) {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString();
      return thunkAPI.rejectWithValue(message);
    }
  }
);

export const getfacCourses = createAsyncThunk(
  "faculty/getAll",
  async (_, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token;
      return await studentService.getfacCourses(token);
    } catch (error) {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString();
      return thunkAPI.rejectWithValue(message);
    }
  }
);


// Get all subjects
export const getSubjects = createAsyncThunk(
  "saubjects/getAll",
  async (_, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token;
      return await studentService.getSubjects(token);
    } catch (error) {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString();
      return thunkAPI.rejectWithValue(message);
    }
  }
);

export const getfacSubjects = createAsyncThunk(
  "faculty/subjects",
  async (_, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token;
      return await studentService.getfacSubjects(token);
    } catch (error) {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString();
      return thunkAPI.rejectWithValue(message);
    }
  }
);

export const getfacRequests = createAsyncThunk(
  "faculty/requests",
  async (_, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token;
      return await studentService.getfacRequests(token);
    } catch (error) {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString();
      return thunkAPI.rejectWithValue(message);
    }
  }
);

// Delete user course
export const deleteCourse = createAsyncThunk(
  "course/delete",
  async (id, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token;
      return await studentService.deleteCourse(id, token);
    } catch (error) {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString();
      return thunkAPI.rejectWithValue(message);
    }
  }
);

export const deletefacCourse = createAsyncThunk(
  "faccourse/delete",
  async (id, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token;
      return await studentService.deletefacCourse(id, token);
    } catch (error) {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString();
      return thunkAPI.rejectWithValue(message);
    }
  }
);

export const approve = createAsyncThunk(
  "course/approve",
  async (resp, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token;
      return await studentService.approve(resp, token);
    } catch (error) {
      const message =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString();
      return thunkAPI.rejectWithValue(message);
    }
  }
);
export const studentSlice = createSlice({
  name: "course",
  initialState,
  reducers: {
    reset: (state) => initialState,
  },
  extraReducers: (builder) => {
    builder
      .addCase(addCourse.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(addCourse.fulfilled, (state, action) => {
        state.isLoading = false;
        state.isSuccess = true;
        // state.subject = action.payload;
      })
      .addCase(addCourse.rejected, (state, action) => {
        state.isLoading = false;
        state.isError = true;
        state.message = action.payload;
      })
      .addCase(getCourses.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(getCourses.fulfilled, (state, action) => {
        state.isLoading = false;
        state.isSuccess = true;
        state.courses = action.payload;
      })
      .addCase(getCourses.rejected, (state, action) => {
        state.isLoading = false;
        state.isError = true;
        state.message = action.payload;
      })
      .addCase(deleteCourse.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(deleteCourse.fulfilled, (state, action) => {
        state.isLoading = false;
        state.isSuccess = true;
        state.courses = state.courses.filter(
          (course) => course._id !== action.payload.id
        );
      })
      .addCase(deleteCourse.rejected, (state, action) => {
        state.isLoading = false;
        state.isError = true;
        state.message = action.payload;
      })
      .addCase(getSubjects.pending, (state) => {

        // state.isLoading = true;
      })
      .addCase(getSubjects.fulfilled, (state, action) => {
        state.isLoading = false;
        state.isSuccess = true;
        state.offers = action.payload;
      })
      .addCase(getSubjects.rejected, (state, action) => {
        state.isLoading = false;
        state.isError = true;
        state.message = action.payload;
      })
      .addCase(getfacCourses.pending, (state) => {

        state.isLoading = true;
      })
      .addCase(getfacCourses.fulfilled, (state, action) => {
        state.isLoading = false;
        state.isSuccess = true;
        state.faccourses = action.payload;
      })
      .addCase(getfacCourses.rejected, (state, action) => {
        state.isLoading = false;
        state.isError = true;
        state.message = action.payload;
      })
      .addCase(getfacSubjects.pending, (state) => {

        state.isLoading = true;
      })
      .addCase(getfacSubjects.fulfilled, (state, action) => {
        state.isLoading = false;
        state.isSuccess = true;
        state.facsubjects = action.payload;
      })
      .addCase(getfacSubjects.rejected, (state, action) => {
        state.isLoading = false;
        state.isError = true;
        state.message = action.payload;
      })
      .addCase(getfacRequests.pending, (state) => {

        state.isLoading = true;
      })
      .addCase(getfacRequests.fulfilled, (state, action) => {
        state.isLoading = false;
        state.isSuccess = true;
        state.facrequests = action.payload;
      })
      .addCase(getfacRequests.rejected, (state, action) => {
        state.isLoading = false;
        state.isError = true;
        state.message = action.payload;
      })
      .addCase(deletefacCourse.pending, (state) => {

        state.isLoading = true;
      })
      .addCase(deletefacCourse.fulfilled, (state, action) => {
        state.isLoading = false;
        state.isSuccess = true;
        state.faccourses = state.faccourses.filter(
          (faccourse) => faccourse._id !== action.payload.id
        );
      })
      .addCase(deletefacCourse.rejected, (state, action) => {
        state.isLoading = false;
        state.isError = true;
      })
      .addCase(approve.pending, (state) => {

        state.isLoading = true;
      })
      .addCase(approve.fulfilled, (state, action) => {
        state.isLoading = false;
        state.isSuccess = true;
      })
      .addCase(approve.rejected, (state, action) => {
        state.isLoading = false;
        state.isError = true;
        state.message = action.payload;
      })
      .addCase(addfacCourse.pending, (state) => {

        state.isLoading = true;
      })
      .addCase(addfacCourse.fulfilled, (state, action) => {
        state.isLoading = false;
        state.isSuccess = true;
      })
      .addCase(addfacCourse.rejected, (state, action) => {
        state.isLoading = false;
        state.isError = true;
        state.message = action.payload;
      });
  },
});

export const { reset } = studentSlice.actions;
export default studentSlice.reducer;
