import { Grid } from "@material-ui/core";
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

            <Grid className="body">
                this is MainPage.
            </Grid>
            <Grid className="body">
                1
            </Grid>
            <Grid className="body">
                2
            </Grid>
            <Grid className="body">
                3
            </Grid>
            <Grid className="body">
                4
            </Grid>
            <Grid className="body">
                5
            </Grid>
            <Grid className="body">
                6
            </Grid>
            <Grid className="body">
                7
            </Grid>
            <Grid className="body">
                8
            </Grid>
            
            <Grid className="undernavbar">
                <UnderNavigationBar/>
            </Grid>
        </Grid>
    )
}

export default MainPage