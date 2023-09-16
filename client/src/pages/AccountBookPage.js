import React, { useEffect } from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import "./AccountBookPage.css";
import { Calendar } from "../components/Calendar";
import { CalenderForDiary } from "../components/CalendarForDiary";
import SwipeableTemporaryDrawer from "../components/bottomDrawer";
import { Link } from "react-router-dom";
import { setIsDiary } from "../redux/authSlice";
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux/es/hooks/useSelector";
import { useState } from "react";
import axios from "axios";
import { fatchMonthTransactionData } from "../redux/authSlice";
import DoneRoundedIcon from "@mui/icons-material/DoneRounded";

const AccountBookPage = () => {
  const upperNavbarName = "가계부";
  const isDiary = useSelector((state) => state.auth.isDiary);
  const selectedYearAndMonth = useSelector((state) => state.auth.selectedYearAndMonth);
  const MonthTransactionData = useSelector((state) => state.auth.MonthTransactionData);

  const dispatch = useDispatch();

  const diaryData = {
    startDay: "2023-08-01",
    endDay: "2023-08-31",
    income: "일기데이터",
    spend: null,
    day: [
      {
        day: "2023-08-01",
        income: "일기데이터",
      },
      {
        day: "2023-08-15",
        income: "일기데이터",
      },
    ],
  };

  const loadData = async () => {
    // 로컬 스토리지에서 엑세스 토큰 가져오기
    const accessToken = localStorage.getItem("accessToken");

    // Axios 요청 헤더 설정
    const headers = {
      Authorization: `Bearer ${accessToken}`, // 엑세스 토큰을 Bearer 토큰으로 헤더에 추가
    };
    const year = selectedYearAndMonth.split("-")[0];
    const month = selectedYearAndMonth.split("-")[1];
    const firstDayOfMonth = `${year}-${month}-01`;
    const lastDayOfMonth = `${year}-${month}-${new Date(year, month, 0).getDate()}`;
    try {
      const response = await axios.get(`/api/history/${firstDayOfMonth}_${lastDayOfMonth}`, { headers });
      // console.log(response.data);
      dispatch(fatchMonthTransactionData(response.data.dataBody));
    } catch (error) {
      console.error(error);
    }
  };

  const ToggleCalendar = () => {
    dispatch(setIsDiary());
  };

  function setScreenSize() {
    //먼저 뷰포트 높이를 얻고 1%를 곱하여 vh 단위 값을 얻습니다.
    let vh = window.innerHeight * 0.01;
    //그런 다음 --vh 사용자 정의 속성의 값을 문서의 루트로 설정합니다.
    document.documentElement.style.setProperty("--vh", `${vh}px`);
  }
  setScreenSize();
  window.addEventListener("resize", setScreenSize);

  return (
    <div className="accountbook-page">
      <div className="uppernavbar">
        <UpperNavigationBar props={upperNavbarName} />
      </div>

      <div className="progressive_bar"></div>

      <div className="accountbook-info-container">
        <div style={{ marginTop: "1.7rem" }}>
          <DoneRoundedIcon sx={{ fontSize: "1.2rem" }} />
          <span>현재 잔액 : 원</span>
        </div>
        <div style={{ marginBottom: "1.7rem" }}>
          <DoneRoundedIcon sx={{ fontSize: "1.2rem" }} />
          <span>이번 달 나갈 예정인 금액 : 원</span>
        </div>
      </div>

      <div className="toggle-container">
        <button className="toggle-button" onClick={ToggleCalendar}>
          <div>{isDiary ? "일기달력" : "가계부달력"}</div>
        </button>
      </div>

      <div className="calendar-container">{isDiary ? <CalenderForDiary /> : <Calendar />}</div>

      <div className="accountbook-button-container">
        {/* <div>
          <SwipeableTemporaryDrawer />
        </div> */}
        <div className="accountbook-button">
          <Link to="/future" className="accountbook-link">
            미래 예상소비 등록
          </Link>
        </div>
        <div className="accountbook-button">
          <Link to="/fixed" className="accountbook-link">
            고정 지출 등록
          </Link>
        </div>
      </div>

      <div className="undernavbar">
        <UnderNavigationBar />
      </div>
    </div>
  );
};

export default AccountBookPage;
