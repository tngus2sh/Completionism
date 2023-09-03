import { Grid } from '@mui/material';
import React from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import "./SettingPage.css";

const SettingPage = ()=>{
    return(
        <Grid>
            <Grid className="uppernavbar">
                <UpperNavigationBar/>
            </Grid>

            <Grid className="body">
                this is SettingPage
            </Grid>
            
            <Grid className="undernavbar">
                <UnderNavigationBar/>
            </Grid>
        </Grid>
    )
}

export default SettingPage