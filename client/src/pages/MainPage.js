import { Grid } from "@material-ui/core";
import React from "react";
import NavigationBar from "../components/NavigationBar";
import "./MainPage.css";

const MainPage = ()=>{
    return(
        <Grid>
            <Grid className="body">
                this is MainPage
            </Grid>
            
            <Grid className="navbar">
                <NavigationBar/>
            </Grid>
        </Grid>
    )
}

export default MainPage