
import React from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import "./AccountBookPage.css";
import { CalenderForDiary } from "../components/CalendarForDiary";
import { Button } from '@mui/material';
import { Link } from 'react-router-dom';

const DiaryPage = () => {
  const upperNavbarName = "일기"

  return (
    <div>
      <div className="uppernavbar">
        <UpperNavigationBar props={upperNavbarName} />
      </div>


      <div className="body">
        this is DiaryPage
      </div>

      <div>
        <CalenderForDiary />
      </div>

      <div>
        <div className="diary-button">
          <Link to="/diary/write" className="diary-link">
            일기 쓰러 가자!!
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