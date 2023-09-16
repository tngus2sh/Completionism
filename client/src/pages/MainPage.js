import React from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import LibraryBooksRoundedIcon from "@mui/icons-material/LibraryBooksRounded";
import DoneRoundedIcon from "@mui/icons-material/DoneRounded";
import SmartToyOutlinedIcon from "@mui/icons-material/SmartToyOutlined";
import "./MainPage.css";
import axios from "axios";

const MainPage = () => {
  const upperNavbarName = "홈";
  const currentDate = new Date();
  const year = currentDate.getFullYear();
  const month = currentDate.getMonth();
  const day = currentDate.getDate();

  //daily_consumption_plan_box 관련 데이터들
  const plannedAmount = 0;
  const actualUsageAmount = 0;
  const amountSpentMoreThanPlanned = 0;

  //daily_consumption_plan_box 관련 데이터들

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
            &nbsp;오늘 하루 예산 : {plannedAmount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}원
            {plannedAmount}50000원
          </div>
          <div className="main-header-content-text">
            <DoneRoundedIcon sx={{ fontSize: "1.2rem" }} />
            &nbsp;오늘 지출된 금액 : {actualUsageAmount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}원
            {actualUsageAmount}70000원
          </div>
          <div className="main-header-content-text">
            <DoneRoundedIcon sx={{ fontSize: "1.2rem" }} />
            &nbsp;계획보다 {amountSpentMoreThanPlanned.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}원
            {amountSpentMoreThanPlanned}20000원을 더 쓰셨습니다.
          </div>
        </div>
      </div>

      <div className="todays-ai-diary-container">
        <div className="main-page-size-container">
          <div className="main-page-title-container">
            <strong>오늘의 ai 일기</strong>
          </div>
          <div className="todays_ai_diary-content-container">
            <div className='todays_ai_diary-content-date'>
              {year}.{month}.{day}
            </div>
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
