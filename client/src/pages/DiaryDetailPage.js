
import React from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import "./AccountBookPage.css";
import { useParams } from 'react-router-dom';

const DiaryDetailPage = ()=>{
    const {id} = useParams()
    const upperNavbarName = `${id}일 일기`;


    return(
        <div>
            <div className="uppernavbar"> 
                <UpperNavigationBar props={upperNavbarName}/>
            </div>

            <div className="progressive_bar"></div>

            <div className="body">
                this is DiaryDetailPage
            </div>
            
            <div className="undernavbar">
                <UnderNavigationBar/>
            </div>
        </div>
    )
}

export default DiaryDetailPage