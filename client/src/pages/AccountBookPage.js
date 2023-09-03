import { Grid } from "@material-ui/core";
import React from "react";
import NavigationBar from "../components/NavigationBar";
import "./AccountBookPage.css";

const AccountBookPage = ()=>{
    return(
        <Grid>
            <Grid className="body">
                this is AccountBookPage
            </Grid>
            
            <Grid className="navbar">
                <NavigationBar/>
            </Grid>
        </Grid>
    )
}

export default AccountBookPage