
import React, { useState } from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import "./AccountBookPage.css";
import { useParams } from 'react-router-dom';
import axios from 'axios';

const DiaryWritePage = () => {
  const { id } = useParams()
  const upperNavbarName = `${id}일 일기`;

  const [useAxios, setUseAxios] = useState(false);

  const [diaryData, setDiaryData] = useState({
    time: "2023-09-16",
    diary: null,
    feel: 'JOY'
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setDiaryData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  // 일기 작성하기
  const sendDiary = async () => {
    const accessToken = localStorage.getItem("accessToken");
    const data = {
      time: diaryData.time,
      diary: diaryData.diary,
      feel: diaryData.feel
    };
    const headers = {
      Authorization: `Bearer ${accessToken}`,
    };
    try {
      const response = await axios.post(`/api/diary/in-person`, data, {
        headers,
      });
      console.log(response.data);
      setUseAxios(!useAxios);
    } catch (error) {
      console.error(error);
    }
  };


  return (
    <div>
      <div className="uppernavbar">
        <UpperNavigationBar props={upperNavbarName} />
      </div>

      <div className="progressive_bar"></div>

      <div className="body">
        <div>
          <input type='text'
            id="diary"
            name="diary"
            value={diaryData.diary || ""}
            onChange={handleInputChange}>
          </input>
        </div>
        <div>
          <button onClick={sendDiary}>
            작성완료
          </button>
        </div>
      </div>

      <div className="undernavbar">
        <UnderNavigationBar />
      </div>
    </div>
  )
}

export default DiaryWritePage