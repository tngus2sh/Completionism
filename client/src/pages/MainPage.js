import { Grid } from '@mui/material';
import React from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import "./MainPage.css";

const MainPage = ()=>{
    const upper_navbar_name = "홈"

    return(
        <Grid className='main_page'>
            <Grid className="uppernav_bar">
                <UpperNavigationBar props = {upper_navbar_name}/>
            </Grid>

            <Grid className="progressive_bar">
            </Grid>

            <Grid className="daily_consumption_plan_box">
            </Grid>

            <Grid className = "todays_ai_diary_container" container>
                <Grid className="todays_ai_diary_title">          
                </Grid>
                <Grid className="todays_ai_diary">          
                </Grid>
            </Grid>

            <Grid className = "tomorrow_consumption_container" container>
                <Grid  className="tomorrow_consumption_title">          
                </Grid>
                <Grid  className="tomorrow_consumption">          
                </Grid>
            </Grid>

            <Grid className = "todays_ai_consumption_feedback_container" container>
                <Grid  className="todays_ai_consumption_feedback_title">          
                </Grid>
                <Grid  className="todays_ai_consumption_feedback">          
                </Grid>
            </Grid>

            
            
            <Grid className="undernavbar">
                <UnderNavigationBar/>
            </Grid>
        </Grid>
    )
}

export default MainPage