
import React from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import "./AccountBookPage.css";
import { Link } from 'react-router-dom';

const TotalDiaryPage = () => {
  const upperNavbarName = "일기 전체조회"

  return (
    <div>
      <div className="uppernavbar">
        <UpperNavigationBar props={upperNavbarName} />
      </div>


      <div className="body">
        this is TotalDiaryPage
      </div>




      <div className="undernavbar">
        <UnderNavigationBar />
      </div>
    </div>
  )
}

export default TotalDiaryPage