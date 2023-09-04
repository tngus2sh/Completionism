import { Grid } from '@mui/material';
import React from "react";
import "./UpperNavigationBar.css";

const UpperNavigationBar = (props) => {
    console.log(props)
    const upper_navbar_name = props.props

    return(
        <Grid className ="uppernavbar">
            {upper_navbar_name}
        </Grid>
    )
}

export default UpperNavigationBar