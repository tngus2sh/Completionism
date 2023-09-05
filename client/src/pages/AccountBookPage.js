import { Grid } from "@mui/material";
import React from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import "./AccountBookPage.css";
import { Calender } from "../components/Calendar";

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
        day: "2023-08-02",
        income: 10000,
        spend: 3000,
      },
    ],
  };

  return (
    <Grid className="accountbook_page">
      <Grid className="uppernavbar">
        <UpperNavigationBar props={upperNavbarName} />
      </Grid>

      <Grid className="progressive_bar"></Grid>

      <Grid className="upper_information_box" container></Grid>

      <Grid className="toggle_container" container>
        <Grid>toggle button</Grid>
      </Grid>

      <Grid className="calander_container" container>
        <Calender props ={monthlyAccountBookData}/>
      </Grid>

      <Grid className="undernavbar">
        <UnderNavigationBar />
      </Grid>
    </Grid>
  );
};

export default AccountBookPage;
