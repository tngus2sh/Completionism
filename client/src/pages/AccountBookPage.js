import { Grid } from "@mui/material";
import React from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import "./AccountBookPage.css";
import { Calender } from "../components/Calendar";
import SwipeableTemporaryDrawer from "../components/bottomDrawer";
import { Link } from "react-router-dom";
import { setIsDiary } from "../redux/authSlice";
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux/es/hooks/useSelector";

const AccountBookPage = () => {
  const upperNavbarName = "가계부";
  const dispatch = useDispatch();

  const monthlyAccountBookData = {
    startDay: "2023-08-01",
    endDay: "2023-08-31",
    income: 3000000,
    spend: 2800000,
    day: [
      {
        day: "2023-08-01",
        income: 10000,
        spend: 3000,
      },
      {
        day: "2023-08-15",
        income: 10000,
        spend: 3000,
      },
    ],
  };

  const diaryData = {
    startDay: "2023-08-01",
    endDay: "2023-08-31",
    income: "일기데이터에요",
    spend: "일기데이터에요",
    day: [
      {
        day: "2023-08-01",
        income: "일기데이터에요",
        spend: "일기데이터에요",
      },
      {
        day: "2023-08-15",
        income: "일기데이터에요",
        spend: "일기데이터에요",
      },
    ],
  };

  const isDiary = useSelector((state) => state.auth.isDiary);

  const ToggleCalendar = () => {
    dispatch(setIsDiary());
  };

  return (
    <Grid className="accountbook_page">
      <Grid className="uppernavbar">
        <UpperNavigationBar props={upperNavbarName} />
      </Grid>

      <Grid className="progressive_bar"></Grid>

      <Grid className="upper_information_box" container></Grid>

      <Grid className="toggle_container" container>
        <button onClick={ToggleCalendar}>
          <div>{isDiary ? "일기달력" : "가계부달력"}</div>
        </button>
      </Grid>

      <Grid className="calander_container" container>
        {isDiary ? (
          <Calender isDiary={isDiary} props={diaryData} />
        ) : (
          <Calender isDiary={isDiary} props={monthlyAccountBookData} />
        )}
      </Grid>

      <Grid xs={12}>
        <Grid>
          <SwipeableTemporaryDrawer />
        </Grid>
        <Grid>
          <Link to="/future">미래예상소비등록</Link>
        </Grid>
        <Grid>
          <Link to="/fixed">고정지출등록</Link>
        </Grid>
      </Grid>

      <Grid className="undernavbar">
        <UnderNavigationBar />
      </Grid>
    </Grid>
  );
};

export default AccountBookPage;
