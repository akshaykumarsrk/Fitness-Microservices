import { createSlice } from '@reduxjs/toolkit'

const authSlice = createSlice({
  name: 'auth',
  initialState : {
    user: JSON.parse(localStorage.getItem('user')) || null,
    token: localStorage.getItem('token') || null,
    userId: localStorage.getItem('userId') || null,
  },
  reducers: {
    // what data we get in action payload
    // take that data and set in local storage
    setCredentials : (state, action) => {

      // get from payload
      state.user = action.payload.user;
      state.token = action.payload.token;
      state.userId = action.payload.user.sub; // user.sub because token payload has sub field in which userId (keycloak userId) is there

      // set in local storage
      localStorage.setItem('token', action.payload.token);
      localStorage.setItem('user', JSON.stringify(action.payload.user));
      localStorage.setItem('userId', action.payload.user.sub);
    },
    logout : (state) => {

      // unset the state
      state.user = null;
      state.token = null;
      state.userId = null;

      // remove from local storage
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      localStorage.removeItem('userId');
    },
  },
})

export const { setCredentials, logout } = authSlice.actions;
export default authSlice.reducer;