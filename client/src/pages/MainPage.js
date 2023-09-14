import { Grid } from '@mui/material';
import React from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import "./MainPage.css";
import axios from 'axios';

const MainPage = ()=>{
    const upperNavbarName = "홈";
    const currentDate = new Date();
    const year = currentDate.getFullYear();
    const month = currentDate.getMonth();
    const day = currentDate.getDate();
    

    //daily_consumption_plan_box 관련 데이터들
    const plannedAmount = null;
    const actualUsageAmount = null;
    const amountSpentMoreThanPlanned= null; 
    
    //daily_consumption_plan_box 관련 데이터들

    const functionName= async (e) => {
        //e.preventDefault();
        // 로컬 스토리지에서 엑세스 토큰 가져오기
        const accessToken = localStorage.getItem('accessToken');
        // Axios 요청 헤더 설정
        const headers = {
            Authorization: `Bearer ${accessToken}`, // 엑세스 토큰을 Bearer 토큰으로 헤더에 추가
        };
        try {
            const response = await axios.get('/api/auth/logout', {headers});
            console.log(response.data);
        } catch (error) {
            console.error(error);
            console.log(accessToken)
        }
    };



    return(
        
        <Grid className='main_page'>
            <Grid className="uppernav_bar">
                <UpperNavigationBar props = {upperNavbarName}/>
            </Grid>

            <Grid className="progressive_bar">
            </Grid>

            <Grid className="daily_consumption_plan_box">
                <Grid>{year}.{month}.{day} 소비계획</Grid>
                <Grid>계획한 소비 금액: {plannedAmount}</Grid>
                <Grid>실제 내가 쓴 금액 : {actualUsageAmount}</Grid>
                <Grid>계획보다 '{amountSpentMoreThanPlanned}' 원을 더 쓰셨습니다.</Grid>
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