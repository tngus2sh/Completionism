import React, { useEffect } from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import LibraryBooksRoundedIcon from "@mui/icons-material/LibraryBooksRounded";
import DoneRoundedIcon from "@mui/icons-material/DoneRounded";
import SmartToyOutlinedIcon from "@mui/icons-material/SmartToyOutlined";
import "./MainPage.css";
import axios from "axios";
import { fatchPinnedData } from "../redux/authSlice";
import { useDispatch, useSelector } from "react-redux";
import { useState } from "react";
import { UseSelector } from "react-redux";
import { fatchFutureData } from "../redux/authSlice";
import { fatchMonthHistoryData } from "../redux/authSlice";

const MainPage = () => {
  const today = new Date()
  const parsingToday = today.getFullYear().toString()+'-'+(today.getMonth()+1).toString().padStart(2, "0")+'-'+today.getDate().toString().padStart(2, "0")

  const dispatch = useDispatch();
  const upperNavbarName = "홈";
  const currentDate = new Date();
  const year = currentDate.getFullYear();
  const month = currentDate.getMonth()+1;
  const day = currentDate.getDate();

  const fixedExpenditureList = useSelector(state=>state.auth.fixedExpenditureList)
  const FutureExpenditureList = useSelector(state=>state.auth.FutureExpenditureList)
  const MonthHistoryData = useSelector(state=>state.auth.MonthHistoryData)
  

  //daily_consumption_plan_box 관련 데이터들
  const [plannedAmount,setPlannedAmount] = useState(0);
  const [FutuerAmount,setFutuerAmount] = useState(0);
  const [actualUsageAmount,setActualUsageAmount] = useState(0);
  const amountSpentMoreThanPlanned = 0;

  //daily_consumption_plan_box 관련 데이터들

  useEffect(()=>{
    const fatch = async ()=>{
      loadData();
      loadFutureData();
      loadDataTodayExpend();
      calAmount();
    }
    fatch();
  },[])


  const functionName = async (e) => {
    //e.preventDefault();
    // 로컬 스토리지에서 엑세스 토큰 가져오기
    const accessToken = localStorage.getItem("accessToken");
    // Axios 요청 헤더 설정
    const headers = {
      Authorization: `Bearer ${accessToken}`, // 엑세스 토큰을 Bearer 토큰으로 헤더에 추가
    };
    try {
      const response = await axios.get("/api/auth/logout", { headers });
      console.log(response.data);
    } catch (error) {
      console.error(error);
      console.log(accessToken);
    }
  };

  function setScreenSize() {
    //먼저 뷰포트 높이를 얻고 1%를 곱하여 vh 단위 값을 얻습니다.
    let vh = window.innerHeight * 0.01;
    //그런 다음 --vh 사용자 정의 속성의 값을 문서의 루트로 설정합니다.
    document.documentElement.style.setProperty("--vh", `${vh}px`);
  }
  setScreenSize();
  window.addEventListener("resize", setScreenSize);



  //고정지출 데이터를 가져와서 
  const loadData = async () => {
    // 로컬 스토리지에서 엑세스 토큰 가져오기
    const accessToken = localStorage.getItem("accessToken");

    // Axios 요청 헤더 설정
    const headers = {
      Authorization: `Bearer ${accessToken}`, // 엑세스 토큰을 Bearer 토큰으로 헤더에 추가
    };

    try {
      let tempSumOfPinned = 0
      const response = await axios.get("/api/schedule/pinned", { headers });
      console.log(response.data);
      dispatch(fatchPinnedData(response.data.dataBody));
    } catch (error) {
      console.error(error);
    }
  };

  //미래지출 데이터를 가져와서 
  const loadFutureData = async () => {// 로컬 스토리지에서 엑세스 토큰 가져오기
    const accessToken = localStorage.getItem("accessToken");

    // Axios 요청 헤더 설정
    const headers = {
      Authorization: `Bearer ${accessToken}`, // 엑세스 토큰을 Bearer 토큰으로 헤더에 추가
    };

    try {
      const response = await axios.get("/api/schedule/future", { headers });
      console.log(response.data.dataBody);
      dispatch(fatchFutureData(response.data.dataBody));
    } catch (error) {
      console.error(error);
    }
  };


  const loadDataTodayExpend = async () => {
    const accessToken = localStorage.getItem("accessToken");
    const headers = {
      Authorization: `Bearer ${accessToken}`,
    };
   
    try {
      const response = await axios.get(
        `/api/history?date=${parsingToday}`,
        {
          headers,
        }
      );
      dispatch(fatchMonthHistoryData(response.data.dataBody));
      // console.log(response.data);
    } catch (error) {
      console.error(error);
    }
  };





  //오늘날짜에 해당되는 것들을 연산한다
  const calAmount = async () =>{
//  fixedExpenditureList.map((item , index)=>{
//       if(parsingToday===item.date){
//         console.log(item.cost)
//         setPlannedAmount(plannedAmount+item.cost);
//       }
//     })
//  FutureExpenditureList.map((item , index)=>{
//       if(parsingToday===item.date){
//         console.log(item.cost)
//         setFutuerAmount(plannedAmount+item.cost);
//       }
//     })
 MonthHistoryData.map((item , index)=>{
      if(parsingToday===item.time.slice(0,9)){
        console.log(item.cost)
        setActualUsageAmount(actualUsageAmount+item.cost);
      }
    })
    
  }

  return (
    <div className="main-page">
      <div className="uppernav_bar">
        <UpperNavigationBar props={upperNavbarName} />
      </div>

      <div className="progressive_bar"></div>

      <div className="daily_consumption_plan_box">
        <div className="main-header-info-text-container">
          <strong>
            {year}년 {month}월 {day}일
          </strong>
        </div>
        <div style={{ marginBottom: "0.3rem" }}>
          <LibraryBooksRoundedIcon sx={{ fontSize: "2.3rem", color: "#FFE500" }} />
        </div>
        <div className="main-header-content-text-container">
          <div className="main-header-content-text">
            <DoneRoundedIcon sx={{ fontSize: "1.2rem" }} />
            &nbsp;오늘 계획한 소비 : {plannedAmount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}원
          </div>
          <div className="main-header-content-text">
            <DoneRoundedIcon sx={{ fontSize: "1.2rem" }} />
            &nbsp;오늘 지출된 금액 : {actualUsageAmount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}원
          </div>
          <div className="main-header-content-text">
            <DoneRoundedIcon sx={{ fontSize: "1.2rem" }} />
            &nbsp;{ plannedAmount>=actualUsageAmount
              ? (<span>오늘 {(plannedAmount-actualUsageAmount).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}원 만큼 더 사용할 수 있어요!</span>)
              :(<span>오늘 계획보다 {(actualUsageAmount-plannedAmount).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}원을 초과했어요!</span>)
          }
          </div>
        </div>
      </div>

      <div className="todays-ai-diary-container">
        <div className="main-page-size-container">
          <div className="main-page-title-container">
            <strong>오늘의 ai 일기</strong>
          </div>
          <div className="todays_ai_diary-content-container">
            <div className="todays_ai_diary-content-feel">
              😍
            </div>
            (이모티콘) 이른아침 나는 오전 8시 45분에..
          </div>
        </div>
      </div>

      <div className="tomorrow-consumption-container">
        <div className="main-page-size-container">
          <div className="main-page-title-container">
            <strong>내일 나는 얼마나 쓸까?</strong>
          </div>
          <div className="tomorrow-consumption-content-container">
            user님은 00000원 소비할 예정입니다.
          </div>
        </div>
      </div>

      <div className="todays-ai-consumption-feedback-container">
        <div className="main-page-size-container">
          <div className="main-page-title-container">
            <strong>ai의 소비피드백</strong>
          </div>
          <div className="todays-ai-consumption-feedback-content-container">
            <div className="todays-ai-consumption-feedback-content-icon-container">
              <SmartToyOutlinedIcon sx={{ fontSize: "2.7rem", color: "#21BD08" }} />
            </div>
            <div className="todays-ai-consumption-feedback-content-text-container">
              너무 많이 사드셨어요
            </div>
          </div>
        </div>
      </div>

      <div
        style={{
          display: "inline-block",
          width: "100%",
          height: "6rem",
          backgroundColor: "#F0F1F4",
        }}
      ></div>

      <div className="undernavbar">
        <UnderNavigationBar />
      </div>
    </div>
  );
};

export default MainPage;
