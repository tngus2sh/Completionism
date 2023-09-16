import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  userName: null,
  isAuthenticated: false,
  selectedYearAndMonth : null,
  fixedExpenditureList : [] , //고정지출
  FutureExpenditureList : [] , //미래소비
  MonthTransactionData : [], //달력에 띄울 수입지출데이터
  MonthHistoryData : [],  //계좌 상세조회
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
    },
    fatchMonthHistoryData: (state,action) => {
      if (action.payload !== null){
        state.MonthHistoryData = action.payload;
      }
    }
  },
});

export const { loginState, logoutState ,setSelectedYearAndMonth ,fatchFutureData ,fatchPinnedData,
  setIsDiary,fatchMonthTransactionData,fatchMonthHistoryData} = authSlice.actions;

export default authSlice.reducer;
