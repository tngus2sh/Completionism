
import React from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import "./AccountBookPage.css";
import { CalenderForDiary } from "../components/CalendarForDiary";

const DiaryPage = ()=>{
    const upperNavbarName = "일기"

    return(
        <div>
            <div className="uppernavbar">
                <UpperNavigationBar props={upperNavbarName}/>
            </div>


            <div className="body">
                this is DiaryPage
            </div>
            
            <div>
                <CalenderForDiary/>
            </div>

            <div className="undernavbar">
                <UnderNavigationBar/>
            </div>
        </div>
    )
}

export default DiaryPage