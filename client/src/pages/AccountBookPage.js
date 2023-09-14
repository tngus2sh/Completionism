import { Grid } from "@mui/material";
import React from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import "./AccountBookPage.css";
import { Calender } from "../components/Calendar";
import { useState } from "react";
import SwipeableTemporaryDrawer from "../components/bottomDrawer";
import { Link } from "react-router-dom";

const AccountBookPage = () => {
  const upperNavbarName = "가계부";
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
  
  const [isDiary , setIsDiary] = useState(false)

  const ToggleCalendar = () => {
    setIsDiary(!isDiary)
  }

  return (
    <Grid className="accountbook_page">
      <Grid className="uppernavbar">
        <UpperNavigationBar props={upperNavbarName} />
      </Grid>

      <Grid className="progressive_bar"></Grid>

      <Grid className="upper_information_box" container></Grid>

      <Grid className="toggle_container" container>
        <button onClick = {ToggleCalendar}>toggle</button>
      </Grid>

      <Grid className="calander_container" container>
        <Calender isDiary={isDiary} props ={monthlyAccountBookData}/>
      </Grid>

      <Grid xs={12}>
        <Grid>
          <SwipeableTemporaryDrawer/>
        </Grid>
        <Grid>
        <Link to ="/future">미래예상소비등록</Link>
        </Grid>
        <Grid>
          <Link to ="/fixed">고정지출등록</Link>
        </Grid>
      </Grid>

      

      <Grid className="undernavbar">
        <UnderNavigationBar />
      </Grid>
    </Grid>
  );
};

export default AccountBookPage;
