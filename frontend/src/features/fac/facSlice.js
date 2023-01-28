import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import facService from "./facService";

const initialState = {
  requests: [],
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
      console.log({subjectdata,token});
      return await facService.addCourse(subjectdata, token);
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

// Get prof courses
export const getCourses = createAsyncThunk(
  "courses/getAll",
  async (_, thunkAPI) => {
    try {
      const token = thunkAPI.getState().auth.user.token;
      return await facService.getCourses(token);
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
      return await facService.getSubjects(token);
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
      return await facService.deleteCourse(id, token);
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
    "course/delete",
    async (val, thunkAPI) => {
      try {
        const token = thunkAPI.getState().auth.user.token;
        return await facService.approve(val, token);
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

 

export const facSlice = createSlice({
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
        state.isLoading = true;
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
      .addCase(approve.pending, (state) => {
        state.isLoading = true;
      })
      .addCase(approve.fulfilled, (state, action) => {
        state.isLoading = false;
        state.isSuccess = true;
        state.offers = action.payload;
      })
      .addCase(approve.rejected, (state, action) => {
        state.isLoading = false;
        state.isError = true;
        state.message = action.payload;
      });
  },
});

export const { reset } = facSlice.actions;
export default facSlice.reducer;
