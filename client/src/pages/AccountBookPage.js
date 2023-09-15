import React, { useEffect } from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import "./AccountBookPage.css";
import { Calender } from "../components/Calendar";
import SwipeableTemporaryDrawer from "../components/bottomDrawer";
import { Link } from "react-router-dom";
import { setIsDiary } from "../redux/authSlice";
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux/es/hooks/useSelector";
import { useState } from "react";
import axios from "axios";
import { fatchMonthTransactionData } from "../redux/authSlice";

const AccountBookPage = () => {
  const upperNavbarName = "가계부";
  const isDiary = useSelector((state) => state.auth.isDiary);
  const selectedYearAndMonth = useSelector(
    (state) => state.auth.selectedYearAndMonth
  );
  const MonthTransactionData = useSelector(
    (state) => state.auth.MonthTransactionData
  );
  const [useAxios, setUseAxios] = useState(false);

  useEffect(() => {
    loadData();
  }, []);

  const dispatch = useDispatch();

  // const monthlyAccountBookData = {
  //   startDay: "2023-08-01",
  //   endDay: "2023-08-31",
  //   income: 3000000,
  //   spend: 2800000,
  //   day: [
  //     {
  //       day: "2023-08-01",
  //       income: 10000,
  //       spend: 3000,
  //     },
  //     {
  //       day: "2023-08-15",
  //       income: 10000,
  //       spend: 3000,
  //     },
  //   ],
  // };
  
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
    const lastDayOfMonth = `${year}-${month}-${new Date(
      year,
      month,
      0
    ).getDate()}`;
    try {
      const response = await axios.get(
        `/api/history/${firstDayOfMonth}_${lastDayOfMonth}`,
        { headers }
      );
      console.log(response.data);
      dispatch(fatchMonthTransactionData(response.data.dataBody));
    } catch (error) {
      console.error(error);
    }
  };

  const ToggleCalendar = () => {
    dispatch(setIsDiary());
  };

  return (
    <div className="accountbook_page">
      <div className="uppernavbar">
        <UpperNavigationBar props={upperNavbarName} />
      </div>

      <div className="progressive_bar"></div>

      <div className="upper_information_box" container></div>

      <div className="toggle_container" container>
        <button onClick={ToggleCalendar}>
          <div>{isDiary ? "일기달력" : "가계부달력"}</div>
        </button>
      </div>

      <div className="calander_container" container>
        {isDiary ? (
          <Calender isDiary={isDiary} props={diaryData} />
        ) : (
          <Calender isDiary={isDiary} props={MonthTransactionData} />
        )}
      </div>

      <div>
        <div>
          <SwipeableTemporaryDrawer />
        </div>
        <div>
          <Link to="/future">미래예상소비등록</Link>
        </div>
        <div>
          <Link to="/fixed">고정지출등록</Link>
        </div>
      </div>

      <div className="undernavbar">
        <UnderNavigationBar />
      </div>
    </div>
  );
};

export default AccountBookPage;
