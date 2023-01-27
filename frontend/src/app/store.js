import { configureStore } from '@reduxjs/toolkit';
import authreducer from '../features/auth/authSlice';
// import coursereducer from '../features/student/studentSlice';

export const store = configureStore({
  reducer: {
    auth: authreducer,
    // courses: coursereducer
  },
});
