import { Grid } from '@mui/material';
import React from "react";
import "./UnderNavigationBar.css";
import {Link} from "react-router-dom" ;

const UnderNavigationBar = () => {
    return(
        <Grid className ="undernavbar">
            this is Navgation bar
            <Link to ="/">홈</Link>
            <Link to ="/accountbook">|가계부</Link>
            <Link to ="/setting">|설정</Link>

        </Grid>
    )
}

export default UnderNavigationBar