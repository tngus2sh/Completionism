import { Grid } from '@mui/material';
import React from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import "./MainPage.css";

const MainPage = ()=>{
    return(
        <Grid>
            <Grid className="uppernavbar">
                <UpperNavigationBar/>
            </Grid>

            <Grid className="box1">
                1
            </Grid>
            <Grid className="box2">
                2
            </Grid>
            <Grid className="box3">
                3
            </Grid>
            <Grid className="box4">
                4
            </Grid>
            
            
            <Grid className="undernavbar">
                <UnderNavigationBar/>
            </Grid>
        </Grid>
    )
}

export default MainPage