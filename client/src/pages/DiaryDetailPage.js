
import React from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import "./DiaryDetailPage.css";
import { Link, useParams } from 'react-router-dom';
const DiaryDetailPage = () => {
  const { id } = useParams()
  const upperNavbarName = `${id}일 일기`;


  return (
    <div>
      <div className="uppernavbar">
        <UpperNavigationBar props={upperNavbarName} />
      </div>

      <div className="progressive_bar"></div>

      <div className="body">
        this is a Diary write page
        <div>
          <div>
            <Link to="/diary/write">
              직접 쓰기
            </Link>
          </div>
          <div>
            <button>
              AI 자동일기
            </button>
          </div>
        </div>
      </div>

      <div className="undernavbar">
        <UnderNavigationBar />
      </div>
    </div >
  )
}

export default DiaryDetailPage