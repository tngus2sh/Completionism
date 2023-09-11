import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  userName: null,
  isAuthenticated: false,
  selectedYearAndMonth : null,
  fixedExpenditureList : [] ,
  FutureExpenditureList : [] ,
};

export const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    loginState: (state) => {
        state.isAuthenticated = true
    },
    logoutState: (state) => {
      state.isAuthenticated = false
    },
    setSelectedYearAndMonth: (state,action) => {
      state.selectedYearAndMonth = action.payload
    }

  },
});

export const { loginState, logoutState ,setSelectedYearAndMonth } = authSlice.actions;

export default authSlice.reducer;
