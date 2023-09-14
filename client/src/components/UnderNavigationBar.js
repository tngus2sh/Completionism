
import React from "react";
import "./UnderNavigationBar.css";
import {Link} from "react-router-dom" ;

const UnderNavigationBar = () => {
    return(
        <div className ="undernavbar">
   
            <Link to ="/mainpage">홈</Link>
            <Link to ="/accountbook">|가계부</Link>
            <Link to ="/feadback">|피드백</Link>
            <Link to ="/setting">|설정</Link>

        </div>
    )
}

export default UnderNavigationBar