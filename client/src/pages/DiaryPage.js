
import React, { useEffect } from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import "./DiaryPage.css";
import { CalenderForDiary } from "../components/CalendarForDiary";
import { Button } from '@mui/material';
import { Link } from 'react-router-dom';
import axios from 'axios'
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { fatchMonthHistoryData } from "../redux/authSlice";
import { useState } from "react";
import { SetAiScript } from "../redux/authSlice";

const DiaryPage = () => {
  const dispatch = useDispatch()
  const upperNavbarName = "일기"
  const MonthHistoryData = useSelector((state)=> state.auth.MonthHistoryData)
  const AiScript = useSelector((state)=> state.auth.AiScript)
  const [isLoading, setIsLoading] = useState(false);

  const dateobj = new Date()
  const today = dateobj.getFullYear().toString() +
  "-" +
  (dateobj.getMonth() + 1).toString().padStart(2, "0")+'-'+dateobj.getDate().toString().padStart(2,"0");

  useEffect(()=>{
    loadDataforAiDiary();
  },[])


  // 먼저 redux스토어에 해당일의 데이터를 담는다
  const loadDataforAiDiary = async () => {
    const accessToken = localStorage.getItem("accessToken");
    const headers = {
      Authorization: `Bearer ${accessToken}`,
    };
   
    

    try {
      const response = await axios.get(
        `/api/history?date=${today}`,
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

  //데이터를 가져왔으니 요청을 보낸다
 const createAiDiary = async (e) => {
  // 로딩 시작
  setIsLoading(true);

  // 로컬 스토리지에서 엑세스 토큰 가져오기
  const accessToken = localStorage.getItem('accessToken');
  // Axios 요청 헤더 설정
  const data = {diaries: MonthHistoryData}

  const headers = {
      Authorization: `Bearer ${accessToken}`, // 엑세스 토큰을 Bearer 토큰으로 헤더에 추가
  };
  try {
    // 요청 보내기
    const response = await axios.post(`/api/diary`,data , {headers});
    console.log(response.data);
    dispatch(SetAiScript(response.data.dataBody.diary));
  } catch (error) {
    console.error("error");
    console.log(data);
  } finally {
    // 로딩 종료
    setIsLoading(false);
  }
};


  return (
    <div>
      <div className="uppernavbar">
        <UpperNavigationBar props={upperNavbarName} />
      </div>


      <div className="body">
        this is DiaryPage
      </div>

      {/* <div>
        <CalenderForDiary />
      </div> */}

      <div>
        <div className="diary-button">
          <Link to="/diary/write" className="diary-link">
            사용자 일기쓰기
          </Link>
        </div>
      </div>

      <button className="diary-button" onClick={createAiDiary}>
          ai일기자동생성
      </button>


       {/* 로딩 스피너를 표시하는 조건부 렌더링 */}
       {isLoading && <div>Loading...</div>}

      <div>
        오늘 일기 띄울곳
        {AiScript}
      </div>
      
      <div>
        <div className="diary-button">
          <Link to="/diary/total">
            일기 전체조회
          </Link>
        </div>
      </div>



      <div className="undernavbar">
        <UnderNavigationBar />
      </div>
    </div>
  )
}

export default DiaryPage