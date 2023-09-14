
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
        
        <div className='main_page'>
            <div className="uppernav_bar">
                <UpperNavigationBar props = {upperNavbarName}/>
            </div>

            <div className="progressive_bar">
            </div>

            <div className="daily_consumption_plan_box">
                <div>{year}.{month}.{day} 소비계획</div>
                <div>계획한 소비 금액: {plannedAmount}</div>
                <div>실제 내가 쓴 금액 : {actualUsageAmount}</div>
                <div>계획보다 '{amountSpentMoreThanPlanned}' 원을 더 쓰셨습니다.</div>
            </div>

            <div className = "todays_ai_diary_container" >
                <div className="todays_ai_diary_title">  
                    오늘의 ai 일기
                </div>
                <div className="todays_ai_diary">
                    (이모티콘)
                    이른아침 나는 오전 8시 45분에..          
                </div>
            </div>

            <div className = "tomorrow_consumption_container" container>
                <div  className="tomorrow_consumption_title">     
                    내일 나는 얼마나 쓸까?     
                </div>
                <div  className="tomorrow_consumption">        
                    user님은 00000원 소비할 예정입니다.  
                </div>
            </div>

            <div className = "todays_ai_consumption_feedback_container" container>
                <div  className="todays_ai_consumption_feedback_title"> 
                    ai의 소비피드백         
                </div>
                <div  className="todays_ai_consumption_feedback">
                    너무 많이 사드셨어요          
                </div>
            </div>

            
            
            <div className="undernavbar">
                <UnderNavigationBar/>
            </div>
        </div>
    )
}

export default MainPage