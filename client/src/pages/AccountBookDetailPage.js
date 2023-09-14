
import React from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import "./AccountBookDetailPage.css";
import { useParams } from 'react-router-dom';

const AccoutBookDetailPage = ()=>{
    const {id} = useParams();
    // console.log ({id})
    const upperNavbarName = `${id}일 가계부`;

    return(
        <div>
            <div className="uppernavbar"> 
                <UpperNavigationBar props={upperNavbarName}/>
            </div>

            <div className="progressive_bar"></div>

            <div className="daily_consumption_plan_box">
            </div>

            <div className="body">
                this is AccountBookDetailPage
            </div>
             
            <div className="undernavbar">
                <UnderNavigationBar/>
            </div>
        </div>
    )
}

export default AccoutBookDetailPage