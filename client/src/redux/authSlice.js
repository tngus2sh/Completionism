import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  userName: null,
  provider: null,
  email: null,
  isAuthenticated: false,
};

export const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    loginState: (state, action) => {
        state = state
    },
    logoutState: (state) => {
        state = state
    },

  },
});

export const { loginState, logoutState } = authSlice.actions;

export default authSlice.reducer;
