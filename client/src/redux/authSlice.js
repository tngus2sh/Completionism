import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  userName: null,
  isAuthenticated: false,
  selectedYearAndMonth : null,
  fixedExpenditureList : [] ,
  FutureExpenditureList : [] ,
  MonthTransactionData : [],
  isDiary : false,
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
    },
    fatchFutureData: (state,action) => {
      state.FutureExpenditureList = action.payload
    },
    fatchPinnedData: (state,action) => {
      state.fixedExpenditureList = action.payload
    },
    setIsDiary: (state) => {
      state.isDiary = !state.isDiary
    },
    fatchMonthTransactionData: (state,action) => {
      state.MonthTransactionData = action.payload;
    }
  },
});

export const { loginState, logoutState ,setSelectedYearAndMonth ,fatchFutureData ,fatchPinnedData,setIsDiary,fatchMonthTransactionData} = authSlice.actions;

export default authSlice.reducer;
