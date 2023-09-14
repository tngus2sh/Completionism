
import React from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import "./AccountBookPage.css";

const FeadbackPage = ()=>{
    return(
        <div>
            <div className="uppernavbar">
                <UpperNavigationBar/>
            </div>

            <div className="body">
                this is FeadbackPage
            </div>
            
            <div className="undernavbar">
                <UnderNavigationBar/>
            </div>
        </div>
    )
}

export default FeadbackPage