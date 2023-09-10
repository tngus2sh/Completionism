import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  userName: null,
  isAuthenticated: false,
};

export const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    loginState: (state, ) => {
        state.isAuthenticated = true
    },
    logoutState: (state) => {
      state.isAuthenticated = false
    },

  },
});

export const { loginState, logoutState } = authSlice.actions;

export default authSlice.reducer;
