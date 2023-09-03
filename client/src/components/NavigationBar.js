import { Grid } from "@material-ui/core";
import React from "react";
import "./NavigationBar.css";
import {Link} from "react-router-dom" ;

const NavigationBar = () => {
    return(
        <Grid className ="navbar">
            this is Navgation bar
            <Link to ="/">홈</Link>
            <Link to ="/accountbook">가계부</Link>
        </Grid>
    )
}

export default NavigationBar